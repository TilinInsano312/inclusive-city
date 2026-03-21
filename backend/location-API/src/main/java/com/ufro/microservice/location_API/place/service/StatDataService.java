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
        log.info("Updating review for place ID: " + placeId + " with like percentage: " + data.getRateChoice() + " and medals: " + data.getForms());
        return placeRepository.updatePlaceByPlaceId(placeId, data.getForms(), data.getRateChoice());
    }
    @Override
    public StatDataResultDTO calculateStatData(String placeId) {
        log.info("Calculating stat data for place ID: " + placeId);
        PlaceDTO placeDTO = placeMapper.toPlaceDTO(placeRepository.findByPlaceId(placeId).orElseThrow());
        log.info("Fetched place data for place ID: " + placeId + " with stats data: " + placeDTO.getStatsData());
        int total = placeDTO.getStatsData().size();
        int likeCount =0;
        Map<Integer, Integer> formsData = new HashMap<>();
        for (StatDataDTO statDataDTO : placeDTO.getStatsData()) {
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
        try {
            if (placeRepository.existsPlaceByPlaceId(placeId)) {
                log.info("Adding stat data to place with ID: " + placeId + " for user: " + userId);
                PlaceDTO placeDTO = placeMapper.toPlaceDTO(placeRepository.findByPlaceId(placeId).orElseThrow());
                log.info("Fetched place data for place ID: " + placeId + " with existing stats data: " + placeDTO.getStatsData());
                StatDataDTO statdata = new StatDataDTO(userId, statDataDTO.getRateChoice(), statDataDTO.getForms());
                placeDTO.getStatsData().add(statdata);
                log.info("Updated stats data for place: " + placeDTO.getStatsData());
                // si el usuario ya tiene una review, se actualiza, sino se agrega una nueva stat data. Para esto se revisa si el userId ya existe en las stat data del lugar, si existe se actualiza esa stat data, sino se agrega una nueva stat data al lugar.
                if ( placeDTO.getStatsData().stream().anyMatch(stat -> stat.getUserId().equals(userId))) {
                    log.info("User with ID: " + userId + " already has a review for place ID: " + placeId + ". Updating existing stat data.");
                    placeDTO.getStatsData().removeIf(stat -> stat.getUserId().equals(userId));
                }
                return placeRepository.updateByPlaceId(placeId, statDataMapper.toStatData(statdata));
            } else {
                log.warn("Place with ID: " + placeId + " does not exist. Cannot add stat data.");
                placeMapper.toPlaceDTO(placeRepository.save(
                        placeMapper.toPlace(new PlaceDTO(
                                placeId,
                                List.of(),
                                0.0f,
                                List.of(new StatDataDTO(userId, statDataDTO.getRateChoice(), statDataDTO.getForms()))
                        ))
                ));
                log.info("Created new place with ID: " + placeId + " and initial stat data for user: " + userId);
                return 0;
            }
        } catch (Exception e) {
            log.info("Error adding stat data to place with ID: " + placeId + " for user: " + userId + ". Error: " + e.getMessage());
            log.error("Exception stack trace: ", e);
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }
}
