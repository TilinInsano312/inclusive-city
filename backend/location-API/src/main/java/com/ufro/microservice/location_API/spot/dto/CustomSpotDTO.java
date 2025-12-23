package com.ufro.microservice.location_API.spot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CustomSpotDTO {
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private String listName;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private String userId;
    @NotNull(message = "Cannot be null")
    private List<SpotDTO> spots;

    public CustomSpotDTO(String listName, String userId, List<SpotDTO> spots) {
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

    public List<SpotDTO> getSpots() {
        return spots;
    }

    public void setSpots(List<SpotDTO> spots) {
        this.spots = spots;
    }
}
