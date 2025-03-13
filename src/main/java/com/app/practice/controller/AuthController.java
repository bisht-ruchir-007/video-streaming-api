package com.app.practice.controller;

import com.app.practice.constants.AuthURIConstants;
import com.app.practice.model.request.RefreshTokenRequest;
import com.app.practice.model.request.UserCredentials;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Code Author : Ruchir Bisht
 * AuthController handles user authentication and registration.
 */
@RestController
@RequestMapping(AuthURIConstants.AUTH_BASE_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    /**
     * Handles user registration.
     *
     * @param userCredentials The user's registration details.
     * @return ResponseEntity with a success message and JWT token.
     * @throws IllegalArgumentException if the username is already taken.
     */
    @PostMapping(AuthURIConstants.REGISTER_ENDPOINT)
    public ResponseEntity<GenericResponse<?>> register(@RequestBody UserCredentials userCredentials) {
        LOGGER.info("User registration request received for username: {}", userCredentials.getUsername());

        // Call service to register user and get response
        GenericResponse<?> response = authService.registerUser(userCredentials);

        // Return response with CREATED status
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Handles user login.
     *
     * @param userCredentials The user's login details.
     * @return ResponseEntity with a success message and JWT token.
     * @throws org.springframework.security.authentication.BadCredentialsException if authentication fails.
     */
    @PostMapping(AuthURIConstants.LOGIN_ENDPOINT)
    public ResponseEntity<GenericResponse<?>> login(@RequestBody UserCredentials userCredentials) {
        LOGGER.info("User login request received for username: {}", userCredentials.getUsername());

        // Call service to log in and get response
        GenericResponse<?> response = authService.loginUser(userCredentials);

        // Return response with OK status
        return ResponseEntity.ok(response);
    }

    /**
     * Handles refresh token request.
     *
     * @param tokenRequest The refresh token request.
     * @return ResponseEntity with new access and refresh tokens.
     */
    @PostMapping(AuthURIConstants.REFRESH_TOKEN)
    public ResponseEntity<GenericResponse<?>> refreshToken(@RequestBody RefreshTokenRequest tokenRequest) {

        // Call service to refresh token and get response
        GenericResponse<?> response = authService.refreshToken(tokenRequest);

        // Return response with OK status
        return ResponseEntity.ok(response);
    }
}
