package com.ufro.microservice.location_API.place.repository.impl;

import com.ufro.microservice.location_API.place.model.Place;
import com.ufro.microservice.location_API.place.repository.PlaceRepositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ObjectOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import java.util.List;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public PlaceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Place> findByStatsDataUserId(String targetUserId) {
        /*
         * Estrategia de Agregación:
         * 1. addFields + ObjectToArray:
         * Transforma el mapa { "key1": {userId: "A"}, "key2": {userId: "B"} }
         * en un array [ {k: "key1", v: {userId: "A"}}, {k: "key2", v: {userId: "B"}} ]
         * * 2. match:
         * Filtra buscando dentro de ese array temporal (statsAsArray.v.userId)
         */

        Aggregation aggregation = newAggregation(
                // Paso 1: Convertir el mapa a Array para poder iterar sus valores
                addFields()
                        .addField("statsAsArray")
                        .withValue(ObjectOperators.ObjectToArray.valueOfToArray("statsData"))
                        .build(),

                // Paso 2: Filtrar donde el valor (v) tenga el userId buscado
                match(Criteria.where("statsAsArray.v.userId").is(targetUserId)),

                // Paso 3 (Opcional): Limpiar el campo temporal auxiliar antes de devolver
                project().andExclude("statsAsArray")
        );

        // Ejecutar la agregación sobre la colección de la clase Place
        return mongoTemplate.aggregate(aggregation, Place.class, Place.class).getMappedResults();
    }
}