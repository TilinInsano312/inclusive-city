package com.ufro.microservice.location_API.spot.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.spot.dto.CustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SaveCustomSpotDTO;
import com.ufro.microservice.location_API.spot.dto.SpotDTO;
import com.ufro.microservice.location_API.spot.service.ISpotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para gestionar las operaciones relacionadas con los spots (ubicaciones no establecimientos)
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

    @GetMapping("{idUser}")
    public ResponseEntity<ApiResponse<List<SpotDTO>>> getSpotByIdUser(@PathVariable @Valid String idUser) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        this.spotService.getAllSpotsById(idUser)
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

    @GetMapping("custom-spot/{idUser} ")
    public ResponseEntity<ApiResponse<List<CustomSpotDTO>>> getAllCustomSpotsById(@PathVariable @Valid String idUser) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        this.spotService.getAllCustomSpotsById(idUser)
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

}
