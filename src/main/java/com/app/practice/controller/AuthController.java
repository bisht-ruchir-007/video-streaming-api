package com.app.practice.controller;

import com.app.practice.constants.AuthURIConstants;
import com.app.practice.model.request.UserCredentials;
import com.app.practice.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AuthController handles user authentication and registration.
 */
@RestController
@RequestMapping(AuthURIConstants.AUTH_BASE_PATH)
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handles user registration.
     *
     * @param userCredentials The user's registration details.
     * @return ResponseEntity with a success message and JWT token.
     * @throws IllegalArgumentException if the username is already taken.
     */
    @PostMapping(AuthURIConstants.REGISTER_ENDPOINT)
    public ResponseEntity<Map<String, String>> register(@RequestBody UserCredentials userCredentials) {
        LOGGER.info("User registration request received for username: {}", userCredentials.getUsername());
        Map<String, String> response = authService.registerUser(userCredentials);
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
    public ResponseEntity<Map<String, String>> login(@RequestBody UserCredentials userCredentials) {
        LOGGER.info("User login request received for username: {}", userCredentials.getUsername());

        Map<String, String> response = authService.loginUser(userCredentials);

        return ResponseEntity.ok(response);
    }
}
