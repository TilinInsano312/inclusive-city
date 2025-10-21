package com.ufro.microservice.location_API.incidence.controller;

import com.ufro.microservice.location_API.incidence.dto.IncidenceDTO;
import com.ufro.microservice.location_API.incidence.service.IncidenceService;
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
    public ResponseEntity<IncidenceDTO> insertAIncidence(@RequestBody IncidenceDTO incidenceDTO) {
        if (incidenceDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(incidenceService.insertAIncidence(incidenceDTO));
    }

    @GetMapping("/incidence/all" )
    public ResponseEntity<List<IncidenceDTO>> getAllIncidences() {
        return ResponseEntity.ok(incidenceService.getAllIncidences());
    }

}
