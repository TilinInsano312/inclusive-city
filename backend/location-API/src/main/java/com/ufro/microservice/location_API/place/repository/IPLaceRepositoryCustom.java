package com.ufro.microservice.location_API.place.repository;

public interface IPLaceRepositoryCustom {
    boolean updateStatDataRateChoice(String placeId, String userId, String newRate);
}
