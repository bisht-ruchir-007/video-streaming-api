package com.app.practice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for handling user credentials in authentication requests.
 * This class is used to pass the username and password of a user during login or authentication operations.
 * <p>
 * Author: Ruchir Bisht
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {

    private String username;
    private String password;


}
