package com.ufro.microservice.location_API.spot.model;

import com.ufro.microservice.location_API.spot.model.enums.ChoiceRate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class StatData {
    private String userId;
    //Valor por defecto ChoiceRate.NA
    private String rateChoice= ChoiceRate.NA.name();
    private List<Form> forms;

    public StatData(String userId, String rateChoice, List<Form> forms) {
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

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }
}
