package com.ufro.microservice.location_API.place.dto;

import com.ufro.microservice.location_API.place.model.StatData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceDTO {
    private String placeId;
    private List<String> medals;
    private float rating;
    private Map<String,StatDataDTO> statsData;

    public PlaceDTO(String placeId, List<String> medals, float rating, Map<String, StatDataDTO> statsData) {
        this.placeId = placeId;
        this.medals = medals;
        this.rating = rating;
        this.statsData = statsData;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<String> getMedals() {
        return medals;
    }

    public void setMedals(List<String> medals) {
        this.medals = medals;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Map<String, StatDataDTO> getStatsData() {
        return statsData;
    }

    public void setStatsData(Map<String, StatDataDTO> statsData) {
        this.statsData = statsData;
    }
}
