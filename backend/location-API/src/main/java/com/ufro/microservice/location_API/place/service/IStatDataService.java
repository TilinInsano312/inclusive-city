package com.ufro.microservice.location_API.place.service;

import com.ufro.microservice.location_API.place.dto.StatDataDTO;
import com.ufro.microservice.location_API.place.dto.StatDataResultDTO;

import java.util.Map;

public interface IStatDataService {
    StatDataResultDTO calculateStatData(String placeId);
    long addStatDataToPlace(StatDataDTO statDataDTO, String placeId, String userId);
    long updateReview(StatDataResultDTO data, String placeId);
}
