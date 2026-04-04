package com.ufro.microservice.location_API.spot.service.impl;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import com.ufro.microservice.location_API.common.mapper.ILocationMapper;
import com.ufro.microservice.location_API.exception.ConflictException;
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
        if (spotRepository.existsSpotByUserIdAndSpotName(spot.userId(), spot.spotName())) {
            log.warn("insertASpot called with existing spotName: {} for userId: {}", spot.spotName(), spot.userId());
            throw new ConflictException("Spot with the same name already exists for this user");
        }
        return spotMapper.toDTOSpot(
                spotRepository.insert(spotMapper.toSpot(spot))
        );
    }

    @Override
    public List<SpotDTO> getAllSpotsById(String idUser) {
        if (!spotRepository.existsByUserId(idUser)) {
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
        //Validacion si existe un customSpot con el mismo nombre para el mismo usuario
        if (customSpotRepository.existsCustomSpotByUserIdAndListName(customSpotDTO.getUserId(), customSpotDTO.getListName())) {
            log.warn("insertACustomSpot called with existing listName: {} for userId: {}", customSpotDTO.getListName(), customSpotDTO.getUserId());
            throw new ConflictException("Custom Spot List with the same name already exists for this user");
        }
        return customSpotMapper.toCustomSpotDTO(customSpotRepository.insert(customSpotMapper.toCustomSpot(customSpotDTO)));
    }

    @Override
    public SaveCustomSpotDTO saveASpotInACustomSpot(SpotDTO spotDTO, String listName) {
        if (!customSpotRepository.existsCustomSpotByUserIdAndListName(spotDTO.userId(), listName)) {
            log.warn("no exist a customSpot called listName: {} for userId: {}", listName, spotDTO.userId());
            throw new ConflictException("Custom Spot List does not exist for this user");
        }
        SaveCustomSpotDTO saveCustomSpotDTO = customSpotMapper.toSaveCustomSpotDTO(
                customSpotRepository.findCustomSpotByUserIdAndListName(
                        spotDTO.userId(),
                        listName)
        );
        //Validar que el spot con el mismo nombre ya existe en la lista
        for (SpotDTO spotInList : saveCustomSpotDTO.getSpots()) {
            if (spotInList.spotName().equals(spotDTO.spotName())) {
                log.warn("Spot with the same name: {} already exists in the custom spot list: {} for userId: {}", spotDTO.spotName(), listName, spotDTO.userId());
                throw new ConflictException("Spot with the same name already exists in the custom spot list for this user");
            }
        }
        saveCustomSpotDTO.setSpots(spotDTO);
        return customSpotMapper.toSaveCustomSpotDTO(customSpotRepository.save(
                customSpotMapper.toCustomSpotFromSaveDTO(saveCustomSpotDTO)));
    }

    @Override
    public List<CustomSpotDTO> getAllCustomSpotsById(String idUser) {
        if (!customSpotRepository.existsByUserId(idUser)) {
            log.warn("getAllCustomSpotsById called with null or empty idUser");
            throw new NotFoundException("User Not Found");
        }
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
