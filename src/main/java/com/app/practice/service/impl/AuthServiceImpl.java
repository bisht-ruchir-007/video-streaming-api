package com.app.practice.service.impl;

import com.app.practice.entity.User;
import com.app.practice.model.request.RefreshTokenRequest;
import com.app.practice.model.request.UserCredentials;
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

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Map<String, String> registerUser(UserCredentials userCredentials) {

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
        String refreshToken = jwtUtil.generateRefreshToken(userCredentials.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("accessToken", token);
        response.put("refreshToken", refreshToken);
        return response;
    }

    public Map<String, String> loginUser(UserCredentials userCredentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword())
        );

        UserDetails userDetails = userRepository.findByUsername(userCredentials.getUsername())
                .orElseThrow(() -> {
                    LOGGER.error("Login failed - User '{}' not found.", userCredentials.getUsername());
                    return new IllegalArgumentException("User not found");
                });

        String token = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        LOGGER.info("User '{}' logged in successfully.", userCredentials.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("message", "User logged in successfully");
        response.put("accessToken", token);
        response.put("refreshToken", refreshToken);

        return response;
    }

    public Map<String, String> refreshToken(RefreshTokenRequest refreshTokenReq) {
        String refreshToken = refreshTokenReq.getRefreshToken();
        String username = jwtUtil.extractUsername(refreshToken);

        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new IllegalArgumentException("Refresh token is expired");
        }

        String newAccessToken = jwtUtil.generateToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        response.put("refreshToken", newRefreshToken);

        return response;
    }
}
