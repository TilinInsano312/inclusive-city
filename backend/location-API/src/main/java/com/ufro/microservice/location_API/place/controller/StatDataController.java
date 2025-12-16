package com.ufro.microservice.location_API.place.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.place.dto.StatDataDTO;
import com.ufro.microservice.location_API.place.service.IStatDataService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequestMapping("inclusive/api/v1/locations/place/statdata/")
@RestController
public class StatDataController {
    private static final Logger log = LoggerFactory.getLogger(StatDataController.class);
    private final IStatDataService statDataService;

    public StatDataController(IStatDataService statDataService) {
        this.statDataService = statDataService;
    }

    //Conectar con @AuthenticatedPrincipal para obtener el userId del usuario logeado
    @GetMapping("update/{placeId}" )
    public ResponseEntity<ApiResponse<Long>> updateStatDataRateChoice(@PathVariable String placeId) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        statDataService.updateReview(statDataService.calculateStatData(placeId), placeId)
                )
        );
    }
    //reemplazar con la userid del authenticacion principal
    @PostMapping("save/{placeId}" )
    public ResponseEntity<ApiResponse<Long>> saveStatDataForms(@RequestBody @Valid StatDataDTO statDataDTO, @PathVariable String placeId, @AuthenticationPrincipal Jwt token) {
        String userId = token.getClaims().get("userId").toString();
        log.info("User ID from token: {}", userId);
        log.info("Received StatDataDTO: {}", statDataDTO);
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        statDataService.addStatDataToPlace(statDataDTO, placeId, userId)
                )
        );
    }
}
