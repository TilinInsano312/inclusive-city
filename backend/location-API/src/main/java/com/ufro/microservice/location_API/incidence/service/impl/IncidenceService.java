package com.ufro.microservice.location_API.incidence.service.impl;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
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
        incidenceMapper.convertToDTO(incidenceRepository.insert(incidenceMapper.convertToEntity(incidenceDTO)));
        return incidenceDTO;
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
    //Todo: @berAxz Agregar mas tipos de incidencias y sus tiempos de expiracion
    private void setExpirationDateByIncidence(IncidenceDTO incidenceDTO) {
        Instant ahora = Instant.now();
        Instant fechaExpiracion;
        switch (incidenceDTO.getIncidence()) {
            case "CALLE_CERRADA":
                fechaExpiracion = ahora.plus(1, ChronoUnit.DAYS);
                incidenceDTO.setExpiresAt(fechaExpiracion);
                break;
            case "VEREDA_ROTA":
                fechaExpiracion = ahora.plus(3, ChronoUnit.DAYS);
                incidenceDTO.setExpiresAt(fechaExpiracion);
                break;
            case "ILUMINACION":
                fechaExpiracion = ahora.plus(12, ChronoUnit.HOURS);
                incidenceDTO.setExpiresAt(fechaExpiracion);
                break;
            default:
                throw new IllegalArgumentException("Invalid incidence type: " + incidenceDTO.getIncidence());
        }
    }
}
