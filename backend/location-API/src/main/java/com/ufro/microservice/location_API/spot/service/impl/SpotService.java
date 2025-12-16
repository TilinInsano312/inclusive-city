package com.ufro.microservice.location_API.spot.service.impl;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import com.ufro.microservice.location_API.common.mapper.ILocationMapper;
import com.ufro.microservice.location_API.exception.NotFoundException;
import com.ufro.microservice.location_API.spot.dto.CustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SaveCustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SpotDTO;
import com.ufro.microservice.location_API.spot.mapper.ICustomSpotMapper;
import com.ufro.microservice.location_API.spot.mapper.ISpotMapper;
import com.ufro.microservice.location_API.spot.repository.ICustomSpotRepository;
import com.ufro.microservice.location_API.spot.repository.ISpotRepository;
import com.ufro.microservice.location_API.spot.service.ISpotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SpotService implements ISpotService {
    private final ISpotRepository spotRepository;
    private final ISpotMapper spotMapper;
    private final ILocationMapper locationMapper;
    private final ICustomSpotMapper customSpotMapper;
    private final ICustomSpotRepository customSpotRepository;

    public SpotService(ISpotRepository spotRepository, ISpotMapper spotMapper, ILocationMapper locationMapper, ICustomSpotMapper customSpotMapper, ICustomSpotRepository customSpotRepository) {
        this.spotRepository = spotRepository;
        this.spotMapper = spotMapper;
        this.locationMapper = locationMapper;
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
        if (idUser == null || idUser.isEmpty()) {
            log.warn("getAllSpotsById called with null or empty idUser");
            throw new NotFoundException("User Not Found");
        }
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

    @Override
    public long deleteSpotByLocation(LocationDTO location, String userId ) {
        log.info("Deleting spot at location: {} for userId: {}", location, userId);
        return spotRepository.deleteSpotByLocationAndUserId(locationMapper.toLocation(location), userId);
    }

    @Override
    public long deleteListCustomSpotByLocation(String listName, String userId) {
        log.info("Deleting custom spot list: {} for userId: {}", listName, userId);
        return customSpotRepository.deleteCustomSpotByListNameAndUserId(listName, userId);
    }

    @Override
    public long deleteCustomSpotByLocation(String listName, String userId, LocationDTO location) {
        log.info("Deleting spot at location: {} from custom spot list: {} for userId: {}", location, listName, userId);
        return customSpotRepository.updateCustomSpotByListNameAndUserId(listName, userId, locationMapper.toLocation(location));
    }

}
