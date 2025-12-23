package com.ufro.microservice.location_API.place.mapper;

import com.ufro.microservice.location_API.place.dto.PlaceDTO;
import com.ufro.microservice.location_API.place.model.Place;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPlaceMapper {
    PlaceDTO toPlaceDTO(Place place);
    Place toPlace(PlaceDTO placeDTO);
}
