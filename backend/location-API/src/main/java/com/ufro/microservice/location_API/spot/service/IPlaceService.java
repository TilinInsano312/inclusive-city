package com.ufro.microservice.location_API.spot.service;

import com.ufro.microservice.location_API.spot.dto.PhotoDTO;
import com.ufro.microservice.location_API.spot.dto.PlaceDetailDTO;
import com.ufro.microservice.location_API.spot.dto.PlaceSearchDTO;
import java.util.List;

public interface IPlaceService {


    PlaceDetailDTO getPlaceDetails(String placeId);
    List<PlaceSearchDTO> getPlaceBySearch(String query);
    PhotoDTO getPlacePhoto(String photoReference, Integer maxWidth);
}
