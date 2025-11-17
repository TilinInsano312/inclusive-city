package com.ufro.microservice.location_API.spot.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.spot.dto.SpotDTO;
import com.ufro.microservice.location_API.spot.service.ISpotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para gestionar las operaciones relacionadas con los spots (ubicaciones no establecimientos)
@RestController
@RequestMapping("inclusive/api/v1/locations/")
public class SpotController {
    private final ISpotService spotService;

    public SpotController(ISpotService spotService) {
        this.spotService = spotService;
    }

    @PostMapping("saves")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<SpotDTO>> insertASpot(@RequestBody @Valid SpotDTO dtoSpot) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        this.spotService.insertASpot(dtoSpot)
                )
        );
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<ApiResponse<List<SpotDTO>>> getSpotByIdUser(@PathVariable @Valid String idUser) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        this.spotService.getAllSpotsById(idUser)
                )
        );

    }
}
