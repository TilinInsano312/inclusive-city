package com.ufro.microservice.location_API.spot.mapper;
import com.ufro.microservice.location_API.spot.dto.SpotDTO;
import com.ufro.microservice.location_API.spot.model.Spot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISpotMapper {
    Spot toSpot(SpotDTO spotDTO);
    SpotDTO toDTOSpot(Spot spot);
}
