package ru.soigo.auth.jwt.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.soigo.auth.jwt.dto.PairToken;
import ru.soigo.auth.jwt.model.TypeToken;
import ru.soigo.auth.jwt.service.JwtService;
import ru.soigo.auth.model.User;

import javax.crypto.SecretKey;
import java.util.*;

/**
 * Implementation of {@link JwtService} that handles JWT operations using the jjwt library.
 */
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {
    final Integer accessExpiration;
    final Integer refreshExpiration;
    final SecretKey key;

    /**
     * Constructs a new instance of {@link JwtServiceImpl} with specified parameters.
     *
     * @param accessExpiration  the expiration time for access tokens in milliseconds.
     * @param refreshExpiration the expiration time for refresh tokens in milliseconds.
     * @param jwtSecret         the secret key used to sign the tokens.
     */
    public JwtServiceImpl(
            @Value("${jwt.access.expiration}")
            @NotNull Integer accessExpiration,
            @Value("${jwt.refresh.expiration}")
            @NotNull Integer refreshExpiration,
            @Value("${jwt.secret}")
            @NotNull String jwtSecret
    ) {
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PairToken generatePairToken(@NotNull User user) {
        log.info("Generating pair token for user: {}", user.getUsername());
        UUID uuid = UUID.randomUUID();
        PairToken pairToken = PairToken
                .builder()
                .access(generateAccessToken(user, uuid))
                .refresh(generateRefreshToken(user, uuid))
                .build();
        log.debug("Generated pair token for user {}: {}", user.getUsername(), pairToken);
        return pairToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsernameFromToken(String token) {
        log.info("Extracting username from token: {}", token);
        String username = parseClaims(token).getSubject();
        log.debug("Extracted username from token: {}", username);
        return username;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateRefreshToken(@NotNull User user, @NotNull UUID uuid) {
        log.info("Generating refresh token for user: {}", user.getUsername());
        Map<String, Object> claims = new HashMap<>() {{
            put("tokenType", TypeToken.REFRESH.toString());
        }};
        String refreshToken = generateToken(uuid, claims, user.getUsername(), refreshExpiration);
        log.debug("Generated refresh token for user {}: {}", user.getUsername(), refreshToken);
        return refreshToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateAccessToken(@NotNull User user, @NotNull UUID uuid) {
        log.info("Generating access token for user: {}", user.getUsername());
        List<String> roles = user
                .getAuthorities()
                .stream()
                .map(Object::toString)
                .toList();

        Map<String, Object> claims = new HashMap<>() {{
            put("roles", roles);
            put("userId", user.getId());
            put("tokenType", TypeToken.ACCESS.toString());
        }};
        String accessToken = generateToken(uuid, claims, user.getUsername(), accessExpiration);
        log.debug("Generated access token for user {}: {}", user.getUsername(), accessToken);
        return accessToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateAccessToken(String accessToken) {
        log.info("Validating access token: {}", accessToken);
        boolean isValid = validateToken(accessToken, TypeToken.ACCESS);
        log.debug("Access token validation result: {}", isValid);
        return isValid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateRefreshToken(String refreshToken) {
        log.info("Validating refresh token: {}", refreshToken);
        boolean isValid = validateToken(refreshToken, TypeToken.REFRESH);
        log.debug("Refresh token validation result: {}", isValid);
        return isValid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Claims parseClaims(String token) {
        log.info("Parsing claims from token");
        Claims claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        log.debug("Parsed claims from token: {}", claims);
        return claims;
    }

    /**
     * Generates a token with the given parameters.
     *
     * @param uuid       the unique identifier for the token.
     * @param claims     the claims to be included in the token.
     * @param username   the subject (username) of the token.
     * @param expiration the expiration time for the token in milliseconds.
     * @return a newly generated token as a String.
     */
    private String generateToken(
            @NotNull UUID uuid,
            @NotNull Map<String, Object> claims,
            @NotNull String username,
            @NotNull Integer expiration
    ) {
        log.info("Generating token for username: {}, UUID: {}", username, uuid);
        String token = Jwts
                .builder()
                .id(uuid.toString())
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
        log.debug("Generated token for username {}: {}", username, token);
        return token;
    }

    /**
     * Validates the given JWT token against the specified token type.
     * <p>
     * This method parses the claims from the JWT token and checks if the token type
     * matches the provided {@code TypeToken}. If the token is invalid or the token type
     * does not match, the method returns {@code false}.
     * </p>
     *
     * @param token     the JWT token to be validated, must not be {@code null}.
     * @param typeToken the expected type of the token, must not be {@code null}.
     * @return {@code true} if the token is valid and the token type matches the expected type, {@code false} otherwise.
     */
    private boolean validateToken(@NotNull String token, @NotNull TypeToken typeToken) {
        log.info("Validating token of type: {}", typeToken);
        try {
            Claims claims = parseClaims(token);
            boolean isValid = claims.get("tokenType").toString().equals(typeToken.toString());
            log.debug("Token validation result for type {}: {}", typeToken, isValid);
            return isValid;
        } catch (JwtException | IllegalArgumentException exception) {
            log.error("Token validation failed for type {}: {}", typeToken, exception.getMessage());
            return false;
        }
    }
}
