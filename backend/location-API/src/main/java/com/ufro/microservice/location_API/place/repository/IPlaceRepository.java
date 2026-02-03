package com.ufro.microservice.location_API.place.repository;

import com.ufro.microservice.location_API.place.model.Place;
import com.ufro.microservice.location_API.place.model.StatData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface IPlaceRepository extends MongoRepository<Place, String>, PlaceRepositoryCustom {
    Optional<Place> findByPlaceId(String placeId);
    boolean existsPlaceByPlaceId (String placeId);
    Place findPlaceByPlaceId(String placeId);

    @Query("{'placeId': ?0}")
    @Update("{'$set': {'statsData': ?1}}")
    long updateByPlaceId(String placeId, StatData statData);

    @Query("{'placeId': ?0}")
    @Update("{'$set': {'medals': ?1, 'rating': ?2}}")
    long updatePlaceByPlaceId(String placeId, List<String> medals, float rating);

    boolean existsByPlaceId (String placeId);

}
