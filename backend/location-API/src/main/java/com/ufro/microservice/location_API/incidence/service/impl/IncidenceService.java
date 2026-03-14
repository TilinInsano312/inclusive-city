package com.ufro.microservice.location_API.incidence.service.impl;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import com.ufro.microservice.location_API.exception.ConflictException;
import com.ufro.microservice.location_API.incidence.dto.IncidenceDTO;
import com.ufro.microservice.location_API.incidence.mapper.IIncidenceMapper;
import com.ufro.microservice.location_API.incidence.repository.IIncidenceRepository;
import com.ufro.microservice.location_API.incidence.service.IIncidenceService;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IncidenceService implements IIncidenceService {
    private final IIncidenceRepository incidenceRepository;
    private final IIncidenceMapper incidenceMapper;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(IncidenceService.class);


    public IncidenceService(IIncidenceRepository incidenceRepository, IIncidenceMapper incidenceMapper) {
        this.incidenceRepository = incidenceRepository;
        this.incidenceMapper = incidenceMapper;
    }

    @Override
    public IncidenceDTO insertAIncidence(IncidenceDTO incidenceDTO) {
        setExpirationDateByIncidence(incidenceDTO);
        log.info("Expiration date set to: {}", incidenceDTO.getExpiresAt());
        if (isADiffIncidenceWithSameLocation(incidenceDTO)) {
            log.warn("An incidence of a different type already exists at the same location: {}", incidenceDTO.getLocation());
            incidenceMapper.convertToDTO(incidenceRepository.insert(incidenceMapper.convertToEntity(incidenceDTO)));
            return incidenceDTO;
        }
        if (isSameIncidenceWithSameLocation(incidenceDTO)) {
            log.warn("An identical incidence already exists at the same location: {}", incidenceDTO.getLocation());
            throw new ConflictException("An identical incidence already exists at the same location.");
        }
        incidenceMapper.convertToDTO(incidenceRepository.insert(incidenceMapper.convertToEntity(incidenceDTO)));
        return incidenceDTO;
    }

    @Override
    public IncidenceDTO updateAIncidence(IncidenceDTO incidenceDTO) {
        IncidenceDTO existingIncidence = incidenceMapper.convertToDTO(incidenceRepository
                .findByPlaceIdAndIncidence(incidenceDTO.getPlaceId(), incidenceDTO.getIncidence())
                .orElseThrow(() -> {
                    log.warn("No se encontró incidencia para actualizar: placeId={}, tipo={}",
                            incidenceDTO.getPlaceId(), incidenceDTO.getIncidence());
                    // Aquí puedes lanzar tu propia excepción personalizada como NotFoundException
                    return new IllegalArgumentException("La incidencia que intentas actualizar no existe.");
                }));
        setExpirationDateByIncidence(incidenceDTO);
        existingIncidence.setExpiresAt(incidenceDTO.getExpiresAt());
        IncidenceDTO updatedIncidence = incidenceMapper.convertToDTO(incidenceRepository.save(incidenceMapper.convertToEntity(existingIncidence)));
        log.info("Tiempo de expiración actualizado a {} para la incidencia {}",
                updatedIncidence.getExpiresAt(), updatedIncidence.getIncidence());
        return updatedIncidence;
    }

    @Override
    public List<IncidenceDTO> getAllIncidences() {
        return incidenceRepository.findAll()
                .stream()
                .map(incidenceMapper::convertToDTO)
                .toList();
    }
    @Override
    public List<IncidenceDTO> getIncedenceBySector(
            LocationDTO pointNorthEast, LocationDTO pointSouthWest) {
        Point puntoEsquinaSupIzq = new Point(pointNorthEast.getLongitude(), pointNorthEast.getLatitude());
        Point puntoEsquinaInfDer = new Point(pointSouthWest.getLongitude(), pointSouthWest.getLatitude());
        Box box = new Box(puntoEsquinaSupIzq, puntoEsquinaInfDer);
        log.info("Searching incidences within box: {}", box);
        List<IncidenceDTO> incidencesInBox =
                incidenceMapper.convertToDTOList(incidenceRepository.findByLocationWithin(box));
        log.info("Found {} incidences within the box.", incidencesInBox.size());
        return incidencesInBox;
    }
    private void setExpirationDateByIncidence(IncidenceDTO incidenceDTO) {
        Instant ahora = Instant.now();
        Instant fechaExpiracion;
        switch (incidenceDTO.getIncidence()) {
            case "ALUMBRADO_PUBLICO":
                fechaExpiracion = ahora.plus(30, ChronoUnit.MINUTES);
                incidenceDTO.setExpiresAt(fechaExpiracion);
                break;
            case "OBRA", "ESCOMBROS":
                fechaExpiracion = ahora.plus(45, ChronoUnit.MINUTES);
                incidenceDTO.setExpiresAt(fechaExpiracion);
                break;
            case "BLOQUEDO_RUTA", "NO_RAMPA", "RAMPA_DANADA":
                fechaExpiracion = ahora.plus(1, ChronoUnit.HOURS);
                incidenceDTO.setExpiresAt(fechaExpiracion);
                break;
            case "RAMPA_BLOQUEADA":
                fechaExpiracion = ahora.plus(15, ChronoUnit.MINUTES);
                incidenceDTO.setExpiresAt(fechaExpiracion);
                break;
            default:
                throw new IllegalArgumentException("Invalid incidence type: " + incidenceDTO.getIncidence());
        }
    }
    private boolean isADiffIncidenceWithSameLocation(IncidenceDTO newIncidence) {
        List<IncidenceDTO> existingIncidences = getAllIncidences();
        for (IncidenceDTO existing : existingIncidences) {
            if (existing.getLocation().equals(newIncidence.getLocation()) &&
                    !existing.getIncidence().equals(newIncidence.getIncidence())) {
                return true;
            }
        }
        return false;
    }
    private boolean isSameIncidenceWithSameLocation(IncidenceDTO newIncidence) {
        List<IncidenceDTO> existingIncidences = getAllIncidences();
        for (IncidenceDTO existing : existingIncidences) {
            if (existing.getLocation().equals(newIncidence.getLocation()) &&
                    existing.getIncidence().equals(newIncidence.getIncidence())) {
                return true;
            }
        }
        return false;
    }
}
