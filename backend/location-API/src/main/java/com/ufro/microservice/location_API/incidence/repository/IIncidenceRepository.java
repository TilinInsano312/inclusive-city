package com.ufro.microservice.location_API.incidence.repository;

import com.ufro.microservice.location_API.incidence.model.Incidence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IIncidenceRepository extends MongoRepository <Incidence, String> {
}
