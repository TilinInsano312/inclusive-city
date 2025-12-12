package com.ufro.microservice.location_API.spot.controller;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.spot.dto.CustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SaveCustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SpotDTO;
import com.ufro.microservice.location_API.spot.service.ISpotService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public ResponseEntity<ApiResponse<SpotDTO>> insertASpot(@RequestBody @Valid SpotDTO dtoSpot) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        this.spotService.insertASpot(dtoSpot)
                )
        );
    }

    @GetMapping("user-spots")
    public ResponseEntity<ApiResponse<List<SpotDTO>>> getSpotByIdUser(@AuthenticationPrincipal Jwt token) {
        String userId = token.getClaims().get("userId").toString();
        return ResponseEntity.ok(
                new ApiResponse<>(
                        this.spotService.getAllSpotsById(userId)
                )
        );

    }

    @PostMapping("custom-spot/insert")
    public ResponseEntity<ApiResponse<CustomSpotDTO>> insertACustomSpot(@RequestBody @Valid CustomSpotDTO customSpotDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        this.spotService.insertACustomSpot(customSpotDTO)
                )
        );
    }

    @GetMapping("custom-spot")
    public ResponseEntity<ApiResponse<List<CustomSpotDTO>>> getAllCustomSpotsById(@AuthenticationPrincipal Jwt token) {
        String userId = token.getClaims().get("userId").toString();
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        this.spotService.getAllCustomSpotsById(userId)
                )
        );
    }
    @PostMapping("custom-spot/save-spot/{listName}" )
    public ResponseEntity<ApiResponse<SaveCustomSpotDTO>> saveASpotInACustomSpot(@RequestBody @Valid SpotDTO spotDTO, @PathVariable("listName") String listName) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        this.spotService.saveASpotInACustomSpot(spotDTO, listName)
                )
        );
    }
    @DeleteMapping("delete/spot" )
    public ResponseEntity<ApiResponse<Long>> deleteSpotByLocation(@RequestBody @Valid LocationDTO location, @AuthenticationPrincipal Jwt token) {
        String userId = token.getClaims().get("userId").toString();
        log.info("Received request to delete spot for userId: {} at location: {}", userId, location);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        this.spotService.deleteSpotByLocation(location, userId)
                )
        );
    }
    @DeleteMapping("custom-spot/list/{listName}" )
    public ResponseEntity<ApiResponse<Long>> deleteListCustomSpotByLocation(@PathVariable("listName") String listName, @AuthenticationPrincipal Jwt token){
        String userId = token.getClaims().get("userId").toString();
        log.info("Received request to delete custom spot list: {} for userId: {}", listName, userId);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        this.spotService.deleteListCustomSpotByLocation(listName, userId)
                )
        );
    }
    @DeleteMapping("custom-spot/spot/{listName}" )
    public ResponseEntity<ApiResponse<Long>> deleteCustomSpotByLocation(@RequestBody @Valid LocationDTO location,
                                                                        @PathVariable("listName") String listName,
                                                                        @AuthenticationPrincipal Jwt token){
        String userId = token.getClaims().get("userId").toString();
        log.info("Received request to delete custom spot in list: {} for userId: {} at location: {}", listName, userId, location);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        this.spotService.deleteCustomSpotByLocation(listName, userId, location)
                )
        );
    }

}
