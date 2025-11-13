package com.ufro.microservice.location_API.spot.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PhotoRequest;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import com.ufro.microservice.location_API.spot.dto.*;
import com.ufro.microservice.location_API.spot.mapper.IPlaceMapper;
import com.ufro.microservice.location_API.spot.repository.IPlaceRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlacesService implements IPlaceService{

    private final GeoApiContext geoApiContext;
    private final IPlaceMapper placeMapper;
    private final IPlaceRepository placeRepository;
    private final StatDataService statDataService;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PlacesService.class);

    public PlacesService(GeoApiContext geoApiContext, IPlaceMapper placeMapper, IPlaceRepository placeRepository, StatDataService statDataService) {
        this.geoApiContext = geoApiContext;
        this.placeMapper = placeMapper;
        this.placeRepository = placeRepository;
        this.statDataService = statDataService;
    }

    //Buscar lugar por placeId
    //To do: manejar excepciones
    @Override
    public PlaceDetailResponseDTO getPlaceDetails(String placeId) {
        try {
            PlaceDetails request = PlacesApi.placeDetails(geoApiContext, placeId)
                    .fields(PlaceDetailsRequest.FieldMask.PLACE_ID,
                            PlaceDetailsRequest.FieldMask.NAME,
                            PlaceDetailsRequest.FieldMask.FORMATTED_ADDRESS,
                            PlaceDetailsRequest.FieldMask.GEOMETRY,
                            PlaceDetailsRequest.FieldMask.PHOTOS).await();
            PlaceDetailDTO detailDTO = convertToDetailDTO(request);
            return new PlaceDetailResponseDTO(
                    detailDTO.getPlaceId(),
                    detailDTO.getName(),
                    detailDTO.getAddress(),
                    detailDTO.getCoordinate(),
                    detailDTO.getPhotos(),
                    getMedalsByPlaceId(placeId),
                    getRatingByPlaceId(placeId)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Buscar lugares por nombre
    //To do: manejar excepciones
    @Override
    public List<PlaceSearchResponseDTO> getPlaceBySearch(String query) {
        try {
            PlacesSearchResponse respuestaGoogle = PlacesApi.textSearchQuery(geoApiContext, query).region("cl").language("es")
                    .await();
            return Arrays.stream(respuestaGoogle.results).map(lugar -> {
                LocationDTO coords = new LocationDTO(
                        lugar.geometry.location.lat,
                        lugar.geometry.location.lng
                );
                List<String> medals = new ArrayList<>();
                float rating = 0.0f;
                try {
                    medals = getMedalsByPlaceId(lugar.placeId);
                    rating = getRatingByPlaceId(lugar.placeId);
                } catch (NoSuchElementException e) {
                    log.warn(e.getMessage());
                }
                return new PlaceSearchResponseDTO(
                        lugar.placeId,
                        lugar.name,
                        lugar.formattedAddress,
                        coords,
                        lugar.photos != null ?
                                Arrays.stream(lugar.photos)
                                        .map(photo -> photo.photoReference)
                                        .collect(Collectors.toList())
                                : Collections.emptyList(),
                        medals,
                        rating

                );
            }).collect(Collectors.toList());
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

    // Utils
    private PlaceDetailDTO convertToDetailDTO(PlaceDetails details) {
        LocationDTO coords = new LocationDTO(
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
    private List<String> getMedalsByPlaceId(String placeId) {
        if (!placeRepository.existsPlaceByPlaceId(placeId)) {
            return Collections.emptyList();
        }
        return placeRepository.findByPlaceId(placeId)
                .map(place -> new ArrayList<>(place.getMedals())).orElseThrow(
                        () -> new NoSuchElementException("No se encontraron medallas para el lugar con placeId: " + placeId)
                );
    }
    private float getRatingByPlaceId(String placeId) {
        if (!placeRepository.existsPlaceByPlaceId(placeId)) {
            return 0.0f;
        }
        return placeRepository.findByPlaceId(placeId)
                .map(place -> place.getRating()).orElseThrow( () ->
                        new NoSuchElementException("No se encontró rating para el lugar con placeId: " + placeId)
                );
    }

}
