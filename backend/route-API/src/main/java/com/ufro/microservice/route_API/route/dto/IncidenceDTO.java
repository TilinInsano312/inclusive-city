package com.ufro.microservice.route_API.route.dto;

import java.time.Instant;

public record IncidenceDTO(String placeId, LocationDTO locationDTO, String incidence, Instant expiresAt, String userId, String image) {
}
