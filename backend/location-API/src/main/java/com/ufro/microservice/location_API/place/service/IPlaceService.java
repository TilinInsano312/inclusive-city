package com.ufro.microservice.location_API.place.service;

import com.ufro.microservice.location_API.place.dto.PhotoDTO;
import com.ufro.microservice.location_API.place.dto.PlaceDTO;
import com.ufro.microservice.location_API.place.dto.PlaceDetailResponseDTO;
import com.ufro.microservice.location_API.place.dto.PlaceSearchResponseDTO;

import java.util.List;

public interface IPlaceService {

    PlaceDetailResponseDTO getNearbySearch(double lat, double lng);
    PlaceDetailResponseDTO getPlaceDetails(String placeId);
    List<PlaceSearchResponseDTO> getPlaceBySearch(String query);
    PhotoDTO getPlacePhoto(String photoReference, Integer maxWidth);
    List<PlaceDTO> getStatDataByUserId(String userId);

}
