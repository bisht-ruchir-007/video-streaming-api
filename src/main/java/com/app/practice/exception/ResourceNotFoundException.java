package com.app.practice.exception;

/**
 * Custom exception to be thrown when a resource is not found.
 * This exception is used to handle cases where a requested resource does not exist.
 * For example, when a video or user is not found in the system.
 * <p>
 * Author: Ruchir Bisht
 */
public class ResourceNotFoundException extends Exception {

    /**
     * Constructor to create a new ResourceNotFoundException with the provided message.
     *
     * @param message the error message that describes the resource not found issue.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
