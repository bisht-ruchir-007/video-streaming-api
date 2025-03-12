package com.app.practice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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

    private final long jwtExpirationTimeInSec;
    private final SecretKey jwtSecretKey;

    public JwtUtil(@Value("${jwt.expiration-time-in-sec}") long jwtExpirationTimeInSec,
                   @Value("${jwt.secret-key}") String jwtSecretKey) {
        this.jwtExpirationTimeInSec = jwtExpirationTimeInSec;
        this.jwtSecretKey = generateSecretKey(jwtSecretKey);
    }

    // Generate SecretKey using SHA-512 hashing
    private SecretKey generateSecretKey(String secret) {
        try {
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            byte[] hashedKey = sha512.digest(secret.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(hashedKey, "HmacSHA512");
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing key", e);
        }
    }

    private SecretKey getSigningKey() {
        return jwtSecretKey;
    }

    /**
     * Generate JWT token for a user.
     */
    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    public String generateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTimeInSec * 1000)) // Convert seconds to milliseconds
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Use SHA-512
                .compact();
    }

    /**
     * Extract username from JWT token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration date from JWT token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract a claim from JWT token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from JWT token.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Check if JWT token is expired.
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Validate JWT token.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
