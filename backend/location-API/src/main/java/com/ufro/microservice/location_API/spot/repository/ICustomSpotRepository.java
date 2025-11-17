package com.ufro.microservice.location_API.spot.repository;

import com.ufro.microservice.location_API.spot.model.CustomSpot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableMongoRepositories
@Repository
public interface ICustomSpotRepository extends MongoRepository<CustomSpot, String> {
    List<CustomSpot> findByUserId(String userId);
    CustomSpot findCustomSpotByUserIdAndListName(String userId, String listName);
}
