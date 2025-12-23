package com.ufro.microservice.location_API.spot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CustomSpotRequestDTO {
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private String listName;
    @NotNull(message = "Cannot be null")
    private List<SpotDTO> spots;

    public CustomSpotRequestDTO(String listName, List<SpotDTO> spots) {
        this.listName = listName;
        this.spots = spots;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<SpotDTO> getSpots() {
        return spots;
    }

    public void setSpots(List<SpotDTO> spots) {
        this.spots = spots;
    }
}
