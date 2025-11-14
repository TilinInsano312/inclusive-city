package com.ufro.microservice.location_API.spot.repository;

public interface IPLaceRepositoryCustom {
    boolean updateStatDataRateChoice(String placeId, String userId, String newRate);
}
