package com.ufro.microservice.route_API.route.client;

import com.ufro.microservice.route_API.route.dto.IncidenceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "location-API", url = "localhost:8070/inclusive/api/v1/incidence")
public interface LocationClient {

    @GetMapping("/all")
    List<IncidenceDTO> getAllIncidences();
}
