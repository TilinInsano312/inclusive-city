package com.ufro.microservice.route_API.route.service;

import com.ufro.microservice.route_API.route.client.LocationClient;
import com.ufro.microservice.route_API.route.common.response.ApiResponse;
import com.ufro.microservice.route_API.route.dto.IncidenceDTO;
import com.ufro.microservice.route_API.route.dto.RouteResponseDTO;
import com.ufro.microservice.route_API.route.exception.ExternalServiceException;
import com.ufro.microservice.route_API.route.exception.InvalidCoordinatesException;
import com.ufro.microservice.route_API.route.exception.RouteNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteService {
    private static final Logger log = LoggerFactory.getLogger(RouteService.class);

    @Value("${google.maps.api.key:}")
    private String googleApiKey;

    @Value("${ors.api.key:}")
    private String orsApiKey;

    @Value("${ors.api.url:https://api.openrouteservice.org/v2/directions/wheelchair}")
    private String orsApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final LocationClient locationClient;

    public RouteService(LocationClient locationClient) {
        this.locationClient = locationClient;
    }

    @SuppressWarnings("unchecked")
    public RouteResponseDTO getPrincipalRoute(String origin, String destination) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&mode=walking&alternatives=false&key=%s",
                origin, destination, googleApiKey
        );

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null) {
            throw new ExternalServiceException("Google Maps API no respondió correctamente");
        }

        List<Map<String, Object>> routes = (List<Map<String, Object>>) response.get("routes");
        if (routes == null || routes.isEmpty()) {
            throw new RouteNotFoundException("No se encontró una ruta entre " + origin + " y " + destination);
        }
        log.info("incidencias obtenidas para ruta principal: {}", getIncidences());
        return simplifyRoute(routes.getFirst());
    }

    @SuppressWarnings("unchecked")
    public RouteResponseDTO getSecureRoute(String origin, String destination) {
        String[] originCoords = origin.split(",");
        String[] destCoords = destination.split(",");

        if (originCoords.length != 2 || destCoords.length != 2) {
            throw new InvalidCoordinatesException("Formato de coordenadas inválido. Use: lat,lng");
        }

        double originLat = parseCoordinate(originCoords[0], "latitud de origen");
        double originLng = parseCoordinate(originCoords[1], "longitud de origen");
        double destinationLat = parseCoordinate(destCoords[0], "latitud de destino");
        double destinationLng = parseCoordinate(destCoords[1], "longitud de destino");

        List<IncidenceDTO> incidencesFromLocation = getIncidences();
        log.info("Total de incidencias obtenidas: {}", incidencesFromLocation.size());

        // Construir el cuerpo de la petición para OpenRouteService
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("coordinates", List.of(
                List.of(originLng, originLat),
                List.of(destinationLng, destinationLat)
        ));

        // Construir e inyectar los polígonos de evitación si existen incidencias
        Map<String, Object> avoidPolygons = buildOrsAvoidPolygons(incidencesFromLocation);
        if (!avoidPolygons.isEmpty()) {
            Map<String, Object> options = new HashMap<>();
            options.put("avoid_polygons", avoidPolygons);
            requestBody.put("options", options);
            log.info("Áreas de evitación dinámicas inyectadas en la petición");
        }

        log.info("Enviando petición a OpenRouteService API...");

        // Configurar los headers requeridos por ORS
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", orsApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(orsApiUrl, entity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null || !responseBody.containsKey("routes")) {
                throw new ExternalServiceException("OpenRouteService API no devolvió rutas disponibles");
            }

            List<Map<String, Object>> routes = (List<Map<String, Object>>) responseBody.get("routes");
            if (routes.isEmpty()) {
                throw new RouteNotFoundException("No se encontró una ruta segura para el trayecto especificado");
            }

            // ORS siempre devuelve la ruta más óptima considerando los polígonos bloqueados
            Map<String, Object> bestRoute = routes.getFirst();
            Map<String, Object> summary = (Map<String, Object>) bestRoute.get("summary");

            double distance = ((Number) summary.get("distance")).doubleValue();
            double durationSecs = ((Number) summary.get("duration")).doubleValue();
            String encodedPolyline = (String) bestRoute.get("geometry");

            log.info("Ruta segura calculada - Distancia: {} m, Duración: {} s", distance, durationSecs);

            // Convertir la respuesta a texto legible para el Frontend
            String distanceText = String.format("%.0f m", distance);
            if (distance >= 1000) {
                distanceText = String.format("%.1f km", distance / 1000);
            }

            long durationSeconds = (long) durationSecs;
            String durationText = String.format("%d min", durationSeconds / 60);
            if (durationSeconds < 60) {
                durationText = String.format("%d seg", durationSeconds);
            }

            return new RouteResponseDTO(distanceText, durationText, encodedPolyline);

        } catch (ExternalServiceException | RouteNotFoundException e) {
            // Ya son excepciones personalizadas, simplemente relanzarlas
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al llamar a OpenRouteService API: {}", e.getMessage(), e);
            throw new ExternalServiceException("Error al obtener ruta segura: " + e.getMessage());
        }
    }

    private double parseCoordinate(String value, String fieldName) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidCoordinatesException("Coordenada inválida para " + fieldName + ": " + value);
        }
    }

    /**
     * Construye el objeto MultiPolygon (GeoJSON) requerido por OpenRouteService
     * para bloquear áreas de incidencias dinámicamente.
     */
    private Map<String, Object> buildOrsAvoidPolygons(List<IncidenceDTO> incidences) {
        Map<String, Object> avoidPolygons = new HashMap<>();

        List<IncidenceDTO> validIncidences = incidences.stream()
                .filter(inc -> inc.getIncidence() != null)
                .toList();
        log.info("Incidencias válidas para generar polígonos de evitación: {}", validIncidences.size());
        log.info("Detalles de incidencias válidas: {}", validIncidences.stream()
                .map(inc -> String.format("ID: %s, Tipo: %s, Ubicación: (%.6f, %.6f)",
                        inc.getPlaceId(), inc.getIncidence(),
                        inc.getLocation().getLatitude(), inc.getLocation().getLongitude()))
                .toList());

        if (validIncidences.isEmpty()) {
            return avoidPolygons;
        }

        // FIX CRÍTICO: 4 niveles de anidación para un MultiPolygon GeoJSON estricto
        // Estructura: Lista de Polígonos -> Lista de Anillos -> Lista de Coordenadas -> [lon, lat]
        List<List<List<List<Double>>>> multiPolygonCoords = new ArrayList<>();

        for (IncidenceDTO inc : validIncidences) {
            double lat = inc.getLocation().getLatitude();
            double lng = inc.getLocation().getLongitude();

            // 20 metros de radio (40m de diámetro). Suficiente para tapar la calle
            // y la vereda sin bloquear las calles paralelas.
            List<List<Double>> polygonCoords = createCircularPolygon(lat, lng, 20);

            // Cada polígono debe tener un anillo exterior (el primer anillo)
            List<List<List<Double>>> singlePolygonRings = new ArrayList<>();
            singlePolygonRings.add(polygonCoords);

            // Añadimos el polígono completo al MultiPolygon
            multiPolygonCoords.add(singlePolygonRings);
        }

        avoidPolygons.put("type", "MultiPolygon");
        avoidPolygons.put("coordinates", multiPolygonCoords);

        return avoidPolygons;
    }

    /**
     * Crea un polígono cerrado alrededor de una coordenada.
     */
    private List<List<Double>> createCircularPolygon(double centerLat, double centerLng, int radiusMeters) {
        List<List<Double>> polygon = new ArrayList<>();
        int numPoints = 16;

        double latDegrees = radiusMeters / 111_111.0;
        double lngDegrees = radiusMeters / (111_111.0 * Math.cos(Math.toRadians(centerLat)));

        // Generamos los puntos del círculo
        for (int i = 0; i < numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            double pointLat = centerLat + latDegrees * Math.sin(angle);
            double pointLng = centerLng + lngDegrees * Math.cos(angle);

            // ORS requiere la coordenada en formato [Longitud, Latitud]
            polygon.add(List.of(pointLng, pointLat));
        }

        // FIX CRÍTICO: Cierre perfecto. El estándar GeoJSON exige que el último
        // punto sea una copia exacta del primero para "cerrar" el polígono.
        polygon.add(polygon.getFirst());

        return polygon;
    }

    @SuppressWarnings("unchecked")
    private RouteResponseDTO simplifyRoute(Map<String, Object> ruta) {
        Map<String, Object> leg = ((List<Map<String, Object>>) ruta.get("legs")).getFirst();
        String distance = ((Map<String, Object>) leg.get("distance")).get("text").toString();
        String duration = ((Map<String, Object>) leg.get("duration")).get("text").toString();
        String polyline = ((Map<String, Object>) ruta.get("overview_polyline")).get("points").toString();

        return new RouteResponseDTO(distance, duration, polyline);
    }

    private List<IncidenceDTO> getIncidences() {
        ApiResponse<List<IncidenceDTO>> response = locationClient.getAllIncidences();
        if (response != null && response.isStatus()) {
            return response.getData();
        }
        return Collections.emptyList();
    }
}