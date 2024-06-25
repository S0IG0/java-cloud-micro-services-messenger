package ru.soigo.auth.jwt.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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
    public PairToken generatePairToken(User user) {
        UUID uuid = UUID.randomUUID();
        return PairToken
                .builder()
                .access(generateAccessToken(user, uuid))
                .refresh(generateRefreshToken(user, uuid))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsernameFromAccessToken(String access) {
        if (!validateAccessToken(access)) {
            return null;
        }
        return parseClaims(access).getSubject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateRefreshToken(@NotNull User user, @NotNull UUID uuid) {
        Map<String, Object> claims = new HashMap<>() {{
            put("tokenType", TypeToken.REFRESH.toString());
        }};

        return generateToken(uuid, claims, user.getUsername(), refreshExpiration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateAccessToken(@NotNull User user, @NotNull UUID uuid) {
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

        return generateToken(uuid, claims, user.getUsername(), accessExpiration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, TypeToken.ACCESS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, TypeToken.REFRESH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Claims parseClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
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
        return Jwts
                .builder()
                .id(uuid.toString())
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
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
        try {
            Claims claims = parseClaims(token);
            return claims.get("tokenType").toString().equals(typeToken.toString());
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }
}
