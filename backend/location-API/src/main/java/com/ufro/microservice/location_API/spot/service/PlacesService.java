package com.ufro.microservice.location_API.spot.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PhotoRequest;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import com.ufro.microservice.location_API.spot.dto.CoordinateDTO;
import com.ufro.microservice.location_API.spot.dto.PhotoDTO;
import com.ufro.microservice.location_API.spot.dto.PlaceDetailDTO;
import com.ufro.microservice.location_API.spot.dto.PlaceSearchDTO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlacesService implements IPlaceService{

    private final GeoApiContext geoApiContext;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PlacesService.class);

    public PlacesService(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }

    //Buscar lugar por placeId
    //To do: manejar excepciones
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
    //Buscar lugares por nombre
    //To do: manejar excepciones
    @Override
    public List<PlaceSearchDTO> getPlaceBySearch(String query) {
        try {
            PlacesSearchResponse respuestaGoogle = PlacesApi.textSearchQuery(geoApiContext, query).region("cl").language("es")
                    .await();
            return Arrays.stream(respuestaGoogle.results)
                    .map(resultado -> new PlaceSearchDTO(
                            resultado.placeId,
                            resultado.name,
                            resultado.formattedAddress,
                            new CoordinateDTO(
                                    resultado.geometry.location.lat,
                                    resultado.geometry.location.lng
                            ),
                            resultado.photos != null ?
                                    Arrays.stream(resultado.photos)
                                            .map(photo -> photo.photoReference)
                                            .collect(Collectors.toList())
                                    : Collections.emptyList())
                    ).collect(Collectors.toList());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //El photoReference es una id que se arroja en la variable photos de los placesDetail/Search
    //to do: manejar excepciones
    @Override
    public PhotoDTO getPlacePhoto(String photoReference, Integer maxWidth) {
        try {
            PhotoRequest photoRequest = PlacesApi.photo(geoApiContext, photoReference);
            return new PhotoDTO(photoRequest.maxWidth(maxWidth).await().imageData, "image/jpeg");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la foto: " + e.getMessage(), e);
        }
    }

    private PlaceDetailDTO convertToDetailDTO(PlaceDetails details) {
        CoordinateDTO coords = new CoordinateDTO(
                details.geometry.location.lat,
                details.geometry.location.lng
        );
        List<String> photoReferences = new ArrayList<>();
        if (details.photos != null) {
            photoReferences = Arrays.stream(details.photos)
                    .map(photo -> photo.photoReference) // Solo extraemos la referencia
                    .collect(Collectors.toList());
        }
        return new PlaceDetailDTO(
                details.placeId,
                details.name,
                details.formattedAddress,
                coords,
                photoReferences
        );
    }
}
