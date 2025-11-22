package com.ufro.microservice.location_API.place.dto;

import com.ufro.microservice.location_API.place.model.enums.ChoiceRate;

import java.util.List;

public class StatDataDTO {

    private String userId;
    //Valor por defecto ChoiceRate.NA
    private String rateChoice= ChoiceRate.NA.name();
    private List<String> forms;

    public StatDataDTO(String userId, String rateChoice, List<String> forms) {
        this.userId = userId;
        this.rateChoice = rateChoice;
        this.forms = forms;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRateChoice() {
        return rateChoice;
    }

    public void setRateChoice(String rateChoice) {
        this.rateChoice = rateChoice;
    }

    public List<String> getForms() {
        return forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }
}
