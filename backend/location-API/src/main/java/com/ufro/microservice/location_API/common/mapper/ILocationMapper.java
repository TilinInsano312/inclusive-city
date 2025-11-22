package com.ufro.microservice.location_API.common.mapper;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import com.ufro.microservice.location_API.common.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ILocationMapper {
    LocationDTO toLocationDTO(Location location);
    Location toLocation(LocationDTO locationDTO);
}
