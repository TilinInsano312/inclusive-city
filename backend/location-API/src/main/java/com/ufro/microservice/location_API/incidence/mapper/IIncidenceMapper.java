package com.ufro.microservice.location_API.incidence.mapper;

import com.ufro.microservice.location_API.incidence.dto.IncidenceDTO;
import com.ufro.microservice.location_API.incidence.model.Incidence;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IIncidenceMapper {
    Incidence convertToEntity(IncidenceDTO incidenceDTO);
    IncidenceDTO convertToDTO(Incidence incidence);
}
