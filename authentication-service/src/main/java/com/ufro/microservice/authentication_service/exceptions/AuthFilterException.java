package com.ufro.microservice.authentication_service.exceptions;

public class AuthFilterException extends RuntimeException {
    public AuthFilterException(String message) {
        super(message);
    }
}
