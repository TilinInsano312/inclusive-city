package com.ufro.microservice.location_API.spot.dto;

import com.ufro.microservice.location_API.spot.model.Spot;

import java.util.List;

public class SaveCustomSpotDTO {
    private String id;
    private String listName;
    private String userId;
    private List<SpotDTO> spots;

    public SaveCustomSpotDTO(String id, String listName, String userId, List<SpotDTO> spots) {
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

    public List<SpotDTO> getSpots() {
        return spots;
    }

    public void setSpots(SpotDTO spot) {
        spots.add(spot);
    }
}
