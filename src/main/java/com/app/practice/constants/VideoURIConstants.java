package com.app.practice.constants;

/**
 * VideoURIConstants defines API endpoint paths for video-related operations.
 */
public class VideoURIConstants {

    // Base path for video-related APIs
    public static final String VIDEO_BASE_PATH = "/api/v1/videos";

    // Endpoints for video operations
    public static final String PUBLISH_VIDEO_ENDPOINT = "/publish";
    public static final String EDIT_VIDEO_METADATA_ENDPOINT = "/edit/{id}";
    public static final String DELIST_VIDEO_ENDPOINT = "/delist/{id}";
    public static final String LOAD_VIDEO_ENDPOINT = "/{id}";
    public static final String PLAY_VIDEO_ENDPOINT = "/{id}/play";
    public static final String LIST_VIDEOS_ENDPOINT = ""; // Root path (same as VIDEO_BASE_PATH)
    public static final String SEARCH_BY_DIRECTOR = "/director";
    public static final String SEARCH_VIDEO_ENDPOINT = "/search"; // Query parameters should be handled in @RequestParam

    // Private constructor to prevent instantiation
    private VideoURIConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated.");
    }
}
