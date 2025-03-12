package com.app.practice.utils;

public class URIConstants {
    public static final String BASE_API = "/api/v1/videos";

    public static final String PUBLISH = "/publish";
    public static final String EDIT_METADATA = "/edit/{id}";
    public static final String DELIST = "/delist/{id}";
    public static final String LOAD_VIDEO = "/{id}";
    public static final String PLAY_VIDEO = "/{id}/play";
    public static final String LIST_VIDEOS = ""; // Root path (same as BASE_API)
    public static final String SEARCH = "/search"; // Query parameters should be handled in @RequestParam
    public static final String ENGAGEMENT = "/{id}/engagement";
}
