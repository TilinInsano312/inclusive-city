package com.ufro.microservice.route_API.route.dto;


import java.time.Instant;

public class IncidenceDTO {

    private String placeId;
    private LocationDTO location;
    private String incidence;
    private Instant expiresAt;
    private String userId;
    private String image;

    public IncidenceDTO(String placeId, LocationDTO locationDTO, String incidence, Instant expiresAt, String userId, String image) {
        this.placeId = placeId;
        this.location = locationDTO;
        this.incidence = incidence;
        this.expiresAt = expiresAt;
        this.userId = userId;
        this.image = image;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
