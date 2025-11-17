package com.ufro.microservice.location_API.incidence.dto;

import com.ufro.microservice.location_API.common.dto.LocationDTO;

import java.time.Instant;
import java.util.Date;

public class IncidenceDTO {
    private String placeId;
    private LocationDTO locationDTO;
    private String incidence;
    private Date date;
    private Instant expiresAt;
    private String userId;
    private String image;

    public IncidenceDTO(String placeId, LocationDTO locationDTO, String incidence, Date date, Instant expiresAt, String userId, String image) {
        this.placeId = placeId;
        this.locationDTO = locationDTO;
        this.incidence = incidence;
        this.date = date;
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

    public LocationDTO getLocationDTO() {
        return locationDTO;
    }

    public void setLocationDTO(LocationDTO locationDTO) {
        this.locationDTO = locationDTO;
    }

    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
