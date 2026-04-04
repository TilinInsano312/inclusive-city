package com.ufro.microservice.route_API.route.controller;

import com.ufro.microservice.route_API.route.common.response.ApiResponse;
import com.ufro.microservice.route_API.route.dto.RouteResponseDTO;
import com.ufro.microservice.route_API.route.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("inclusive/api/v1/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/principal")
    public ResponseEntity<ApiResponse<RouteResponseDTO>> getPrincipalRoute(
            @RequestParam String origin,
            @RequestParam String destination) {

        return ResponseEntity.ok().body(new ApiResponse<>(routeService.getPrincipalRoute(origin, destination)));
    }


    @GetMapping("/secure")
    public ResponseEntity<ApiResponse<RouteResponseDTO>> getSecondaryRoute(
            @RequestParam String origin,
            @RequestParam String destination) {

        return ResponseEntity.ok().body(new ApiResponse<>(routeService.getSecureRoute(origin, destination)));
    }
}

