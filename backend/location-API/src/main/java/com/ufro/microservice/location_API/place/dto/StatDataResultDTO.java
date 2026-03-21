package com.ufro.microservice.location_API.place.dto;

import java.util.List;

public class StatDataResultDTO {
    private float rateChoice;
    private List<String> forms;

    public StatDataResultDTO(float rateChoice, List<String> forms) {
        this.rateChoice = rateChoice;
        this.forms = forms;
    }

    public StatDataResultDTO() {
    }

    public float getRateChoice() {
        return rateChoice;
    }

    public void setRateChoice(float rateChoice) {
        this.rateChoice = rateChoice;
    }

    public List<String> getForms() {
        return forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    @Override
    public String toString() {
        return "StatDataResultDTO{" +
                "rateChoice=" + rateChoice +
                ", forms=" + forms +
                '}';
    }
}
