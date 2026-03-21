package com.ufro.microservice.location_API.place.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PlaceDTO {
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private String placeId;
    @NotNull(message = "Cannot be null")
    private List<String> medals;
    @NotNull(message = "Cannot be null")
    private float rating;
    private List<StatDataDTO> statsData;

    public PlaceDTO(String placeId, List<String> medals, float rating, List<StatDataDTO> statsData) {
        this.placeId = placeId;
        this.medals = medals;
        this.rating = rating;
        this.statsData = statsData;
    }

    public PlaceDTO() {
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

    public List<StatDataDTO> getStatsData() {
        return statsData;
    }

    public void setStatsData(List<StatDataDTO> statsData) {
        this.statsData = statsData;
    }

    @Override
    public String toString() {
        return "PlaceDTO{" +
                "placeId='" + placeId + '\'' +
                ", medals=" + medals +
                ", rating=" + rating +
                ", statsData=" + statsData +
                '}';
    }
}
