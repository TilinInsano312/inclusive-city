package com.ufro.microservice.location_API.spot.repository;

import com.ufro.microservice.location_API.spot.model.Spot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableMongoRepositories
@Repository
public interface ISpotRepository extends MongoRepository<Spot, String> {
    List<Spot> findByUserId(String userId);
}
