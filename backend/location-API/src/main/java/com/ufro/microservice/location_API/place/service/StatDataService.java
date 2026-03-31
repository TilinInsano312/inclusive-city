package com.ufro.microservice.location_API.place.service;

import com.ufro.microservice.location_API.place.dto.*;
import com.ufro.microservice.location_API.place.mapper.IPlaceMapper;
import com.ufro.microservice.location_API.place.mapper.IStatDataMapper;
import com.ufro.microservice.location_API.place.model.enums.Medals;
import com.ufro.microservice.location_API.place.repository.IPlaceRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

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
    // Este metodo se encarga de agregar los datos del formulario (forms) a un lugar específico para un usuario determinado.
    // Primero, verifica si el lugar existe en la base de datos.
    // Si el lugar existe, se obtiene la información del lugar y se agrega la nueva información del formulario a la lista de datos estadísticos del lugar.
    // Si el usuario ya tiene una elección de calificación para ese lugar, se actualiza la información del formulario existente en lugar de agregar una nueva.
    // Finalmente, se actualiza el lugar en la base de datos con la nueva información del formulario.
    @Override
    public long addFormsToPlace(FormsDTO formsDTO, String placeId, String userId) {
        try {
            List<String> forms = new ArrayList<>(formsDTO.getForms());

            if (!placeRepository.existsPlaceByPlaceId(placeId)) {
                log.warn("Place with ID: {} does not exist. Creating place with initial forms.", placeId);

                PlaceDTO newPlace = new PlaceDTO(
                        placeId,
                        List.of(),
                        0.0f,
                        List.of(new StatDataDTO(userId, "NA", forms))
                );

                placeMapper.toPlaceDTO(placeRepository.save(placeMapper.toPlace(newPlace)));
                log.info("Created new place with ID: {} and initial forms for user ID: {}", placeId, userId);
                return 0;
            }

            log.info("Adding/updating forms for place ID: {} and user ID: {}", placeId, userId);
            PlaceDTO placeDTO = placeMapper.toPlaceDTO(placeRepository.findByPlaceId(placeId).orElseThrow());

            StatDataDTO statToPersist;
            Optional<StatDataDTO> existingStatOpt = placeDTO.getStatsData().stream()
                    .filter(stat -> stat.getUserId().equals(userId))
                    .findFirst();

            if (existingStatOpt.isPresent()) {
                String rate = existingStatOpt.get().getRateChoice() != null ? existingStatOpt.get().getRateChoice() : "NA";
                statToPersist = new StatDataDTO(userId, rate, forms);
                log.info("Updated existing forms for user ID: {} in place ID: {}", userId, placeId);
            } else {
                statToPersist = new StatDataDTO(userId, "NA", forms);
                log.info("Added new forms for user ID: {} in place ID: {} with NA rate choice", userId, placeId);
            }

            if (placeRepository.existsStatDataByPlaceIdAndUserId(placeId, userId)) {
                return placeRepository.updateExistingStatData(placeId, userId, statDataMapper.toStatData(statToPersist));
            }
            return placeRepository.addStatDataIfUserNotExists(placeId, userId, statDataMapper.toStatData(statToPersist));
        } catch (Exception e) {
            log.info("Error adding/updating forms for place ID: {} and user ID: {}. Error: {}", placeId, userId, e.getMessage());
            log.error("Exception stack trace: ", e);
            throw new RuntimeException(e);
        }
    }
    // Este metodo se encarga de agregar una nueva elección de calificación (LIKE o DISLIKE) a un lugar específico para un usuario determinado.
    // Primero, verifica si el lugar existe en la base de datos.
    // Si el lugar existe, se obtiene la información del lugar y se agrega la nueva elección de calificación a la lista de datos estadísticos del lugar.
    // Si el usuario ya tiene una elección de calificación para ese lugar, se actualiza la elección existente en lugar de agregar una nueva.
    // Finalmente, se actualiza el lugar en la base de datos con la nueva información de calificación.
    // Si el usuario no tiene realizado el formulario, se agrega la calificacion y el formulario queda como una lista de strings "NA" (no aplica) y se agrega la nueva elección de calificación a la lista de datos estadísticos del lugar.
    @Override
    public long addRateChoiceToPlace(String placeId, String userId, RateChoiceDTO rateChoice) {
        try {
            List<String> defaultForms = List.of("NA", "NA", "NA", "NA", "NA", "NA");

            if (!placeRepository.existsPlaceByPlaceId(placeId)) {
                log.warn("Place with ID: {} does not exist. Creating place with initial rate choice.", placeId);

                PlaceDTO newPlace = new PlaceDTO(
                        placeId,
                        List.of(),
                        0.0f,
                        List.of(new StatDataDTO(userId, rateChoice.getRateChoice(), defaultForms))
                );

                placeMapper.toPlaceDTO(placeRepository.save(placeMapper.toPlace(newPlace)));
                log.info("Created new place with ID: {} and initial rate choice for user ID: {}", placeId, userId);
                return 0;
            }

            log.info("Adding/updating rate choice for place ID: {} and user ID: {}", placeId, userId);

            PlaceDTO placeDTO = placeMapper.toPlaceDTO(
                    placeRepository.findByPlaceId(placeId).orElseThrow()
            );

            StatDataDTO statToPersist;
            Optional<StatDataDTO> existingStatOpt = placeDTO.getStatsData().stream()
                    .filter(stat -> stat.getUserId().equals(userId))
                    .findFirst();

            if (existingStatOpt.isPresent()) {
                statToPersist = new StatDataDTO(userId, rateChoice.getRateChoice(), existingStatOpt.get().getForms());
                log.info("Updated existing rate choice for user ID: {} in place ID: {}", userId, placeId);
            } else {
                statToPersist = new StatDataDTO(userId, rateChoice.getRateChoice(), defaultForms);
                log.info("Added new rate choice for user ID: {} in place ID: {} with NA forms", userId, placeId);
            }

            if (placeRepository.existsStatDataByPlaceIdAndUserId(placeId, userId)) {
                return placeRepository.updateExistingStatData(placeId, userId, statDataMapper.toStatData(statToPersist));
            }
            return placeRepository.addStatDataIfUserNotExists(placeId, userId, statDataMapper.toStatData(statToPersist));

        } catch (Exception e) {
            log.info("Error adding/updating rate choice for place ID: {} and user ID: {}. Error: {}", placeId, userId, e.getMessage());
            log.error("Exception stack trace: ", e);
            throw new RuntimeException(e);
        }
    }
}
