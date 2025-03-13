package com.app.practice.model.response;

import org.springframework.http.HttpStatus;

/**
 * A generic response wrapper class for sending responses with status, data, and error messages.
 * This class can be used for standardizing the structure of responses in the application.
 * <p>
 * Author: Ruchir Bisht
 */
public class GenericResponse<T> {

    private String status;
    private int statusCode;
    private T data;
    private String error;

    /**
     * Default constructor.
     */
    public GenericResponse() {
    }

    /**
     * Constructor to create a GenericResponse object with all fields.
     *
     * @param status     the status of the response (success or error).
     * @param statusCode the HTTP status code of the response.
     * @param data       the data to be returned in the response.
     * @param error      the error message if any error occurs.
     */
    public GenericResponse(String status, int statusCode, T data, String error) {
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
        this.error = error;
    }

    // Getter and Setter methods for each field

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

    /**
     * Creates a success response with the given data and status.
     *
     * @param data   the data to be returned in the response.
     * @param status the HTTP status.
     * @return a GenericResponse object with success status.
     */
    public static <T> GenericResponse<T> success(T data, HttpStatus status) {
        return new GenericResponse<>("success", status.value(), data, null);
    }

    /**
     * Creates an error response with the given error message and status.
     *
     * @param errorMessage the error message to be included in the response.
     * @param status       the HTTP status.
     * @return a GenericResponse object with error status.
     */
    public static <T> GenericResponse<T> error(String errorMessage, HttpStatus status) {
        return new GenericResponse<>("error", status.value(), null, errorMessage);
    }
}
