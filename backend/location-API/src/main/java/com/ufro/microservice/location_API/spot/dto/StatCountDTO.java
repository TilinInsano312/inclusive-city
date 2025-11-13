package com.ufro.microservice.location_API.spot.dto;

import java.util.Objects;

public class StatCountDTO {
    private String _id; // Aquí vendrá "LIKE", "DISLIKE", "YES" o "NO"
    private int count;

    public StatCountDTO(String _id, int count) {
        this._id = _id;
        this.count = count;
    }

    // Getters y Setters
    public String get_id() { return _id; }
    public void set_id(String _id) { this._id = _id; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    @Override
    public String toString() {
        return "StatCountDTO{" +
                "_id='" + _id + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatCountDTO that = (StatCountDTO) o;
        return count == that.count && Objects.equals(_id, that._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, count);
    }
}
