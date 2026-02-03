package com.ufro.microservice.location_API.place.repository;

import com.ufro.microservice.location_API.place.model.Place;

import java.util.List;

public interface PlaceRepositoryCustom {
    List<Place> findByStatsDataUserId(String userId);
}