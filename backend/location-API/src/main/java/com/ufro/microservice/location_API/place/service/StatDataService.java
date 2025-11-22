package com.ufro.microservice.location_API.place.service;

import com.ufro.microservice.location_API.place.dto.*;
import com.ufro.microservice.location_API.place.mapper.IPlaceMapper;
import com.ufro.microservice.location_API.place.mapper.IStatDataMapper;
import com.ufro.microservice.location_API.place.model.enums.Medals;
import com.ufro.microservice.location_API.place.repository.IPlaceRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatDataService implements IStatDataService {
    private final IPlaceRepository placeRepository;
    private final IPlaceMapper placeMapper;
    private final IStatDataMapper statDataMapper;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(StatDataService.class);

    public StatDataService(IPlaceRepository placeRepository, IPlaceMapper placeMapper, IStatDataMapper statDataMapper) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
        this.statDataMapper = statDataMapper;
    }

    @Override
    public long updateReview(StatDataResultDTO data, String placeId){
        return placeRepository.updatePlaceByPlaceId(placeId, data.getFormStatistics(), data.getAverageRate());
    }
    @Override
    public StatDataResultDTO calculateStatData(String placeId) {
        PlaceDTO placeDTO = placeMapper.toPlaceDTO(placeRepository.findByPlaceId(placeId).orElseThrow());
        int total = placeDTO.getStatsData().size();
        int likeCount =0;
        Map<Integer, Integer> formsData = new HashMap<>();
        for (Map.Entry<String, StatDataDTO> entry : placeDTO.getStatsData().entrySet()) {
            StatDataDTO statDataDTO = entry.getValue();
            if (statDataDTO.getRateChoice().equals("LIKE")) {
                likeCount ++;
            }
            for (int i = 0; i <6; i++) {
                String choice = statDataDTO.getForms().get(i);
                if (choice.equals("YES")) {
                    formsData.put(i, formsData.getOrDefault(i, 0) + 1);
                }
            }
        }
        log.info("Calculated stat data for place ID: " + placeId + " with total responses: " + total);
        log.info("Like count: " + likeCount + ", Forms data: " + formsData);
        return new StatDataResultDTO(((float) likeCount / total) * 100, calculateMedals(formsData, total));
    }

    private List<String> calculateMedals(Map<Integer, Integer> formsData, int totalForms) {
        List<String> formStatistics = new java.util.ArrayList<>();
        for (int i = 0; i < 6; i++) {
            float percentage = ((float) formsData.getOrDefault(i, 0) / totalForms) * 100;
            if (percentage > 51) {
                switch (i) {
                    case 0 -> formStatistics.add(Medals.ATENCION_PREFERENCIAL.name());
                    case 1 -> formStatistics.add(Medals.ACCESIBILIDAD.name());
                    case 2 -> formStatistics.add(Medals.BANOS.name());
                    case 3 -> formStatistics.add(Medals.BANOS.name());
                    case 4 -> formStatistics.add(Medals.ESTACIONAMIENTO.name());
                    case 5  -> formStatistics.add(Medals.FACIL_CIRCULACION.name());
                    default -> formStatistics.add("NO_MEDAL");
                }
            }
            }
        log.info("Calculated medals: " + formStatistics);
        return formStatistics;
    }

    @Override
    public long addStatDataToPlace(StatDataDTO statDataDTO, String placeId, String userId) {
        if(placeRepository.existsPlaceByPlaceId(placeId)){
            log.info("Adding stat data to place with ID: " + placeId + " for user: " + userId);
            PlaceDTO placeDTO = placeMapper.toPlaceDTO(placeRepository.findByPlaceId(placeId).orElseThrow());
            placeDTO.getStatsData().put(userId, statDataDTO);
            log.info("Updated stats data for place: " + placeDTO.getStatsData());
            return placeRepository.updateByPlaceId(placeId, statDataMapper.toStatData(statDataDTO));
        }
        else {
            log.warn("Place with ID: " + placeId + " does not exist. Cannot add stat data.");
            placeMapper.toPlaceDTO(placeRepository.save(
                    placeMapper.toPlace(new PlaceDTO(
                            placeId,
                            List.of(),
                            0.0f,
                            Map.of(userId, statDataDTO)))));
            log.info("Created new place with ID: " + placeId + " and initial stat data for user: " + userId);
            return 0;
        }

    }
}
