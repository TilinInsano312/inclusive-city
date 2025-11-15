package com.ufro.microservice.location_API.common.response;

public class ApiError<ErrorResponse> {
    private boolean status;
    private ErrorResponse error;

    public ApiError(ErrorResponse error) {
        this.status = false;
        this.error = error;
    }

    public ApiError() {
        this.status = false;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
}