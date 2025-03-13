package com.app.practice.exception;

/**
 * Custom exception to be thrown when a video is not found in the system.
 * This exception is used to handle cases where a video could not be located
 * in the system based on the provided criteria.
 * <p>
 * Author: Ruchir Bisht
 */
public class VideoNotFoundException extends Exception {

    /**
     * Constructor to create a new VideoNotFoundException with the provided message.
     *
     * @param message the error message indicating the reason the video was not found.
     */
    public VideoNotFoundException(String message) {
        super(message);
    }
}
