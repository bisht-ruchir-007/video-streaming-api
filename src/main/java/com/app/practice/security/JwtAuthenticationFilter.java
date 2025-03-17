package com.app.practice.security;

import com.app.practice.constants.ModuleConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter class is responsible for processing the incoming HTTP requests
 * to extract and validate JWT tokens for user authentication.
 * This filter ensures that only authenticated users can access protected endpoints.
 * <p>
 * Author: Ruchir Bisht
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Filters incoming HTTP requests to check if they contain a valid JWT token in the header.
     * If a valid token is found, the user is authenticated and the security context is updated.
     *
     * @param request  the incoming HTTP request.
     * @param response the HTTP response.
     * @param chain    the filter chain to continue processing after authentication.
     * @throws ServletException if there's an issue with the filter chain.
     * @throws IOException      if there's an issue with reading/writing the request/response.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = extractToken(request);
        if (token != null) {
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the "Authorization" header in the HTTP request.
     * The token is expected to be prefixed with "Bearer ".
     *
     * @param request the incoming HTTP request containing the Authorization header.
     * @return the extracted JWT token, or null if no valid token is found.
     */
    private String extractToken(HttpServletRequest request) {

        String header = request.getHeader(ModuleConstants.AUTH_HEADER);

        // Check if the header contains the "Bearer " prefix and return the token part.
        return (header != null && header.startsWith(ModuleConstants.AUTH_BEARER_PREFIX))
                ? header.substring(ModuleConstants.AUTH_BEARER_PREFIX.length()) : null;
    }
}
