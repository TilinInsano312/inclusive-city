package com.ufro.microservice.location_API.spot.service;

import java.util.Map;

public interface IStatDataService {
    boolean updateStatDataRateChoice(String placeId, String userId, String newRate);
    Map<String, Integer> getRating(String placeId);
    void setForm(String formChoices);
    float calculateAverageRate(String placeId);
    void calculateFormStatistics(String placeId);
}
