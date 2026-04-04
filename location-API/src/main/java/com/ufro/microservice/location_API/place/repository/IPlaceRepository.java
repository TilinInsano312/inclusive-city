package com.ufro.microservice.location_API.place.repository;

import com.ufro.microservice.location_API.place.model.Place;
import com.ufro.microservice.location_API.place.model.StatData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface IPlaceRepository extends MongoRepository<Place, String> {
    Optional<Place> findByPlaceId(String placeId);
    boolean existsPlaceByPlaceId (String placeId);
    Place findPlaceByPlaceId(String placeId);

    @Query(value = "{ 'placeId': ?0, 'statsData.userId': ?1 }", exists = true)
    boolean existsStatDataByPlaceIdAndUserId(String placeId, String userId);

    @Query("{ 'placeId': ?0, 'statsData.userId': ?1 }")
    @Update("{ '$set': { 'statsData.$': ?2 } }")
    long updateExistingStatData(String placeId, String userId, StatData statData);

    @Query("{ 'placeId': ?0, 'statsData.userId': { '$ne': ?1 } }")
    @Update("{ '$push': { 'statsData': ?2 } }")
    long addStatDataIfUserNotExists(String placeId, String userId, StatData statData);

    @Query("{'placeId': ?0}")
    @Update("{'$set': {'medals': ?1, 'rating': ?2}}")
    long updatePlaceByPlaceId(String placeId, List<String> medals, float rating);

    boolean existsByPlaceId (String placeId);

}
