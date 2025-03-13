package com.app.practice.model.request;

/**
 * Model class for handling refresh token requests.
 * This class is used to pass the refresh token in API requests to refresh the user session.
 * <p>
 * Author: Ruchir Bisht
 */
public class RefreshTokenRequest {

    private String refreshToken;

    /**
     * Constructor to create a new RefreshTokenRequest with the provided refresh token.
     *
     * @param refreshToken the refresh token to be used for session renewal.
     */
    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Getter for the refresh token.
     *
     * @return the refresh token.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Setter for the refresh token.
     *
     * @param refreshToken the refresh token to be set.
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Default constructor.
     */
    public RefreshTokenRequest() {
    }
}
