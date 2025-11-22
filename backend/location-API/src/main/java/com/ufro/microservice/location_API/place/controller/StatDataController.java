package com.ufro.microservice.location_API.place.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.place.dto.StatDataDTO;
import com.ufro.microservice.location_API.place.service.IStatDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("inclusive/api/v1/locations/place/statdata/")
@RestController
public class StatDataController {
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
    @PostMapping("save/{placeId}/{userId}" )
    public ResponseEntity<ApiResponse<Long>> saveStatDataForms(@RequestBody StatDataDTO statDataDTO, @PathVariable String placeId,@PathVariable String userId) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        statDataService.addStatDataToPlace(statDataDTO, placeId, userId)
                )
        );
    }
}
