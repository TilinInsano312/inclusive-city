package com.ufro.microservice.location_API.incidence.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.incidence.dto.IncidenceDTO;
import com.ufro.microservice.location_API.incidence.dto.IncidenceRequestDTO;
import com.ufro.microservice.location_API.incidence.dto.LocationSectorRequest;
import com.ufro.microservice.location_API.incidence.service.IIncidenceService;
import com.ufro.microservice.location_API.incidence.service.impl.IncidenceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inclusive/api/v1/location/incidence")
public class IncidenceController {
    private static final Logger log = LoggerFactory.getLogger(IncidenceController.class);
    private final IIncidenceService incidenceService;
    public IncidenceController(IncidenceService incidenceService) {
        this.incidenceService = incidenceService;
    }

    //Todo: @berAxz Revisar si la creacion de ...RequestDTO a ...DTO va en el controller o en el service
    @PostMapping("/insert" )
    public ResponseEntity<ApiResponse<IncidenceDTO>> insertAIncidence(@RequestBody @Valid IncidenceRequestDTO incidenceRequestDTO, @AuthenticationPrincipal Jwt token) {
        String userId = token.getClaims().get("userId").toString();
        IncidenceDTO incidenceDTO = new IncidenceDTO(
                incidenceRequestDTO.getPlaceId(),
                incidenceRequestDTO.getLocation(),
                incidenceRequestDTO.getIncidence(),
                null,
                userId,
                incidenceRequestDTO.getImage()
        );
        return ResponseEntity.status(201).body(new ApiResponse<>(incidenceService.insertAIncidence(incidenceDTO)));
    }

    @GetMapping("/all" )
    public ResponseEntity<ApiResponse<List<IncidenceDTO>>> getAllIncidences() {
        return ResponseEntity.ok().body(new ApiResponse<>(incidenceService.getAllIncidences()));
    }
    @PostMapping("/sector" )
    public ResponseEntity<ApiResponse<List<IncidenceDTO>>> getIncidenceBySector(
            @RequestBody LocationSectorRequest locationSectorRequest) {
        log.info("Received request for incidences in sector: NE("
                + locationSectorRequest.getPointNorthEast().getLatitude() + ", "
                + locationSectorRequest.getPointNorthEast().getLongitude() + "), SW("
                + locationSectorRequest.getPointSouthWest().getLatitude() + ", "
                + locationSectorRequest.getPointSouthWest().getLongitude() + ")"
        );
        return ResponseEntity.ok().body(new ApiResponse<>(
                incidenceService.getIncedenceBySector(
                        locationSectorRequest.getPointNorthEast(),
                        locationSectorRequest.getPointSouthWest()
                )));
    }



}
