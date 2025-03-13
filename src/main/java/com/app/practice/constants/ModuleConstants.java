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

    // Auth
    public static final String USER_REGISTERED_SUCCESS = "User registered successfully"; // Success message for user registration
    public static final String USER_LOGIN_SUCCESS = "User logged in successfully"; // Success message for user login
    public static final String USERNAME_TAKEN = "Username is already taken!"; // Error message when username is already taken
    public static final String USER_NOT_FOUND = "User not found"; // Error message when user is not found
    public static final String REFRESH_TOKEN_EXPIRED = "Refresh token is expired"; // Error message for expired refresh token

    public static final String ROLE_USER = "USER"; // Constant for the user role
    public static final String ROLE_ADMIN = "ADMIN"; // Constant for the admin role

    // Video Engagement
    public static final String VIDEO_STATS_FETCHED_SUCCESSFULLY = "Video engagement stats fetched successfully"; // Success message for fetching video engagement stats

    // Error Messages
    public static final String VIDEO_ALREADY_PRESENT = "Video already present with title: "; // Error message when video already exists
    public static final String VIDEO_NOT_FOUND = "Video not found"; // Error message when video is not found
    public static final String VIDEO_DELISTED = "Video is delisted."; // Error message for delisted video
    public static final String INVALID_DIRECTOR_NAME = "Director name cannot be empty."; // Error message for invalid director name
    public static final String INVALID_SEARCH_PHRASE = "Invalid search phrase"; // Error message for invalid search phrase
    public static final String GENERIC_ERROR_MESSAGE = "An error occurred while processing the request"; // Generic error message
    public static final String SUCCESS = "success"; // Constant for success
    public static final String ERROR = "error"; // Constant for error

    // Status codes
    public static final int SUCCESS_STATUS_CODE = 200; // HTTP status code for success
    public static final int NOT_FOUND_STATUS_CODE = 404; // HTTP status code for not found
    public static final int SERVER_ERROR_STATUS_CODE = 500; // HTTP status code for server error

    // Log Messages
    public static final String PUBLISHING_VIDEO = "Publishing new video: "; // Log message when publishing a new video
    public static final String VIDEO_PUBLISHED_SUCCESSFULLY = "Video published successfully: "; // Log message when video is published
    public static final String EDITING_VIDEO = "Editing video with ID: "; // Log message when editing a video
    public static final String VIDEO_EDITED_SUCCESSFULLY = "Video edited successfully: "; // Log message when video is edited
    public static final String DELISTING_VIDEO = "Delisting video with ID: "; // Log message when delisting a video
    public static final String VIDEO_DELISTED_SUCCESSFULLY = "Video successfully delisted: "; // Log message when video is delisted
    public static final String LOADING_VIDEO = "Loading video with ID: "; // Log message when loading a video
    public static final String PLAYING_VIDEO = "Playing video with ID: "; // Log message when playing a video
    public static final String LISTING_ALL_VIDEOS = "Listing all videos (Page: {}, Size: {})"; // Log message for listing all videos with pagination
    public static final String SEARCHING_VIDEOS = "Searching videos directed by: {} (Page: {}, Size: {})"; // Log message when searching for videos by director with pagination

    // Add more constants here as needed
}
