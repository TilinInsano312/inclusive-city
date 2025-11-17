package com.ufro.microservice.location_API.incidence.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.incidence.dto.IncidenceDTO;
import com.ufro.microservice.location_API.incidence.service.impl.IncidenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inclusive/api/v1")
public class IncidenceController {
    private final IncidenceService incidenceService;
    public IncidenceController(IncidenceService incidenceService) {
        this.incidenceService = incidenceService;
    }

    @PostMapping("/incidence" )
    public ResponseEntity<ApiResponse<IncidenceDTO>> insertAIncidence(@RequestBody IncidenceDTO incidenceDTO) {
        return ResponseEntity.status(201).body(new ApiResponse<>(incidenceService.insertAIncidence(incidenceDTO)));
    }

    @GetMapping("/incidence/all" )
    public ResponseEntity<ApiResponse<List<IncidenceDTO>>> getAllIncidences() {
        return ResponseEntity.ok().body(new ApiResponse<>(incidenceService.getAllIncidences()));
    }

}
