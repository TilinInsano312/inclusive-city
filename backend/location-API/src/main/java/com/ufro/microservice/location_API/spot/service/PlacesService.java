package com.ufro.microservice.location_API.spot.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.stereotype.Service;

@Service
public class PlacesService {

    private final GeoApiContext geoApiContext;

    public PlacesService(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }

    public void getPlacesDetails(String placeId) {
        // Lógica para buscar lugares utilizando la API de Google Places
        PlaceDetailsRequest a = PlacesApi.placeDetails(geoApiContext, placeId)
                .fields();
    }
}
