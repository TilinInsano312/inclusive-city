package com.ufro.microservice.location_API.spot.model;

public class Form {
    private Integer page;
    private String choice;

    public Form() {
    }

    public Form(int page, String choice) {
        this.page = page;
        this.choice = choice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
