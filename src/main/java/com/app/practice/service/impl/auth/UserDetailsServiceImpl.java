package com.app.practice.service.impl.auth;

import com.app.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementation for loading user details.
 * Implements the UserDetailsService interface to integrate with Spring Security.
 * This service is used to load user information from the database based on the username
 * for authentication and authorization purposes.
 * Author : Ruchir Bisht
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user details by username for authentication.
     * This method fetches the user from the repository by the provided username.
     * If the user is not found, a UsernameNotFoundException is thrown.
     *
     * @param username the username of the user to be loaded
     * @return UserDetails the user details found by the username
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the repository by username.
        // If not found, throw UsernameNotFoundException.
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
