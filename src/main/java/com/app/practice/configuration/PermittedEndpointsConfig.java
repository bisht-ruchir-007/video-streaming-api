package com.app.practice.configuration;


import com.app.practice.constants.AuthURIConstants;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class PermittedEndpointsConfig {

    public static final RequestMatcher[] PERMITTED_MATCHERS = new RequestMatcher[]{
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/webjars/**"),
            new AntPathRequestMatcher(AuthURIConstants.AUTH_BASE_PATH + "/**") // Ensure VERSION is correctly replaced
    };

    public static RequestMatcher[] getPermittedMatchers() {
        return PERMITTED_MATCHERS;
    }
}
