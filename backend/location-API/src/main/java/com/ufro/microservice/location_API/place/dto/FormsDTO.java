package com.ufro.microservice.location_API.place.dto;

import java.util.List;

public class FormsDTO {
    List<String> forms;

    public FormsDTO(List<String> forms) {
        this.forms = forms;
    }

    public List<String> getForms() {
        return forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }
}
