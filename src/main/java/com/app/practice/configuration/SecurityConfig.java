package com.app.practice.configuration;

import com.app.practice.constants.AuthURIConstants;
import com.app.practice.security.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Code Author : Ruchir Bisht
 * <p>
 * SecurityConfig class is responsible for configuring Spring Security for the application.
 * It includes JWT authentication, session management, and password encoding.
 */

@Configuration
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    RequestMatcher[] permittedMatchers = PermittedEndpointsConfig.getPermittedMatchers();

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures HTTP security settings, including disabling CSRF, defining authentication rules,
     * setting session policy, and adding JWT authentication filter.
     *
     * @param http HttpSecurity instance to configure security settings.
     * @return SecurityFilterChain with applied security configurations.
     * @throws Exception if any security configuration fails.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        LOGGER.info("Initializing security configurations...");

        http
                .csrf(csrf -> {
                    LOGGER.info("Disabling CSRF protection as JWT is used.");
                    csrf.disable();
                })
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                .authorizeHttpRequests(auth -> {
                    LOGGER.info("Configuring authentication rules...");

                    auth.requestMatchers(permittedMatchers).permitAll();

                    LOGGER.info("Public endpoints: {}, {}", AuthURIConstants.LOGIN_ENDPOINT, AuthURIConstants.REGISTER_ENDPOINT);

                    auth.anyRequest().authenticated();
                    LOGGER.info("All other endpoints require authentication.");
                })
                .sessionManagement(session -> {
                    LOGGER.info("Setting session management policy to STATELESS.");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        LOGGER.info("Security configuration successfully initialized.");
        return http.build();
    }

    /**
     * Provides the AuthenticationManager bean required for authentication.
     *
     * @param authenticationConfiguration Spring Security's authentication configuration.
     * @return AuthenticationManager instance.
     * @throws Exception if authentication manager retrieval fails.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        LOGGER.info("Initializing AuthenticationManager bean...");
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Provides a PasswordEncoder bean for secure password hashing using BCrypt.
     *
     * @return PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        LOGGER.info("Initializing PasswordEncoder bean (BCryptPasswordEncoder).");
        return new BCryptPasswordEncoder();
    }
}
