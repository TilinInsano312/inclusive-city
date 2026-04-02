package com.ufro.microservice.authentication_service.common.response;

public class ApiResponse<T> {
    private boolean status;
    private T data;

    public ApiResponse(T data) {
        this.data = data;
        this.status = true;
    }

    public ApiResponse() {
        this.status = true;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
