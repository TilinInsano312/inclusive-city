package com.ufro.microservice.location_API.spot.dto;

import com.ufro.microservice.location_API.common.dto.LocationDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SpotRequestDTO (@NotNull(message = "Cannot be null")
                              @NotBlank(message = "Cannot be blank")
                              String spotName,
                              @NotNull(message = "Cannot be null")
                              @NotBlank(message = "Cannot be blank")
                              String placeId,
                              @NotNull(message = "Cannot be null")
                              @NotBlank(message = "Cannot be blank")
                              String address,
                              @NotNull(message = "Cannot be null")
                              LocationDTO location,
                              @NotNull(message = "Cannot be null")
                              @NotBlank(message = "Cannot be blank")
                              String type) {
}
