package com.ufro.microservice.location_API.spot.service;

import com.ufro.microservice.location_API.spot.dto.*;

import java.util.List;

public interface IPlaceService {

    PlaceDetailResponseDTO getPlaceDetails(String placeId);
    List<PlaceSearchResponseDTO> getPlaceBySearch(String query);
    PhotoDTO getPlacePhoto(String photoReference, Integer maxWidth);

}
