package com.ufro.microservice.location_API.spot.service;

import com.ufro.microservice.location_API.spot.dto.DTOSpot;

import java.util.List;

public interface ISpotService {
    DTOSpot insertASpot(DTOSpot spot);
    List<DTOSpot> getAllSpotsById(String idUser);

}
