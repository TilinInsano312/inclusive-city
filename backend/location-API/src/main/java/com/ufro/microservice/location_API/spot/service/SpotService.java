package com.ufro.microservice.location_API.spot.service;

import com.ufro.microservice.location_API.spot.dto.DTOSpot;
import com.ufro.microservice.location_API.spot.model.Spot;
import com.ufro.microservice.location_API.spot.repository.ISpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService {
    private final ISpotRepository spotRepository;

    public SpotService(ISpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    public DTOSpot insertASpot(DTOSpot spot) {
        return convertToDTO(
                spotRepository.insert(convertToEntity(spot))
        );
    }
    public List<DTOSpot> getAllSpotsById(String idUser) {
        return spotRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    private DTOSpot convertToDTO(Spot spot) {
        return new DTOSpot(
                spot.getId(),
                spot.getSpotName(),
                spot.getPlace_id(),
                spot.getAddress(),
                spot.getLatitude(),
                spot.getLongitude(),
                spot.getType()
        );
    }
    private Spot convertToEntity(DTOSpot dtoSpot) {
        Spot spot = new Spot();
        spot.setId(dtoSpot.id());
        spot.setSpotName(dtoSpot.spotName());
        spot.setPlace_id(dtoSpot.place_id());
        spot.setAddress(dtoSpot.address());
        spot.setLatitude(dtoSpot.latitude());
        spot.setLongitude(dtoSpot.longitude());
        spot.setType(dtoSpot.type());
        return spot;
    }
}
