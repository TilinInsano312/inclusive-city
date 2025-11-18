package com.ufro.microservice.route_API.route.service;

import com.ufro.microservice.route_API.route.client.LocationClient;
import com.ufro.microservice.route_API.route.dto.IncidenceDTO;
import com.ufro.microservice.route_API.route.dto.RouteResponseDTO;
import com.ufro.microservice.route_API.route.exception.ExternalServiceException;
import com.ufro.microservice.route_API.route.exception.InvalidCoordinatesException;
import com.ufro.microservice.route_API.route.exception.RouteNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class RouteService {
    @Value("${google.maps.api.key:}")
    private String googleApiKey;
    @Value("${here.api.key:}")
    private String hereApiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    private LocationClient locationClient;

    public RouteService(LocationClient locationClient) {
        this.locationClient = locationClient;
    }
    // las warning solo son unchecked casts, no afectan el funcionamiento

    public RouteResponseDTO getPrincipalRoute(String origin, String destination) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&alternatives=false&key=%s",
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

        return simplifyRoute(routes.getFirst());
    }

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

        List<Map<String, Object>> incidences = incidencesFromLocation.stream()
                .map(inc -> Map.<String, Object>of(
                        "lat", inc.latitude(),
                        "lng", inc.longitude(),
                        "radius", 200
                ))
                .toList();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("https://router.hereapi.com/v8/routes")
                .queryParam("origin", originLat + "," + originLng)
                .queryParam("destination", destinationLat + "," + destinationLng)
                .queryParam("transportMode", "car")
                .queryParam("return", "polyline,summary")
                .queryParam("polylineFormat", "flex")
                .queryParam("apikey", hereApiKey);

        for (Map<String, Object> inc : incidences) {
            String area = createAvoidArea(
                    (double) inc.get("lat"),
                    (double) inc.get("lng"),
                    (int) inc.get("radius")
            );
            builder.queryParam("avoid[areas]", area);
        }

        String url = builder.toUriString();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("routes")) {
            throw new ExternalServiceException("HERE API no devolvió rutas disponibles");
        }

        List<Map> routes = (List<Map>) response.get("routes");
        if (routes.isEmpty()) {
            throw new RouteNotFoundException("No se encontró una ruta segura para el trayecto especificado");
        }

        Map route = routes.get(0);
        Map section = ((List<Map>) route.get("sections")).get(0);
        Map<String, Object> summary = (Map<String, Object>) section.get("summary");
        Object polylineObj = section.get("polyline");

        String polyline = extractPolyline(polylineObj);

        return new RouteResponseDTO(
                summary.get("length").toString(),
                summary.get("duration").toString(),
                polyline
        );
    }

    private double parseCoordinate(String value, String fieldName) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidCoordinatesException("Coordenada inválida para " + fieldName + ": " + value);
        }
    }

    private String extractPolyline(Object polylineObj) {
        if (polylineObj instanceof String) {
            return (String) polylineObj;
        } else if (polylineObj instanceof Map<?, ?> polyMap) {
            String encoded = (String) polyMap.get("encoded");
            if (encoded == null) {
                throw new ExternalServiceException("Formato de polyline sin campo 'encoded'");
            }
            return encoded;
        } else {
            throw new ExternalServiceException("Formato inesperado de polyline");
        }
    }


    private String createAvoidArea(double lat, double lng, int radiusMeters) {
        double degrees = radiusMeters / 111_111.0;

        double south = lat - degrees;
        double north = lat + degrees;
        double west = lng - degrees;
        double east = lng + degrees;

        return west + "," + south + ";" + east + "," + north;
    }


    // metodo para simplificar la ruta y dar una respuesta mas simple
    private RouteResponseDTO simplifyRoute(Map<String, Object> ruta) {
        Map<String, Object> leg = ((List<Map<String, Object>>) ruta.get("legs")).get(0);
        String distance = ((Map<String, Object>) leg.get("distance")).get("text").toString();
        String duration = ((Map<String, Object>) leg.get("duration")).get("text").toString();
        String polyline = ((Map<String, Object>) ruta.get("overview_polyline")).get("points").toString();

        return new RouteResponseDTO(distance, duration, polyline);
    }

    private List<IncidenceDTO> getIncidences() {
        return locationClient.getAllIncidences();
    }
}

