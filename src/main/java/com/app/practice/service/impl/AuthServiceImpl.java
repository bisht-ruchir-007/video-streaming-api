package com.app.practice.service.impl;

import com.app.practice.constants.ModuleConstants;
import com.app.practice.model.request.RefreshTokenRequest;
import com.app.practice.model.request.UserCredentials;
import com.app.practice.entity.User;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.repository.UserRepository;
import com.app.practice.security.JwtUtil;
import com.app.practice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Optional;

/**
 * Service implementation for authentication-related functionalities including user registration, login, and token refresh.
 * This class provides concrete methods to register a new user, log in a user, and refresh the JWT tokens.
 * <p>
 * Author: Ruchir Bisht
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // Logger instance for logging events related to authentication.
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final JwtUtil jwtUtil;  // Utility class for JWT token generation and validation
    private final UserRepository userRepository;  // Repository for user-related database operations
    private final AuthenticationManager authenticationManager;  // Authentication manager for authenticating login requests
    private final PasswordEncoder passwordEncoder;  // Password encoder to securely store user passwords


    /**
     * Registers a new user in the system.
     *
     * @param userCredentials the credentials for the new user (username and password)
     * @return a GenericResponse containing the status of the registration process and JWT tokens
     */
    @Override
    public GenericResponse<?> registerUser(UserCredentials userCredentials) {
        // Check if the user already exists
        Optional<User> existingUser = userRepository.findByUsername(userCredentials.getUsername());
        if (existingUser.isPresent()) {
            LOGGER.warn("Registration failed - Username '{}' is already taken.", userCredentials.getUsername());
            return GenericResponse.error(ModuleConstants.USERNAME_TAKEN, HttpStatus.BAD_REQUEST);
        }

        // Create and save the new user
        User newUser = new User();
        newUser.setUsername(userCredentials.getUsername());
        newUser.setPassword(passwordEncoder.encode(userCredentials.getPassword()));  // Encrypt the password
        newUser.setRole(ModuleConstants.ROLE_USER);  // Default role for new users

        userRepository.save(newUser);
        LOGGER.info("User '{}' registered successfully.", userCredentials.getUsername());

        // Generate tokens for the new user
        String token = jwtUtil.generateToken(userCredentials.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userCredentials.getUsername());

        // Return success response with JWT tokens
        return GenericResponse.success(Map.of(
                ModuleConstants.AUTH_MESSAGE, ModuleConstants.USER_REGISTERED_SUCCESS,
                ModuleConstants.ACCESS_AUTH_TOKEN, token,
                ModuleConstants.REFRESH_AUTH_TOKEN, refreshToken), HttpStatus.CREATED);
    }

    /**
     * Authenticates a user and generates JWT tokens.
     *
     * @param userCredentials the credentials provided by the user (username and password)
     * @return a GenericResponse containing the JWT access token and refresh token
     */
    @Override
    public GenericResponse<?> loginUser(UserCredentials userCredentials) {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword())
        );

        // Retrieve user details from the repository
        UserDetails userDetails = userRepository.findByUsername(userCredentials.getUsername())
                .orElseThrow(() -> {
                    LOGGER.error("Login failed - User '{}' not found.", userCredentials.getUsername());
                    return new IllegalArgumentException(ModuleConstants.USER_NOT_FOUND);
                });

        // Generate tokens for the authenticated user
        String token = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        LOGGER.info("User '{}' logged in successfully.", userCredentials.getUsername());

        // Return success response with JWT tokens
        return GenericResponse.success(Map.of(
                ModuleConstants.AUTH_MESSAGE, ModuleConstants.USER_LOGIN_SUCCESS,
                ModuleConstants.ACCESS_AUTH_TOKEN, token,
                ModuleConstants.REFRESH_AUTH_TOKEN, refreshToken), HttpStatus.OK);
    }

    /**
     * Refreshes the JWT access token using a valid refresh token.
     *
     * @param refreshTokenReq the request containing the refresh token
     * @return a GenericResponse containing new JWT access and refresh tokens
     */
    @Override
    public GenericResponse<?> refreshToken(RefreshTokenRequest refreshTokenReq) {
        String refreshToken = refreshTokenReq.getRefreshToken();
        String username = jwtUtil.extractUsername(refreshToken);

        // Check if the refresh token is expired
        if (jwtUtil.isTokenExpired(refreshToken)) {
            return GenericResponse.error(ModuleConstants.REFRESH_TOKEN_EXPIRED, HttpStatus.FORBIDDEN);
        }

        // Generate new access and refresh tokens
        String newAccessToken = jwtUtil.generateToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        // Return success response with new tokens
        return GenericResponse.success(Map.of(
                ModuleConstants.ACCESS_AUTH_TOKEN, newAccessToken,
                ModuleConstants.REFRESH_AUTH_TOKEN, newRefreshToken), HttpStatus.OK);
    }
}
