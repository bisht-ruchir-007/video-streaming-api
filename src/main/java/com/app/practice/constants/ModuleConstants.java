package com.app.practice.constants;

/*
    Code Author : Ruchir Bisht

    This class contains all the constant values used across the application for various modules.
    Constants help ensure consistency throughout the application and avoid hardcoding values.

    - Auth: Contains constants related to user authentication and registration.
    - Video Engagement: Constants related to video engagement stats.
    - Error Messages: Predefined error messages for common scenarios in the application.
    - Status Codes: Standard HTTP status codes used in responses.
    - Log Messages: Predefined log messages for various operations related to video management.
 */

public class ModuleConstants {

    /*
        Auth
     */
    public static final String USER_REGISTERED_SUCCESS = "User registered successfully";
    public static final String USER_LOGIN_SUCCESS = "User logged in successfully";
    public static final String USERNAME_TAKEN = "Username is already taken!";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String REFRESH_TOKEN_EXPIRED = "Refresh token is expired";

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    /*
        Error Messages
     */
    public static final String VIDEO_ALREADY_PRESENT = "Video already present with title: ";
    public static final String VIDEO_NOT_FOUND = "Video not found";
    public static final String VIDEO_DELISTED = "Video is delisted.";
    public static final String INVALID_DIRECTOR_NAME = "Director name cannot be empty.";
    public static final String INVALID_SEARCH_PHRASE = "Invalid search phrase";


    /*
     Log Messages
     */
    public static final String PUBLISHING_VIDEO = "Publishing new video: ";
    public static final String VIDEO_PUBLISHED_SUCCESSFULLY = "Video published successfully: ";
    public static final String EDITING_VIDEO = "Editing video with ID: ";
    public static final String VIDEO_EDITED_SUCCESSFULLY = "Video edited successfully: ";
    public static final String DELISTING_VIDEO = "Delisting video with ID: ";
    public static final String VIDEO_DELISTED_SUCCESSFULLY = "Video successfully delisted: ";
    public static final String LOADING_VIDEO = "Loading video with ID: ";
    public static final String PLAYING_VIDEO = "Playing video with ID: ";
    public static final String LISTING_ALL_VIDEOS = "Listing all videos (Page: {}, Size: {})";
    public static final String SEARCHING_VIDEOS = "Searching videos directed by: {} (Page: {}, Size: {})";

    /*
     Auth Constants
     */
    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_BEARER_PREFIX = "Bearer ";

    public static final String AUTH_MESSAGE = "message";
    public static final String ACCESS_AUTH_TOKEN = "accessToken";
    public static final String REFRESH_AUTH_TOKEN = "refreshToken";

    /*
     Hashing Constants
     */
    public static final String HASH_ALGO_NAME = "HmacSHA512";
    public static final String MESSAGE_DIGEST_ALGO_NAME = "SHA-512";

    private ModuleConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated.");
    }

}
