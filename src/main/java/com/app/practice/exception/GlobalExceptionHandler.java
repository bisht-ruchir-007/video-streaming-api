package com.app.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global Exception Handler for handling exceptions in the application.
 * This class uses `@RestControllerAdvice` to handle exceptions globally across all controllers.
 * It catches various exceptions like `ResourceNotFoundException`, `VideoNotFoundException`,
 * `InvalidCredentialsException`, `UserAlreadyExistsException`, and `VideoAlreadyPresentException`,
 * then returns a structured response with the error details.
 * <p>
 * Author: Ruchir Bisht
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Helper method to build the response entity with error details.
     *
     * @param status  the HTTP status code
     * @param message the error message
     * @return ResponseEntity containing the error details
     */
    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }

    /**
     * Exception handler for `ResourceNotFoundException`.
     * Returns a response entity with `NOT_FOUND` status and the exception message.
     *
     * @param ex the caught `ResourceNotFoundException`
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Exception handler for `InvalidCredentialsException`.
     * Returns a response entity with `NOT_FOUND` status and the exception message.
     *
     * @param ex the caught `InvalidCredentialsException`
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    /**
     * Exception handler for `UserAlreadyExistsException`.
     * Returns a response entity with `NOT_FOUND` status and the exception message.
     *
     * @param ex the caught `UserAlreadyExistsException`
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Exception handler for `VideoAlreadyPresentException`.
     * Returns a response entity with `NOT_FOUND` status and the exception message.
     *
     * @param ex the caught `VideoAlreadyPresentException`
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(VideoAlreadyPresentException.class)
    public ResponseEntity<Object> handleVideoAlreadyPresent(VideoAlreadyPresentException ex) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Exception handler for `VideoNotFoundException`.
     * Returns a response entity with `NOT_FOUND` status and a custom message.
     *
     * @param ex the caught `VideoNotFoundException`
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<Object> handleVideoNotFoundException(VideoNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Generic exception handler for any unhandled exceptions.
     * Returns a response entity with `INTERNAL_SERVER_ERROR` status and the exception message.
     *
     * @param ex the caught generic `Exception`
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
