package com.gateway.gateway_service.common;

import com.gateway.gateway_service.exception.NoAuthorizedException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.ServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(NoAuthorizedException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                "Bad Credentials: "+ ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(jakarta.ws.rs.ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(jakarta.ws.rs.ForbiddenException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                "Forbidden: "+ ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleServiceUnavailableException(ServiceUnavailableException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                "Service Unavailable: "+ ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
