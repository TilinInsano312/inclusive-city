package com.ufro.microservice.location_API.place.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PhotoDTO {

    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private byte[] data;
    @NotNull(message = "Cannot be null")
    @NotBlank(message = "Cannot be blank")
    private String contentType;

    public PhotoDTO(byte[] data, String contentType) {
        this.data = data;
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }

}
