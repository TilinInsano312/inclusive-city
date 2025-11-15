package com.ufro.microservice.location_API.spot.controller;

import com.ufro.microservice.location_API.common.response.ApiResponse;
import com.ufro.microservice.location_API.spot.service.IStatDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("inclusive/api/v1/locations/place/statdata/")
@RestController
public class StatDataController {
    private final IStatDataService statDataService;

    public StatDataController(IStatDataService statDataService) {
        this.statDataService = statDataService;
    }

    //Conectar con @AuthenticatedPrincipal para obtener el userId del usuario logeado
    @PatchMapping("statdata/update" )
    public ResponseEntity<ApiResponse<Boolean>> updateStatDataRateChoice(String placeId, String userId, String newRate) {
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        statDataService.updateStatDataRateChoice(placeId, userId, newRate)
                )
        );
    }
}
