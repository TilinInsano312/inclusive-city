package com.ufro.microservice.location_API.place.repository;

import com.ufro.microservice.location_API.place.dto.AggregatedStatDTO;
import com.ufro.microservice.location_API.place.model.Place;
import com.ufro.microservice.location_API.place.model.StatData;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface IPlaceRepository extends MongoRepository<Place, String>, IPLaceRepositoryCustom {
    Optional<Place> findByPlaceId(String placeId);
    boolean existsPlaceByPlaceId (String placeId);

    /**
     * Obtiene los conteos de 'rateChoice' y 'forms.choice' para un placeId específico
     * usando el framework de agregación de MongoDB.
     *
     * @param placeId El ID del lugar a consultar.
     * @return Un DTO con las listas de conteos.
     */
    @Aggregation(pipeline = {
            "{ $match: { placeId: ?0 } }",
            "{ $facet: {" +
                    "    'rateCounts': [" +
                    "        { $unwind: '$statsData' }," +
                    "        { $group: { _id: '$statsData.rateChoice', count: { $sum: 1 } } }" +
                    "    ]," +
                    "    'formCounts': [" +
                    "        { $unwind: '$statsData' }," +
                    "        { $unwind: '$statsData.forms' }," +
                    "        { $group: { _id: '$statsData.forms.choice', count: { $sum: 1 } } }" +
                    "    ]" +
                    "} }"
    })
    AggregatedStatDTO getAggregatedStats(String placeId);

    Place findPlaceByPlaceId(String placeId);

    @Query("{'placeId': ?0}")
    @Update("{'$set': {'statsData': ?1}}")
    long updateByPlaceId(String placeId, StatData statData);

    @Query("{'placeId': ?0}")
    @Update("{'$set': {'medals': ?1, 'rating': ?2}}")
    long updatePlaceByPlaceId(String placeId, List<String> medals, float rating);

}
