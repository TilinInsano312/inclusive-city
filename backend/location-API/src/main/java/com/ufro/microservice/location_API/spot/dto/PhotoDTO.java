package com.ufro.microservice.location_API.spot.dto;

public class PhotoDTO {

    private byte[] data;
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
