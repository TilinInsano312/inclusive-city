package com.ufro.microservice.location_API.incidence.model;

import com.ufro.microservice.location_API.common.model.Location;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "incidences")
public class Incidence {
    @Id
    private String id;
    @Indexed(unique = true)
    private String placeId;
    private Location location;
    private String incidence;
    @Indexed(expireAfter = "0s")
    private Instant expiresAt;
    private String userId;
    private String image;

    public Incidence(String id, String placeId, Location location, String incidence, Instant expiresAt, String userId, String image) {
        this.id = id;
        this.placeId = placeId;
        this.location = location;
        this.incidence = incidence;
        this.expiresAt = expiresAt;
        this.userId = userId;
        this.image = image;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
