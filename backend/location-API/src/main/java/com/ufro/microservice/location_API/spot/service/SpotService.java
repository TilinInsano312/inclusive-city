package com.ufro.microservice.location_API.spot.service;

import com.ufro.microservice.location_API.spot.dto.DTOSpot;
import com.ufro.microservice.location_API.spot.mapper.ISpotMapper;
import com.ufro.microservice.location_API.spot.repository.ISpotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService {
    private final ISpotRepository spotRepository;
    private final ISpotMapper spotMapper;

    public SpotService(ISpotRepository spotRepository, ISpotMapper spotMapper) {
        this.spotRepository = spotRepository;
        this.spotMapper = spotMapper;
    }
    public DTOSpot insertASpot(DTOSpot spot) {
        return spotMapper.toDTOSpot(
                spotRepository.insert(spotMapper.toSpot(spot))
        );
    }
    public List<DTOSpot> getAllSpotsById(String idUser) {
        return spotRepository.findByUserId(idUser)
                .stream()
                .map(spotMapper::toDTOSpot)
                .toList();
    }
}
