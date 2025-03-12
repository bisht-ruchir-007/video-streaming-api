package com.app.practice.controller;

import com.app.practice.constants.AuthURIConstants;
import com.app.practice.entity.User;
import com.app.practice.exception.InvalidCredentialsException;
import com.app.practice.exception.UserAlreadyExistsException;
import com.app.practice.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(AuthURIConstants.BASE_API)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(AuthURIConstants.REGISTER)
    public Map<String, String> register(@RequestBody User user) throws UserAlreadyExistsException {
        return authService.register(user);
    }

    @PostMapping(AuthURIConstants.LOGIN)
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) throws InvalidCredentialsException {
        return authService.login(username, password);
    }

}
