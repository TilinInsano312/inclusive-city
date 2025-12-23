package com.ufro.microservice.location_API.spot.repository;

import com.ufro.microservice.location_API.common.model.Location;
import com.ufro.microservice.location_API.spot.model.CustomSpot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableMongoRepositories
@Repository
public interface ICustomSpotRepository extends MongoRepository<CustomSpot, String> {
    List<CustomSpot> findByUserId(String userId);
    CustomSpot findCustomSpotByUserIdAndListName(String userId, String listName);
    boolean existsCustomSpotByUserIdAndListName(String userId, String listName);
    boolean existsByUserId(String userId);
    long deleteCustomSpotByListNameAndUserId(String listName, String userId);

    @Query("{ 'listName': ?0,'userId': ?1}")
    @Update("{ $pull: { spots: { location: ?2 } } }")
    long updateCustomSpotByListNameAndUserId(String listName, String userId, Location location);
}
