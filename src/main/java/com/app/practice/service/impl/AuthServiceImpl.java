package com.app.practice.service.impl;


import com.app.practice.constants.ModuleConstants;
import com.app.practice.model.request.RefreshTokenRequest;
import com.app.practice.model.request.UserCredentials;
import com.app.practice.entity.User;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.repository.UserRepository;
import com.app.practice.security.JwtUtil;
import com.app.practice.service.AuthService;
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

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtUtil jwtUtil, UserRepository userRepository, AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public GenericResponse<?> registerUser(UserCredentials userCredentials) {
        Optional<User> existingUser = userRepository.findByUsername(userCredentials.getUsername());
        if (existingUser.isPresent()) {
            LOGGER.warn("Registration failed - Username '{}' is already taken.", userCredentials.getUsername());
            return GenericResponse.error(ModuleConstants.USERNAME_TAKEN, HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setUsername(userCredentials.getUsername());
        newUser.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        newUser.setRole(ModuleConstants.ROLE_USER); // Default role

        userRepository.save(newUser);
        LOGGER.info("User '{}' registered successfully.", userCredentials.getUsername());

        String token = jwtUtil.generateToken(userCredentials.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userCredentials.getUsername());

        return GenericResponse.success(Map.of(
                "message", ModuleConstants.USER_REGISTERED_SUCCESS,
                "accessToken", token,
                "refreshToken", refreshToken), HttpStatus.CREATED);
    }

    public GenericResponse<?> loginUser(UserCredentials userCredentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword())
        );

        UserDetails userDetails = userRepository.findByUsername(userCredentials.getUsername())
                .orElseThrow(() -> {
                    LOGGER.error("Login failed - User '{}' not found.", userCredentials.getUsername());
                    return new IllegalArgumentException(ModuleConstants.USER_NOT_FOUND);
                });

        String token = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        LOGGER.info("User '{}' logged in successfully.", userCredentials.getUsername());

        return GenericResponse.success(Map.of(
                "message", ModuleConstants.USER_LOGIN_SUCCESS,
                "accessToken", token,
                "refreshToken", refreshToken), HttpStatus.OK);
    }

    public GenericResponse<?> refreshToken(RefreshTokenRequest refreshTokenReq) {
        String refreshToken = refreshTokenReq.getRefreshToken();
        String username = jwtUtil.extractUsername(refreshToken);

        if (jwtUtil.isTokenExpired(refreshToken)) {
            return GenericResponse.error(ModuleConstants.REFRESH_TOKEN_EXPIRED, HttpStatus.FORBIDDEN);
        }

        String newAccessToken = jwtUtil.generateToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        return GenericResponse.success(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken), HttpStatus.OK);
    }
}
