package com.ufro.microservice.location_API.spot.model;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@ToString
@EqualsAndHashCode
@Document(collection = "spots")
public class Spot {
    //Hace referencia a un punto de interes al guardar(Ej: Casa, Trabajo)
    @MongoId
    private String id;
    private String userId;
    private String spotName;
    @Indexed(unique = true)
    private String placeId;
    private String address;
    private Location location;
    private String type;

    public Spot(String id, String userId, String spotName, String placeId, String address, Location location, String type) {
        this.id = id;
        this.userId = userId;
        this.spotName = spotName;
        this.placeId = placeId;
        this.address = address;
        this.location = location;
        this.type = type;
    }

    public Spot() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
