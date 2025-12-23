package com.ufro.microservice.location_API.common.dto;

//Todo: @berAxz revisar las validaciones de location que esta rara la validacion de @NotBlank
public class LocationDTO {
    private double longitude;
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
