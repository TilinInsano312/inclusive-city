package com.ufro.microservice.authentication_service.exceptions;

public class RegisterConflictException extends RuntimeException {
  public RegisterConflictException(String message) {
    super(message);
  }
}
