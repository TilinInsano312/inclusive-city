package com.ufro.microservice.location_API.spot.controller;

import com.ufro.microservice.location_API.common.ApiResponse;
import com.ufro.microservice.location_API.spot.dto.PlaceDetailDTO;
import com.ufro.microservice.location_API.spot.service.IPlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("inclusive/api/v1/place/")
@RestController
public class PlaceController {
    private final IPlaceService placeService;

    public PlaceController(IPlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("{placeId}")
    public ResponseEntity<ApiResponse<PlaceDetailDTO>> getPlaceById(@PathVariable String placeId) {
        return ResponseEntity.ok().body(new ApiResponse<>(placeService.getPlaceDetails(placeId)));
    }
}
