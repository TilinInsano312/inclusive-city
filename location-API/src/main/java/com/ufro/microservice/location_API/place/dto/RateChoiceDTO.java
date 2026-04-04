package com.ufro.microservice.location_API.place.dto;

import com.ufro.microservice.location_API.place.model.enums.ChoiceRate;

public class RateChoiceDTO {
    private String rateChoice= ChoiceRate.NA.name();

    public RateChoiceDTO(String rateChoice) {
        this.rateChoice = rateChoice;
    }

    public String getRateChoice() {
        return rateChoice;
    }

    public void setRateChoice(String rateChoice) {
        this.rateChoice = rateChoice;
    }
}
