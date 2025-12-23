package com.ufro.microservice.location_API.incidence.service;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import com.ufro.microservice.location_API.incidence.dto.IncidenceDTO;
import com.ufro.microservice.location_API.incidence.dto.IncidenceRequestDTO;

import java.util.List;

public interface IIncidenceService {
    IncidenceDTO insertAIncidence(IncidenceDTO incidenceDTO);
    List<IncidenceDTO> getAllIncidences();
    List<IncidenceDTO> getIncedenceBySector(LocationDTO pointNorthEast, LocationDTO pointSouthWest);
}
