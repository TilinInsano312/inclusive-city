package com.ufro.microservice.location_API.spot.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.spot.dto.DTOSpot;
import com.ufro.microservice.location_API.spot.repository.ISpotRepository;
import com.ufro.microservice.location_API.spot.service.SpotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para gestionar las operaciones relacionadas con los spots (ubicaciones no establecimientos)
@RestController
@RequestMapping("inclusive/api/v1/locations/")
public class SpotController {
    private final SpotService spotService;
    private final ISpotRepository spotRepository;

    public SpotController(SpotService spotService, ISpotRepository spotRepository) {
        this.spotService = spotService;
        this.spotRepository = spotRepository;
    }

    @PostMapping("saves")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<DTOSpot>> insertASpot(@RequestBody @Valid DTOSpot dtoSpot) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        this.spotService.insertASpot(dtoSpot)
                )
        );
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<ApiResponse<List<DTOSpot>>> getSpotByIdUser(@PathVariable @Valid String idUser) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        this.spotService.getAllSpotsById(idUser)
                )
        );

    }
}
