package com.ufro.microservice.location_API.spot.service.impl;

import com.ufro.microservice.location_API.spot.dto.CustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SaveCustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SpotDTO;
import com.ufro.microservice.location_API.spot.mapper.ICustomSpotMapper;
import com.ufro.microservice.location_API.spot.mapper.ISpotMapper;
import com.ufro.microservice.location_API.spot.repository.ICustomSpotRepository;
import com.ufro.microservice.location_API.spot.repository.ISpotRepository;
import com.ufro.microservice.location_API.spot.service.ISpotService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService implements ISpotService {
    private final ISpotRepository spotRepository;
    private final ISpotMapper spotMapper;
    private final ICustomSpotMapper customSpotMapper;
    private final ICustomSpotRepository customSpotRepository;

    public SpotService(ISpotRepository spotRepository, ISpotMapper spotMapper, ICustomSpotMapper customSpotMapper, ICustomSpotRepository customSpotRepository) {
        this.spotRepository = spotRepository;
        this.spotMapper = spotMapper;
        this.customSpotMapper = customSpotMapper;
        this.customSpotRepository = customSpotRepository;
    }

    @Override
    public SpotDTO insertASpot(SpotDTO spot) {
        return spotMapper.toDTOSpot(
                spotRepository.insert(spotMapper.toSpot(spot))
        );
    }

    @Override
    public List<SpotDTO> getAllSpotsById(String idUser) {
        return spotRepository.findByUserId(idUser)
                .stream()
                .map(spotMapper::toDTOSpot)
                .toList();
    }

    @Override
    public CustomSpotDTO insertACustomSpot(CustomSpotDTO customSpotDTO) {
        return customSpotMapper.toCustomSpotDTO(customSpotRepository.insert(customSpotMapper.toCustomSpot(customSpotDTO)));
    }

    @Override
    public SaveCustomSpotDTO saveASpotInACustomSpot(SpotDTO spotDTO, String listName) {
        SaveCustomSpotDTO saveCustomSpotDTO = customSpotMapper.toSaveCustomSpotDTO(
                customSpotRepository.findCustomSpotByUserIdAndListName(
                        spotDTO.userId(),
                        listName)
        );
        saveCustomSpotDTO.setSpots(spotDTO);
        return customSpotMapper.toSaveCustomSpotDTO(customSpotRepository.save(
                customSpotMapper.toCustomSpotFromSaveDTO(saveCustomSpotDTO)));
    }

    @Override
    public List<CustomSpotDTO> getAllCustomSpotsById(String idUser) {
        return customSpotRepository.findByUserId(idUser)
                .stream()
                .map(customSpotMapper::toCustomSpotDTO)
                .toList();
    }

}
