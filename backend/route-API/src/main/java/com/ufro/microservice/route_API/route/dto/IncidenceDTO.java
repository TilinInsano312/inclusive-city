package com.ufro.microservice.route_API.route.dto;

import java.util.Date;

public record IncidenceDTO(String place_id, double latitude, double longitude, String incidence, Date date, String idUser, String image) {
}
