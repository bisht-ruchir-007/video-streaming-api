package com.app.practice.controller;

import com.app.practice.constants.AuthURIConstants;
import com.app.practice.entity.User;
import com.app.practice.model.request.UserCredentials;
import com.app.practice.repository.UserRepository;
import com.app.practice.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * AuthController handles user authentication and registration.
 */
@RestController
@RequestMapping(AuthURIConstants.AUTH_BASE_PATH)
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtUtil jwtUtil, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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

        if (userRepository.findByUsername(userCredentials.getUsername()).isPresent()) {
            LOGGER.warn("Registration failed - Username '{}' is already taken.", userCredentials.getUsername());
            throw new IllegalArgumentException("Username is already taken!");
        }

        User newUser = new User();
        newUser.setUsername(userCredentials.getUsername());
        newUser.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        newUser.setRole("USER"); // Default role

        userRepository.save(newUser);
        LOGGER.info("User '{}' registered successfully.", userCredentials.getUsername());

        String token = jwtUtil.generateToken(userCredentials.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("token", token);

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

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword())
        );

        UserDetails userDetails = userRepository.findByUsername(userCredentials.getUsername())
                .orElseThrow(() -> {
                    LOGGER.error("Login failed - User '{}' not found.", userCredentials.getUsername());
                    return new IllegalArgumentException("User not found");
                });

        String token = jwtUtil.generateToken(userDetails.getUsername());
        LOGGER.info("User '{}' logged in successfully.", userCredentials.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("message", "User logged in successfully");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
