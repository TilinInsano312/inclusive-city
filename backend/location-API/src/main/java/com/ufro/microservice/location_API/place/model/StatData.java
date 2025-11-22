package com.ufro.microservice.location_API.place.model;

import com.ufro.microservice.location_API.place.model.enums.ChoiceRate;

import java.util.List;

public class StatData {
    private String userId;
    //Valor por defecto ChoiceRate.NA
    private String rateChoice= ChoiceRate.NA.name();
    private List<String> forms;

    public StatData(String userId, String rateChoice, List<String> forms) {
        this.userId = userId;
        this.rateChoice = rateChoice;
        this.forms = forms;
    }

    public StatData() {
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
