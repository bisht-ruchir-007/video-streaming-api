package com.app.practice.exception;

/**
 * Custom exception to be thrown when invalid credentials are provided.
 * This exception is used to handle cases where the user provides incorrect or invalid credentials,
 * such as during login or authentication processes.
 * <p>
 * Author: Ruchir Bisht
 */
public class InvalidCredentialsException extends Exception {

    /**
     * Constructor to create a new InvalidCredentialsException with the provided message.
     *
     * @param message the error message that describes the invalid credentials issue.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
