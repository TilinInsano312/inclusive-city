package com.ufro.microservice.location_API.spot.model;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.MongoId;


@ToString
@EqualsAndHashCode
public class Spot {
    @MongoId
    private String id;
    private String spotName;
    @Indexed(unique = true)
    private String place_id;
    private String address;
    private double longitude;
    private double latitude;
    private String type;

    public Spot(Spot spot) {
        this.id = spot.getId();
        this.spotName = spot.getSpotName();
        this.place_id = spot.getPlace_id();
        this.address = spot.getAddress();
        this.longitude = spot.getLongitude();
        this.latitude = spot.getLatitude();
        this.type = spot.getType();
    }

    public Spot() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
