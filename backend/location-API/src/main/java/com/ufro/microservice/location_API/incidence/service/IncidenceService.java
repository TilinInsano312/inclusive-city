package com.ufro.microservice.location_API.incidence.service;

import com.ufro.microservice.location_API.incidence.dto.IncidenceDTO;
import com.ufro.microservice.location_API.incidence.model.Incidence;
import com.ufro.microservice.location_API.incidence.repository.IIncidenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidenceService {
    private final IIncidenceRepository incidenceRepository;

    public IncidenceService(IIncidenceRepository incidenceRepository) {
        this.incidenceRepository = incidenceRepository;
    }

    public IncidenceDTO insertAIncidence(IncidenceDTO incidenceDTO) {
        incidenceRepository.insert(convertToEntity(incidenceDTO));
        return incidenceDTO;
    }

    public List<IncidenceDTO> getAllIncidences() {
        return incidenceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    private IncidenceDTO convertToDTO(Incidence incidence) {
        return new IncidenceDTO(
                incidence.getId(),
                incidence.getPlace_id(),
                incidence.getLatitude(),
                incidence.getLongitude(),
                incidence.getIncidence(),
                incidence.getDate(),
                incidence.getIdUser(),
                incidence.getImage()
        );
    }
    private Incidence convertToEntity(IncidenceDTO incidenceDTO) {
        return new Incidence(
                incidenceDTO.id(),
                incidenceDTO.place_id(),
                incidenceDTO.latitude(),
                incidenceDTO.longitude(),
                incidenceDTO.incidence(),
                incidenceDTO.date(),
                incidenceDTO.idUser(),
                incidenceDTO.image()
        );
    }

}
