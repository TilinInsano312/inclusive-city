package com.gateway.gateway_service.exception;

public class NoAuthorizedException extends RuntimeException {
    public NoAuthorizedException(String message) {
        super(message);
    }
}
