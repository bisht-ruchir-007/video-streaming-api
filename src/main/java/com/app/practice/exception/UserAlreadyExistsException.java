package com.app.practice.exception;

/**
 * Custom exception to be thrown when a user already exists in the system.
 * This exception is used to handle cases where an attempt to create a user fails
 * because the username or email is already taken.
 * <p>
 * Author: Ruchir Bisht
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * Constructor to create a new UserAlreadyExistsException with the provided message.
     *
     * @param message the error message indicating the reason the user already exists.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
