package com.app.practice.service;

import com.app.practice.model.request.RefreshTokenRequest;
import com.app.practice.model.request.UserCredentials;

import java.util.Map;

public interface AuthService {

    public Map<String, String> registerUser(UserCredentials userCredentials);

    public Map<String, String> loginUser(UserCredentials userCredentials);

    public Map<String, String> refreshToken(RefreshTokenRequest refreshTokenReq);
}
