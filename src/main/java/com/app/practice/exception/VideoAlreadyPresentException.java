package com.app.practice.exception;

/**
 * Custom exception to be thrown when a video is already present in the system.
 * This exception is used to handle cases where an attempt to add a video fails
 * because the video already exists in the system.
 * <p>
 * Author: Ruchir Bisht
 */
public class VideoAlreadyPresentException extends Exception {

    /**
     * Constructor to create a new VideoAlreadyPresentException with the provided message.
     *
     * @param message the error message indicating the reason the video is already present.
     */
    public VideoAlreadyPresentException(String message) {
        super(message);
    }
}
