package com.ufro.microservice.location_API.spot.dto;

import java.util.List;

public class AggregatedStatDTO {
    private List<StatCountDTO> rateCounts;
    private List<StatCountDTO> formCounts;

    // Getters y Setters
    public List<StatCountDTO> getRateCounts() { return rateCounts; }
    public void setRateCounts(List<StatCountDTO> rateCounts) { this.rateCounts = rateCounts; }
    public List<StatCountDTO> getFormCounts() { return formCounts; }
    public void setFormCounts(List<StatCountDTO> formCounts) { this.formCounts = formCounts; }
}
