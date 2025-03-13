package com.app.practice;

import com.app.practice.model.request.RefreshTokenRequest;
import com.app.practice.model.request.UserCredentials;
import com.app.practice.model.response.GenericResponse;
import com.app.practice.service.impl.auth.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authServiceImpl;  // Assuming there's an implementation class AuthServiceImpl

    private UserCredentials userCredentials;
    private RefreshTokenRequest refreshTokenRequest;
    private GenericResponse<String> genericResponse;  // Explicitly define the type here

    @BeforeEach
    public void setUp() {
        userCredentials = new UserCredentials();
        userCredentials.setUsername("testuser");
        userCredentials.setPassword("password123");

        refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken("dummy_refresh_token");

        genericResponse = new GenericResponse<String>();
        genericResponse.setStatus("success");
    }

    @Test
    public void testRegisterUser() {
        //when(authServiceImpl.registerUser(userCredentials)).thenReturn(genericResponse);  // Use the specific type here

        GenericResponse<String> response = (GenericResponse<String>) authServiceImpl.registerUser(userCredentials);

        assertNotNull(response);
        assertEquals("success", response.getStatus());
        verify(authServiceImpl, times(1)).registerUser(userCredentials);
    }

    @Test
    public void testLoginUser() {
        // when(authServiceImpl.loginUser(userCredentials)).thenReturn(genericResponse);  // Use the specific type here

        GenericResponse<String> response = (GenericResponse<String>) authServiceImpl.loginUser(userCredentials);

        assertNotNull(response);
        assertEquals("success", response.getStatus());
        verify(authServiceImpl, times(1)).loginUser(userCredentials);
    }

    @Test
    public void testRefreshToken() {
//when(authServiceImpl.refreshToken(refreshTokenRequest)).thenReturn(genericResponse);  // Use the specific type here

        GenericResponse<String> response = (GenericResponse<String>) authServiceImpl.refreshToken(refreshTokenRequest);

        assertNotNull(response);
        assertEquals("success", response.getStatus());
        verify(authServiceImpl, times(1)).refreshToken(refreshTokenRequest);
    }
}
