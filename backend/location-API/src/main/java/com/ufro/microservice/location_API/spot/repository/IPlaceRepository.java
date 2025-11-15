package com.ufro.microservice.location_API.spot.repository;

import com.ufro.microservice.location_API.spot.dto.AggregatedStatDTO;
import com.ufro.microservice.location_API.spot.model.Place;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

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


}
