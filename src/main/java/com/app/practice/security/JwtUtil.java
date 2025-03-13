package com.app.practice.security;

import com.app.practice.constants.ModuleConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final long jwtExpirationTimeInSec;
    private final long refreshExpirationTimeInSec;
    private final SecretKey jwtSecretKey;

    public JwtUtil(@Value("${jwt.expiration-time-in-sec}") long jwtExpirationTimeInSec,
                   @Value("${jwt.refresh-expiration-time-in-sec}") long refreshExpirationTimeInSec,
                   @Value("${jwt.secret-key}") String jwtSecretKey) {
        this.jwtExpirationTimeInSec = jwtExpirationTimeInSec;
        this.refreshExpirationTimeInSec = refreshExpirationTimeInSec;
        this.jwtSecretKey = generateSecretKey(jwtSecretKey);
        logger.info("JwtUtil initialized with expiration times: AccessToken={}s, RefreshToken={}s", jwtExpirationTimeInSec, refreshExpirationTimeInSec);
    }

    // Generate SecretKey using SHA-512 hashing
    private SecretKey generateSecretKey(String secret) {
        try {
            MessageDigest sha512 = MessageDigest.getInstance(ModuleConstants.MESSAGE_DIGEST_ALGO_NAME);
            byte[] hashedKey = sha512.digest(secret.getBytes(StandardCharsets.UTF_8));
            logger.debug("Generated secret key using SHA-512 hashing");
            return new SecretKeySpec(hashedKey, ModuleConstants.HASH_ALGO_NAME);
        } catch (Exception e) {
            logger.error("Error while hashing key", e);
            throw new RuntimeException("Error while hashing key", e);
        }
    }

    private SecretKey getSigningKey() {
        return jwtSecretKey;
    }

    /**
     * Generate JWT Access Token.
     *
     * @param username the username for which the token is being generated
     * @return JWT token
     */
    public String generateToken(String username) {
        logger.info("Generating JWT token for user: {}", username);
        return generateToken(new HashMap<>(), username, jwtExpirationTimeInSec);
    }

    /**
     * Generate JWT Refresh Token.
     *
     * @param username the username for which the refresh token is being generated
     * @return JWT refresh token
     */
    public String generateRefreshToken(String username) {
        logger.info("Generating JWT refresh token for user: {}", username);
        return generateToken(new HashMap<>(), username, refreshExpirationTimeInSec);
    }

    private String generateToken(Map<String, Object> claims, String username, long expirationTime) {
        logger.debug("Generating JWT token with claims for user: {} with expiration: {} seconds", username, expirationTime);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 1000)) // Convert seconds to milliseconds
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extract username from JWT.
     *
     * @param token the JWT token
     * @return extracted username
     */
    public String extractUsername(String token) {
        logger.debug("Extracting username from token");
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration date from JWT.
     *
     * @param token the JWT token
     * @return extracted expiration date
     */
    public Date extractExpiration(String token) {
        logger.debug("Extracting expiration date from token");
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract claims from JWT.
     *
     * @param token          the JWT token
     * @param claimsResolver the function to resolve the claim
     * @param <T>            the type of the claim
     * @return the claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        logger.debug("Extracting claim from token");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        logger.debug("Extracting all claims from token");
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Validate JWT token.
     *
     * @param token       the JWT token
     * @param userDetails the user details
     * @return true if token is valid, otherwise false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        logger.debug("Validating token for user: {}", userDetails.getUsername());
        final String username = extractUsername(token);
        boolean valid = username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        logger.info("Token validation result for user {}: {}", username, valid);
        return valid;
    }

    /**
     * Check if the token is expired.
     *
     * @param token the JWT token
     * @return true if token is expired, otherwise false
     */
    public boolean isTokenExpired(String token) {
        logger.debug("Checking if token is expired");
        boolean expired = extractExpiration(token).before(new Date());
        logger.info("Token expiration status: {}", expired ? "Expired" : "Valid");
        return expired;
    }
}
