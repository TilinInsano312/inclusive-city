package com.ufro.microservice.location_API.spot.controller;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.spot.dto.*;
import com.ufro.microservice.location_API.spot.service.ISpotService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para gestionar las operaciones relacionadas con los spots (ubicaciones no establecimientos)
@Slf4j
@RestController
@RequestMapping("inclusive/api/v1/location/spot/")
public class SpotController {
    private final ISpotService spotService;

    public SpotController(ISpotService spotService) {
        this.spotService = spotService;
    }

    @PostMapping("insert")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<SpotDTO>> insertASpot(@RequestBody @Valid SpotRequestDTO spot, @AuthenticationPrincipal String uid) {
        SpotDTO spotDTO = new SpotDTO(uid, spot.spotName(),spot.placeId(), spot.address(), spot.location(), spot.type());
        log.info("Received request to insert spot: {} for userId: {}", spotDTO, uid);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        spotService.insertASpot(spotDTO)
                )
        );
    }

    @GetMapping("user-spot")
    public ResponseEntity<ApiResponse<List<SpotDTO>>> getSpotByIdUser(@AuthenticationPrincipal String uid) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        spotService.getAllSpotsById(uid)
                )
        );

    }
    @PostMapping("custom-spot/insert")
    public ResponseEntity<ApiResponse<CustomSpotDTO>> insertACustomSpot(@RequestBody @Valid CustomSpotRequestDTO customSpot, @AuthenticationPrincipal String uid) {
        CustomSpotDTO customSpotDTO = new CustomSpotDTO(customSpot.getListName(), uid, customSpot.getSpots());
        log.info("Received request to insert custom spot: {} for userId: {}", customSpotDTO, uid);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        spotService.insertACustomSpot(customSpotDTO)
                )
        );
    }

    @GetMapping("custom-spot")
    public ResponseEntity<ApiResponse<List<CustomSpotDTO>>> getAllCustomSpotsById(@AuthenticationPrincipal String uid) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        spotService.getAllCustomSpotsById(uid)
                )
        );
    }

    @PostMapping("custom-spot/save-spot/{listName}" )
    public ResponseEntity<ApiResponse<SaveCustomSpotDTO>> saveASpotInACustomSpot(@RequestBody @Valid SpotRequestDTO spot, @PathVariable("listName") String listName, @AuthenticationPrincipal String uid){
        SpotDTO spotDTO = new SpotDTO(uid, spot.spotName(),spot.placeId(), spot.address(), spot.location(), spot.type());
        log.info("Received request to save spot: {} in custom spot list: {} for userId: {}", spotDTO, listName, uid);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        spotService.saveASpotInACustomSpot(spotDTO, listName)
                )
        );
    }
    @DeleteMapping("delete/spot" )
    public ResponseEntity<ApiResponse<Long>> deleteSpotByLocation(@RequestBody @Valid LocationDTO location, @AuthenticationPrincipal String uid) {
        log.info("Received request to delete spot for userId: {} at location: {}", uid, location);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        spotService.deleteSpotByLocation(location, uid)
                )
        );
    }
    @DeleteMapping("delete/custom-spot/list/{listName}" )
    public ResponseEntity<ApiResponse<Long>> deleteListCustomSpotByLocation(@PathVariable("listName") String listName, @AuthenticationPrincipal String uid){
        log.info("Received request to delete custom spot list: {} for userId: {}", listName, uid);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        spotService.deleteListCustomSpotByLocation(listName, uid)
                )
        );
    }
    @DeleteMapping("delete/custom-spot/spot/{listName}" )
    public ResponseEntity<ApiResponse<Long>> deleteCustomSpotByLocation(@RequestBody @Valid LocationDTO location,
                                                                        @PathVariable("listName") String listName,
                                                                        @AuthenticationPrincipal String uid){
        log.info("Received request to delete custom spot in list: {} for userId: {} at location: {}", listName, uid, location);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        spotService.deleteCustomSpotByLocation(listName, uid, location)
                )
        );
    }

}
