package com.ufro.microservice.location_API.incidence.repository;

import com.ufro.microservice.location_API.incidence.model.Incidence;
import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IIncidenceRepository extends MongoRepository <Incidence, String> {
    List<Incidence> findByLocationWithin(Box box);
    Optional<Incidence> findByPlaceIdAndIncidence(String placeId, String incidence);
}
