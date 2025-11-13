package com.ufro.microservice.location_API.spot.repository;

import com.ufro.microservice.location_API.spot.model.enums.ChoiceRate;

public interface IPLaceRepositoryCustom {
    boolean updateStatDataRateChoice(String placeId, String userId, String newRate);
}
