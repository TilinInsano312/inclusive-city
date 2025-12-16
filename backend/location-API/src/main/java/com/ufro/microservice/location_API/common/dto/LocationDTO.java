package com.ufro.microservice.location_API.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LocationDTO {
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private double longitude;
    @NotBlank(message = "Cannot be blank")
    @NotNull(message = "Cannot be null")
    private double latitude;

    public LocationDTO(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
