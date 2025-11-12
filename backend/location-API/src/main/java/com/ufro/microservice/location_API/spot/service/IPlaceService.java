package com.ufro.microservice.location_API.spot.service;

import com.google.maps.GeoApiContext;
import com.ufro.microservice.location_API.spot.dto.PlaceDetailDTO;
import com.ufro.microservice.location_API.spot.dto.PlaceSearchDTO;

public interface IPlaceService {


    PlaceDetailDTO getPlaceDetails(String placeId);
    PlaceSearchDTO getPlaceBySearch(GeoApiContext geoApiContext, String query);
}
