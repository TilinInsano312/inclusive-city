package com.ufro.microservice.location_API.incidence.dto;

import com.ufro.microservice.location_API.common.dto.LocationDTO;


public class IncidenceRequestDTO {
    private String placeId;
    private LocationDTO location;
    private String incidence;
    private String userId;
    private String image;

    public IncidenceRequestDTO(String placeId, LocationDTO location, String incidence, String userId, String image) {
        this.placeId = placeId;
        this.location = location;
        this.incidence = incidence;
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
