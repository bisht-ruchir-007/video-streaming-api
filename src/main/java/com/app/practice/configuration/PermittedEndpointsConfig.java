package com.app.practice.configuration;

import com.app.practice.constants.AuthURIConstants;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/*
    Code Author : Ruchir Bisht

    This class is used to define and provide a list of permitted endpoints that do not require authentication.
    The endpoints specified in this class can be accessed without any security restrictions.

    - The PERMITTED_MATCHERS array contains a set of RequestMatcher instances that represent the paths
      that are allowed without authentication. These are typically Swagger documentation and authentication
      related paths.
    - The class uses AntPathRequestMatcher to match the paths, which supports pattern matching (e.g., **).
    - The AUTH_BASE_PATH from AuthURIConstants is used to match authentication-related endpoints.
 */

public class PermittedEndpointsConfig {


    public static final RequestMatcher[] PERMITTED_MATCHERS = new RequestMatcher[]{
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher(AuthURIConstants.AUTH_BASE_PATH + "/**")
    };

    /**
     * Provides the array of permitted request matchers.
     *
     * @return An array of RequestMatchers representing the paths that do not require authentication.
     */
    public static RequestMatcher[] getPermittedMatchers() {
        return PERMITTED_MATCHERS;
    }
}
