package com.app.practice.model.response;

import org.springframework.http.HttpStatus;

public class GenericResponse<T> {

    private String status;
    private int statusCode;
    private T data;
    private String error;

    public GenericResponse() {
    }

    public GenericResponse(String status, int statusCode, T data, String error) {
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
        this.error = error;
    }

    // Getter and Setter methods

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    // Static methods for creating success and error responses
    public static <T> GenericResponse<T> success(T data, HttpStatus status) {
        return new GenericResponse<>("success", status.value(), data, null);
    }

    public static <T> GenericResponse<T> error(String errorMessage, HttpStatus status) {
        return new GenericResponse<>("error", status.value(), null, errorMessage);
    }
}

