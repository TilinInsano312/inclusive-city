package com.ufro.microservice.location_API.exception;

public class ErrorUploadFileException extends RuntimeException {
    public ErrorUploadFileException(String message) {
        super(message);
    }
}
