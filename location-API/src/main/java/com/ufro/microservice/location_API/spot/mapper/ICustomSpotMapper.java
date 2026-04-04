package com.ufro.microservice.location_API.spot.mapper;

import com.ufro.microservice.location_API.spot.dto.CustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SaveCustomSpotDTO;
import com.ufro.microservice.location_API.spot.model.CustomSpot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICustomSpotMapper {
    CustomSpot toCustomSpot(CustomSpotDTO customSpotDTO);
    CustomSpotDTO toCustomSpotDTO(CustomSpot customSpot);
    SaveCustomSpotDTO toSaveCustomSpotDTO(CustomSpot customSpot);
    CustomSpot toCustomSpotFromSaveDTO(SaveCustomSpotDTO saveCustomSpotDTO);
}
