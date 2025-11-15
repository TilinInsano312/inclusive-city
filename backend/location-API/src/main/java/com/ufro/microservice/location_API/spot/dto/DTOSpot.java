package com.ufro.microservice.location_API.spot.dto;

public record DTOSpot(String id,String userId, String spotName, String placeId, String address, LocationDTO location, String type) {
}
