package com.ufro.microservice.location_API.spot.controller;

import com.ufro.microservice.location_API.common.ApiResponse;
import com.ufro.microservice.location_API.spot.dto.DTOSpot;
import com.ufro.microservice.location_API.spot.repository.ISpotRepository;
import com.ufro.microservice.location_API.spot.service.SpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<DTOSpot>> insertASpot(@RequestBody DTOSpot dtoSpot) {

        if (dtoSpot == null) {
            ApiResponse<DTOSpot> responseError400 = new ApiResponse<>(dtoSpot, "Invalid spot data");
            return ResponseEntity.badRequest().body(responseError400);
        }
        ApiResponse<DTOSpot> response = new ApiResponse<>(
                this.spotService.insertASpot(dtoSpot));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<ApiResponse<List<DTOSpot>>> getSpotByIdUser(@PathVariable String idUser) {
        if (idUser == null || idUser.isEmpty()) {
            ApiResponse<List<DTOSpot>> responseError400 = new ApiResponse<>( null, "Invalid format");
            return ResponseEntity.badRequest().body(responseError400);
        }
        if (!spotRepository.existsById(idUser)) {
            ApiResponse<List<DTOSpot>> responseError404 = new ApiResponse<>( null, "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError404);
        }
        ApiResponse<List<DTOSpot>> response = new ApiResponse<>(
                this.spotService.getAllSpotsById(idUser));
        return ResponseEntity.ok().body(response);

    }
}
