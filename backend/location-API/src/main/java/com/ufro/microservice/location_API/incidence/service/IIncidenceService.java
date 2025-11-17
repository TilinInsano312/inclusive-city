package com.ufro.microservice.location_API.incidence.service;

import com.ufro.microservice.location_API.incidence.dto.IncidenceDTO;

import java.util.List;

public interface IIncidenceService {
    IncidenceDTO insertAIncidence(IncidenceDTO incidenceDTO);
    List<IncidenceDTO> getAllIncidences();
}
