package com.ufro.microservice.route_API.route.dto;

public class RouteResponseDTO {
    String distance;
    String duration;
    String polyline;

    public RouteResponseDTO(String distance, String duration, String polyline) {
        this.distance = distance;
        this.duration = duration;
        this.polyline = polyline;
    }

    public RouteResponseDTO() {
    }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    public String getPolyline() {
        return polyline;
    }
}
