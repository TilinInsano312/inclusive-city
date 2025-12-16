package com.ufro.microservice.location_API.incidence.dto;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LocationSectorRequest {
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private LocationDTO pointNorthEast;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private LocationDTO pointSouthWest;

    public LocationDTO getPointNorthEast() {
        return pointNorthEast;
    }

    public void setPointNorthEast(LocationDTO pointNorthEast) {
        this.pointNorthEast = pointNorthEast;
    }

    public LocationDTO getPointSouthWest() {
        return pointSouthWest;
    }

    public void setPointSouthWest(LocationDTO pointSouthWest) {
        this.pointSouthWest = pointSouthWest;
    }
}
