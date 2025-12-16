package com.ufro.microservice.location_API.incidence.dto;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class IncidenceRequestDTO {
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private String placeId;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private LocationDTO location;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private String incidence;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private String userId;
    @NotNull(message = "Cannot be null")
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
