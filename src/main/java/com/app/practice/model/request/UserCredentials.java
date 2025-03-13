package com.app.practice.model.request;

/**
 * Model class for handling user credentials in authentication requests.
 * This class is used to pass the username and password of a user during login or authentication operations.
 * <p>
 * Author: Ruchir Bisht
 */
public class UserCredentials {

    private String username;
    private String password;

    /**
     * Default constructor for creating a new instance of UserCredentials.
     */
    public UserCredentials() {
    }

    /**
     * Constructor to create a new UserCredentials object with the provided username and password.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     */
    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username.
     *
     * @param username the username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password.
     *
     * @param password the password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
