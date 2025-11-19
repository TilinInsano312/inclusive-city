package com.ufro.microservice.location_API.place.service;

import com.ufro.microservice.location_API.place.dto.*;
import com.ufro.microservice.location_API.place.mapper.IPlaceMapper;
import com.ufro.microservice.location_API.place.repository.IPlaceRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatDataService implements IStatDataService {
    private final IPlaceRepository placeRepository;
    private final IPlaceMapper placeMapper;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(StatDataService.class);

    public StatDataService(IPlaceRepository placeRepository, IPlaceMapper placeMapper) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
    }

    //Estadisticas de rating
    @Override
    public boolean updateStatDataRateChoice(String placeId, String userId, String newRate){
        calculateAverageRate(placeId);
        return placeRepository.updateStatDataRateChoice(placeId, userId, newRate);
    }
    @Override
    public Map<String, Integer> getRating(String placeId) {
        AggregatedStatDTO stats = placeRepository.getAggregatedStats(placeId);
        Map<String, Integer> countsMap = new HashMap<>();

        // Procesar rateCounts
        if (stats.getRateCounts() != null) {
            for (StatCountDTO count : stats.getRateCounts()) {
                if ("LIKE".equals(count.get_id())) {
                    countsMap.put("LIKES", count.getCount());
                } else if ("DISLIKE".equals(count.get_id())) {
                    countsMap.put("DISLIKES", count.getCount());
                }
            }
        }
        countsMap.putIfAbsent("LIKES", 0);
        countsMap.putIfAbsent("DISLIKES", 0);
        return countsMap;
    }
    @Override
    public float calculateAverageRate(String placeId) {
        Integer likeCount = getRating(placeId).get("LIKES");
        Integer dislikeCount = getRating(placeId).get("DISLIKES");
        Integer total = likeCount + dislikeCount;
        return ((float) likeCount /total)*100;
    }

    //Formulario
    @Override
    public void setForm(String formChoices) {

    }

    @Override
    public void calculateFormStatistics(String placeId) {

    }

    public StatDataDTO addFormToStatData(StatDataDTO statDataDTO, String placeId) {
        return null;
    }
}
