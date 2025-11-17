package com.ufro.microservice.location_API.place.mapper;

import com.ufro.microservice.location_API.place.dto.PlaceDTO;
import com.ufro.microservice.location_API.place.model.Place;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPlaceMapper {
    PlaceDTO toPlaceDTO(Place place);
    default Place toPlace(PlaceDTO dto) {
        Place place = new Place();
        place.setPlaceId(dto.getPlaceId());
        place.setMedals(dto.getMedals());
        place.setRating(dto.getRating());
        place.setStatsData(dto.getStatsData());  // Asegúrate de que esto exista
        return place;
    }
}
