package com.app.practice.service;

import com.app.practice.model.request.RefreshTokenRequest;
import com.app.practice.model.request.UserCredentials;
import com.app.practice.model.response.GenericResponse;

/**
 * Interface for authentication-related services in the application.
 * This includes user registration, login, and token refresh functionality.
 * <p>
 * Author: Ruchir Bisht
 */
public interface AuthService {

    /**
     * Registers a new user in the system.
     *
     * @param userCredentials the user credentials (username and password) provided during registration
     * @return a GenericResponse indicating the result of the registration process
     * <p>
     * Method: registerUser
     * Purpose: Handles user registration by validating the provided credentials
     */
    public GenericResponse<?> registerUser(UserCredentials userCredentials);

    /**
     * Authenticates the user and generates a JWT access token for successful login.
     *
     * @param userCredentials the user credentials (username and password) provided during login
     * @return a GenericResponse containing the JWT access token if the login is successful
     * <p>
     * Method: loginUser
     * Purpose: Authenticates the user, verifies the provided credentials, and returns an access token
     */
    public GenericResponse<?> loginUser(UserCredentials userCredentials);

    /**
     * Refreshes the JWT access token using a valid refresh token.
     *
     * @param refreshTokenReq the request object containing the refresh token
     * @return a GenericResponse containing a new access token if the refresh token is valid
     * <p>
     * Method: refreshToken
     * Purpose: Refreshes the user's access token by verifying the provided refresh token and generating a new one
     */
    public GenericResponse<?> refreshToken(RefreshTokenRequest refreshTokenReq);
}
