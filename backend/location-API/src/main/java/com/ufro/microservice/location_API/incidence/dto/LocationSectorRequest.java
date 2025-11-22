package com.ufro.microservice.location_API.incidence.dto;

import com.ufro.microservice.location_API.common.dto.LocationDTO;

public class LocationSectorRequest {
    private LocationDTO pointNorthEast;
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
