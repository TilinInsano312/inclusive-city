package com.ufro.microservice.location_API.common.response;

public class ApiResponse<T> {
    private boolean status;
    private T data;

    public ApiResponse(T data) {
        this.status = true;
        this.data = data;
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
