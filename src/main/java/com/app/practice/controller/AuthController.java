package com.app.practice.controller;

import com.app.practice.constants.AuthURIConstants;
import com.app.practice.model.request.RefreshTokenRequest;
import com.app.practice.model.request.UserCredentials;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Code Author : Ruchir Bisht
 * AuthController handles user authentication and registration.
 */
@RestController
@RequestMapping(AuthURIConstants.AUTH_BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for user authentication and token management")
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    /**
     * Handles user registration.
     *
     * @param userCredentials The user's registration details.
     * @return ResponseEntity with a success message and JWT token.
     */
    @Operation(summary = "Register a new user", description = "Registers a new user and returns a JWT token")
    @PostMapping(AuthURIConstants.REGISTER_ENDPOINT)
    public ResponseEntity<GenericResponse<?>> register(
            @Parameter(description = "User credentials for registration", required = true)
            @RequestBody UserCredentials userCredentials) {

        LOGGER.info("User registration request received for username: {}", userCredentials.getUsername());
        GenericResponse<?> response = authService.registerUser(userCredentials);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Handles user login.
     *
     * @param userCredentials The user's login details.
     * @return ResponseEntity with a success message and JWT token.
     */
    @Operation(summary = "Login user", description = "Authenticates the user and returns a JWT token")
    @PostMapping(AuthURIConstants.LOGIN_ENDPOINT)
    public ResponseEntity<GenericResponse<?>> login(
            @Parameter(description = "User credentials for login", required = true)
            @RequestBody UserCredentials userCredentials) {

        LOGGER.info("User login request received for username: {}", userCredentials.getUsername());
        GenericResponse<?> response = authService.loginUser(userCredentials);
        return ResponseEntity.ok(response);
    }

    /**
     * Handles refresh token request.
     *
     * @param tokenRequest The refresh token request.
     * @return ResponseEntity with new access and refresh tokens.
     */
    @Operation(summary = "Refresh access token", description = "Generates a new access token using a refresh token")
    @PostMapping(AuthURIConstants.REFRESH_TOKEN)
    public ResponseEntity<GenericResponse<?>> refreshToken(
            @Parameter(description = "Refresh token request payload", required = true)
            @RequestBody RefreshTokenRequest tokenRequest) {

        GenericResponse<?> response = authService.refreshToken(tokenRequest);
        return ResponseEntity.ok(response);
    }
}
