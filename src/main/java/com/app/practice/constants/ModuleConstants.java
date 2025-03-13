package com.app.practice.constants;

public class ModuleConstants {

    // Auth
    public static final String USER_REGISTERED_SUCCESS = "User registered successfully";
    public static final String USER_LOGIN_SUCCESS = "User logged in successfully";
    public static final String USERNAME_TAKEN = "Username is already taken!";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String REFRESH_TOKEN_EXPIRED = "Refresh token is expired";

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    // Video Engagement
    public static final String VIDEO_STATS_FETCHED_SUCCESSFULLY = "Video engagement stats fetched successfully";

    // Error Messages
    public static final String VIDEO_ALREADY_PRESENT = "Video already present with title: ";
    public static final String VIDEO_NOT_FOUND = "Video not found";
    public static final String VIDEO_DELISTED = "Video is delisted.";
    public static final String INVALID_DIRECTOR_NAME = "Director name cannot be empty.";
    public static final String INVALID_SEARCH_PHRASE = "Invalid search phrase";
    public static final String GENERIC_ERROR_MESSAGE = "An error occurred while processing the request";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    // Status codes
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int NOT_FOUND_STATUS_CODE = 404;
    public static final int SERVER_ERROR_STATUS_CODE = 500;

    // Log Messages
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

    // Add more constants here as needed
}

