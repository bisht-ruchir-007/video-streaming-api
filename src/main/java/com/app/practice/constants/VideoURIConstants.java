package com.app.practice.constants;

/**
 * VideoURIConstants defines API endpoint paths for video-related operations.
 * This class contains constants for video-related API endpoints used in the application.
 * The constants help avoid hardcoding strings and improve maintainability and consistency.
 * <p>
 * - The VIDEO_BASE_PATH defines the base URL for video-related operations.
 * - Each endpoint constant represents a specific action (e.g., publishing, editing, delisting videos).
 * - The private constructor prevents instantiation of this constants class.
 */
public class VideoURIConstants {

    /*
        Base path for video-related APIs
     */
    public static final String VIDEO_BASE_PATH = "/api/v1/videos"; // Base path for all video-related APIs

    /*
        Endpoints for video operations
     */
    public static final String PUBLISH_VIDEO_ENDPOINT = "/publish";
    public static final String EDIT_VIDEO_METADATA_ENDPOINT = "/edit/{id}";
    public static final String DELIST_VIDEO_ENDPOINT = "/delist/{id}";
    public static final String LOAD_VIDEO_ENDPOINT = "/{id}";
    public static final String PLAY_VIDEO_ENDPOINT = "/{id}/play";
    public static final String LIST_VIDEOS_ENDPOINT = "";
    public static final String SEARCH_BY_DIRECTOR = "/director";
    public static final String SEARCH_VIDEO_ENDPOINT = "/search";

    private VideoURIConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated.");
    }
}
