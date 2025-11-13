package com.ufro.microservice.location_API.spot.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "places")
public class Place {
    @MongoId
    private String id;
    @Indexed(unique = true)
    private String placeId;
    private List<String> medals;
    private float rating;
    @Field("statsData")
    private List<StatData> statsData;

    public Place(String id, String placeId, List<String> medals, float rating, List<StatData> statsData) {
        this.id = id;
        this.placeId = placeId;
        this.medals = medals;
        this.rating = rating;
        this.statsData = statsData;
    }

    public Place() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
