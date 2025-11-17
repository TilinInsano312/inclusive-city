package com.ufro.microservice.location_API.spot.service;

import com.ufro.microservice.location_API.spot.dto.CustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SaveCustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SpotDTO;

import java.util.List;

public interface ISpotService {
    SpotDTO insertASpot(SpotDTO spot);
    List<SpotDTO> getAllSpotsById(String idUser);
    List<CustomSpotDTO> getAllCustomSpotsById(String idUser);
    CustomSpotDTO insertACustomSpot(CustomSpotDTO customSpotDTO);
    SaveCustomSpotDTO saveASpotInACustomSpot(SpotDTO spotDTO, String listName);

}
