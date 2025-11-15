package com.ufro.microservice.location_API.spot.dto;

import java.util.List;

public class PlaceSearchResponseDTO {
    private String placeId;
    private String name;
    private String address;
    private LocationDTO location;
    private List<String> photos;
    private List<String> medals;
    private float rating;

    public PlaceSearchResponseDTO(String placeId, String name, String address, LocationDTO coordinate, List<String> photos, List<String> medals, float rating) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.location = coordinate;
        this.photos = photos;
        this.medals = medals;
        this.rating = rating;
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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
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
}
