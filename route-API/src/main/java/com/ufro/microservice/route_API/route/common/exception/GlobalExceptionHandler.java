package com.ufro.microservice.route_API.route.common.exception;


import com.ufro.microservice.route_API.route.common.response.ApiError;
import com.ufro.microservice.route_API.route.exception.ExternalServiceException;
import com.ufro.microservice.route_API.route.exception.InvalidCoordinatesException;
import com.ufro.microservice.route_API.route.exception.RouteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<ApiError<ErrorResponse>> handleRouteNotFound(RouteNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        ApiError<ErrorResponse> response = new ApiError<>(errorResponse);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCoordinatesException.class)
    public ResponseEntity<ApiError<ErrorResponse>> handleInvalidCoordinates(InvalidCoordinatesException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        ApiError<ErrorResponse> response = new ApiError<>(errorResponse);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ApiError<ErrorResponse>> handleExternalService(ExternalServiceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        ApiError<ErrorResponse> response = new ApiError<>(errorResponse);
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Internal Server Error: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
