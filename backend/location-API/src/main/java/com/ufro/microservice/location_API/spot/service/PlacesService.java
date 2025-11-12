package com.ufro.microservice.location_API.spot.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import com.ufro.microservice.location_API.spot.dto.CoordinateDTO;
import com.ufro.microservice.location_API.spot.dto.PlaceDetailDTO;
import com.ufro.microservice.location_API.spot.dto.PlaceSearchDTO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PlacesService implements IPlaceService{

    private final GeoApiContext geoApiContext;
    private final Logger log = (Logger) LoggerFactory.getLogger(PlacesService.class);


    public PlacesService(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }


    private PlaceDetailDTO convertToDetailDTO(PlaceDetails details) {

        // 1. Mapear coordenadas
        CoordinateDTO coords = new CoordinateDTO(
                details.geometry.location.lat,
                details.geometry.location.lng
        );

        // 2. Mapear referencias de fotos (ver explicación abajo)
        List<String> photoReferences = new ArrayList<>();
        if (details.photos != null) {
            photoReferences = Arrays.stream(details.photos)
                    .map(photo -> photo.photoReference) // Solo extraemos la referencia
                    .collect(Collectors.toList());
        }

        // 3. Construir y devolver nuestro DTO personalizado
        return new PlaceDetailDTO(
                details.placeId,
                details.name,
                details.formattedAddress,
                coords,
                photoReferences
        );
    }

    @Override
    public PlaceDetailDTO getPlaceDetails(String placeId) {
        try {
            PlaceDetails request = PlacesApi.placeDetails(geoApiContext, placeId)
                    .fields(PlaceDetailsRequest.FieldMask.PLACE_ID,
                            PlaceDetailsRequest.FieldMask.NAME,
                            PlaceDetailsRequest.FieldMask.FORMATTED_ADDRESS,
                            PlaceDetailsRequest.FieldMask.GEOMETRY,
                            PlaceDetailsRequest.FieldMask.PHOTOS).await();
            log.info(request.name);
            return convertToDetailDTO(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlaceSearchDTO getPlaceBySearch(GeoApiContext geoApiContext, String query) {
        return null;
    }
}
