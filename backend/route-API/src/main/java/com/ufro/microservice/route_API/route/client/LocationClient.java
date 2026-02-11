package com.ufro.microservice.route_API.route.client;

import com.ufro.microservice.route_API.route.common.response.ApiResponse;
import com.ufro.microservice.route_API.route.dto.IncidenceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "location-api", url = "gateway-service:8080/inclusive/api/v1/location/incidence")
public interface LocationClient {

    @GetMapping("/all")
    ApiResponse<List<IncidenceDTO>> getAllIncidences();
}
