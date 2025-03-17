package com.app.practice.constants;

/**
 * Code Author : Ruchir Bisht
 * AuthURIConstants defines API endpoint paths for authentication-related operations.
 */
public class AuthURIConstants {

    /*
     Base path for authentication-related APIs
     */
    public static final String AUTH_BASE_PATH = "/api/v1/auth";

    public static final String LOGIN_ENDPOINT = "/login";
    public static final String REGISTER_ENDPOINT = "/register";
    public static final String REFRESH_TOKEN = "/refresh-token";

    private AuthURIConstants() {
        throw new UnsupportedOperationException("This is a constants class and cannot be instantiated.");
    }
}
