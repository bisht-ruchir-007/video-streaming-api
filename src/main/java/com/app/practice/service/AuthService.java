package com.app.practice.service;

import com.app.practice.entity.User;
import com.app.practice.exception.InvalidCredentialsException;
import com.app.practice.exception.UserAlreadyExistsException;
import com.app.practice.model.request.UserCredentials;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface AuthService {

    public Map<String, String> registerUser(UserCredentials userCredentials);
    public Map<String, String> loginUser(UserCredentials userCredentials);

}
