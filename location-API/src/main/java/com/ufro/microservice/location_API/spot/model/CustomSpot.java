package com.ufro.microservice.location_API.spot.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "customSpots")
public class CustomSpot {
    @MongoId
    private String id;
    private String listName;
    private String userId;
    private List<Spot> spots;

    public CustomSpot(String id, String listName, String userId, List<Spot> spots) {
        this.id = id;
        this.listName = listName;
        this.userId = userId;
        this.spots = spots;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(Spot spot) {
        spots.add(spot);
    }
}
