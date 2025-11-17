package com.ufro.microservice.location_API.spot.dto;

import com.ufro.microservice.location_API.spot.model.Spot;

import java.util.List;

public class CustomSpotDTO {
    private String listName;
    private String userId;
    private List<Spot> spots;

    public CustomSpotDTO(String listName, String userId, List<Spot> spots) {
        this.listName = listName;
        this.userId = userId;
        this.spots = spots;
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

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }
}
