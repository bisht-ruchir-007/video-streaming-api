package com.app.practice.service;

import com.app.practice.entity.User;
import com.app.practice.exception.InvalidCredentialsException;
import com.app.practice.exception.UserAlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface AuthService extends UserDetailsService {

    public Map<String, String> register(User user) throws UserAlreadyExistsException;

    public Map<String, String> login(String username, String password) throws InvalidCredentialsException;
}
