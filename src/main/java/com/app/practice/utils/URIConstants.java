package com.app.practice.utils;

public class URIConstants {
    public static final String BASE_API = "/api/v1/videos";
    public static final String PUBLISH = BASE_API + "/publish";
    public static final String EDIT_METADATA = BASE_API + "/edit/{id}";
    public static final String DELIST = BASE_API + "/delist/{id}";
    public static final String LOAD_VIDEO = BASE_API + "/{id}";
    public static final String PLAY_VIDEO = BASE_API + "/{id}/play";
    public static final String LIST_VIDEOS = BASE_API;
    public static final String SEARCH = BASE_API + "/search";
    public static final String ENGAGEMENT = BASE_API + "/{id}/engagement";
}
