package com.ufro.microservice.authentication_service.common.exception;

import com.ufro.microservice.authentication_service.exceptions.AuthFilterException;
import com.ufro.microservice.authentication_service.exceptions.ForbiddenException;
import com.ufro.microservice.authentication_service.exceptions.RegisterConflictException;
import jakarta.ws.rs.NotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                "Internal Server Error: "+ ex.getMessage() );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RegisterConflictException.class)
    public ResponseEntity<ErrorResponse> handleResgiterConflicException(RegisterConflictException ex)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                "Conflict Error: "+ ex.getMessage() );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Devuelve un 400 Bad Request
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.append(errorMessage).append(" ");
        });
        ErrorResponse errorResponse = new ErrorResponse(
                "Validation Failed: "+
                errors.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                "Bad Credentials: "+ ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleNotAuthorizedException(NotAuthorizedException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                "Not Authorized: "+ ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                "Forbidden: "+ ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(AuthFilterException.class)
    public ResponseEntity<ErrorResponse> handleAuthFilterException(AuthFilterException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                "Authentication Filter Error: "+ ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}