package com.ufro.microservice.location_API.spot.dto;

import com.ufro.microservice.location_API.spot.model.StatData;

import java.util.List;

public class PlaceDTO {
    private String placeId;
    private List<String> medals;
    private float rating;
    private List<StatData> statsData;

    public PlaceDTO(String placeId, List<String> medals, float rating, List<StatData> statsData) {
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

    public List<StatData> getStatsData() {
        return statsData;
    }

    public void setStatsData(List<StatData> statsData) {
        this.statsData = statsData;
    }
}
