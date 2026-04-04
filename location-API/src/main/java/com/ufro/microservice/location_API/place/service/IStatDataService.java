package com.ufro.microservice.location_API.place.service;

import com.ufro.microservice.location_API.place.dto.FormsDTO;
import com.ufro.microservice.location_API.place.dto.RateChoiceDTO;
import com.ufro.microservice.location_API.place.dto.StatDataResultDTO;

public interface IStatDataService {
    StatDataResultDTO calculateStatData(String placeId);
    long addFormsToPlace(FormsDTO formsDTO, String placeId, String userId);
    long updateReview(StatDataResultDTO data, String placeId);
    long addRateChoiceToPlace(String placeId, String userId, RateChoiceDTO rateChoice);
}
