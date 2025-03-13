package com.app.practice.service;

import com.app.practice.model.request.RefreshTokenRequest;
import com.app.practice.model.request.UserCredentials;
import com.app.practice.model.response.GenericResponse;

import java.util.Map;

public interface AuthService {

    public GenericResponse<?> registerUser(UserCredentials userCredentials);

    public GenericResponse<?> loginUser(UserCredentials userCredentials);

    public GenericResponse<?> refreshToken(RefreshTokenRequest refreshTokenReq);
}
