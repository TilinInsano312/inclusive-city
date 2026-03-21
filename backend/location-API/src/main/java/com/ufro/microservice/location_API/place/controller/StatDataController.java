package com.ufro.microservice.location_API.place.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.place.dto.ReviewDTO;
import com.ufro.microservice.location_API.place.dto.StatDataDTO;
import com.ufro.microservice.location_API.place.service.IStatDataService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("inclusive/api/v1/location/place/statdata/")
@RestController
public class StatDataController {
    private static final Logger log = LoggerFactory.getLogger(StatDataController.class);
    private final IStatDataService statDataService;

    public StatDataController(IStatDataService statDataService) {
        this.statDataService = statDataService;
    }

    @GetMapping("update/{placeId}" )
    public ResponseEntity<ApiResponse<Long>> updateStatDataRateChoice(@PathVariable String placeId) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        statDataService.updateReview(statDataService.calculateStatData(placeId), placeId)
                )
        );
    }
    @PostMapping("save/{placeId}" )
    public ResponseEntity<ApiResponse<Long>> saveStatDataForms(@RequestBody @Valid ReviewDTO reviewDTO, @PathVariable String placeId, @AuthenticationPrincipal String uid) {
        StatDataDTO statDataDTO = new StatDataDTO("",reviewDTO.getRateChoice(), reviewDTO.getForms());
        statDataDTO.setUserId(uid);
        log.info("User ID from token: {}", uid);
        log.info("Received StatDataDTO: {}", statDataDTO);
        return ResponseEntity.status(201).body(
                new ApiResponse<>(
                        statDataService.addStatDataToPlace(statDataDTO, placeId, uid)
                )
        );
    }


}
