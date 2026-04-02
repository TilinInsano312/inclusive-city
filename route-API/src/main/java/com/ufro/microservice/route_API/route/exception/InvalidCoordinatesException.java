package com.ufro.microservice.route_API.route.exception;

public class InvalidCoordinatesException extends RuntimeException {
    public InvalidCoordinatesException(String message) {
        super(message);
    }
}
