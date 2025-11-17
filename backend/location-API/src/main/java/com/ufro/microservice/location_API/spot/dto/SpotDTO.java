package com.ufro.microservice.location_API.spot.dto;

import com.ufro.microservice.location_API.common.dto.LocationDTO;

public record SpotDTO(String userId, String spotName, String placeId, String address, LocationDTO location, String type) {
}
