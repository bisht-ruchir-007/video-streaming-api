package com.app.practice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for handling refresh token requests.
 * This class is used to pass the refresh token in API requests to refresh the user session.
 * <p>
 * Author: Ruchir Bisht
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

    private String refreshToken;


}
