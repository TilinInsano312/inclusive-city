package com.ufro.microservice.location_API.incidence.dto;

import java.util.Date;

public record IncidenceDTO(String id, String place_id, double latitude, double longitude, String incidence, Date date, String idUser, String image) {
}
