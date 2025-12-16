package com.ufro.microservice.location_API.place.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class StatDataResultDTO {
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private float averageRate;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private List<String> formStatistics;

    public StatDataResultDTO(float averageRate, List<String> formStatistics) {
        this.averageRate = averageRate;
        this.formStatistics = formStatistics;
    }

    public float getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(float averageRate) {
        this.averageRate = averageRate;
    }

    public List<String> getFormStatistics() {
        return formStatistics;
    }

    public void setFormStatistics(List<String> formStatistics) {
        this.formStatistics = formStatistics;
    }
}
