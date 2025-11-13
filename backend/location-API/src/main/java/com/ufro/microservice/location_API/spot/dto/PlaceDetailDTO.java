package com.ufro.microservice.location_API.spot.dto;

import java.util.List;

public class PlaceDetailDTO {
    private String placeId;
    private String name;
    private String address;
    private LocationDTO coordinate;
    private List<String> photos;

    public PlaceDetailDTO(String placeId, String name, String address, LocationDTO coordinate, List<String> photos) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.coordinate = coordinate;
        this.photos = photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocationDTO getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(LocationDTO coordinate) {
        this.coordinate = coordinate;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
