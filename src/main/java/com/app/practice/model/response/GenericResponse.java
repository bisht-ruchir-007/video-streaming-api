package com.app.practice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * A generic response wrapper class for sending responses with status, data, and error messages.
 * This class can be used for standardizing the structure of responses in the application.
 * <p>
 * Author: Ruchir Bisht
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {

    private String status;
    private int statusCode;
    private T data;
    private String error;

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
