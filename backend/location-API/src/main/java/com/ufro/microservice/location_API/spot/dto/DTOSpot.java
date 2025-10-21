package com.ufro.microservice.location_API.spot.dto;

public record DTOSpot( String id, String spotName, String place_id, String address, double longitude, double latitude, String type) {
}
