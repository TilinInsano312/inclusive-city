package com.ufro.microservice.location_API.spot.repository.impl;

import com.mongodb.client.result.UpdateResult;
import com.ufro.microservice.location_API.spot.model.Place;
import com.ufro.microservice.location_API.spot.repository.IPLaceRepositoryCustom;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class IPlaceRepositoryImpl implements IPLaceRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public IPlaceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean updateStatDataRateChoice(String placeId, String userId, String  newRate) {
        Query query = Query.query(
                Criteria.where("_id").is(placeId)    // o "id" según tu mapeo
                        .and("statsData.userId").is(userId)
        );

        Update update = new Update().set("statsData.$.rateChoice", newRate);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Place.class);
        return result.getModifiedCount() > 0;
    }
}
