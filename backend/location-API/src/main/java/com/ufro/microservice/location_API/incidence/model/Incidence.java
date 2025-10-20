package com.ufro.microservice.location_API.incidence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "incidences")
public class Incidence {
    @Id
    private String id;
    @Indexed(unique = true)
    private String place_id;
    private double latitude;
    private double longitude;
    private String incidence;
    private Date date;
    private String idUser;
    private String image;

    public Incidence(String id, String place_id, double latitude, double longitude, String incidence, Date date, String idUser, String image) {
        this.id = id;
        this.place_id = place_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.incidence = incidence;
        this.date = date;
        this.idUser = idUser;
        this.image = image;
    }

    public Incidence() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIncidence() {
        return incidence;
    }

    public void setIncidence(String incidence) {
        this.incidence = incidence;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
