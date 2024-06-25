package ru.soigo.auth.jwt.service;

import io.jsonwebtoken.Claims;
import ru.soigo.auth.jwt.dto.PairToken;
import ru.soigo.auth.model.User;

import java.util.UUID;

/**
 * Service interface for managing JWT (JSON Web Tokens).
 */
public interface JwtService {

    /**
     * Generates a pair of tokens (access and refresh) for the given user.
     *
     * @param user the user for whom the tokens are generated.
     * @return a {@link PairToken} containing both access and refresh tokens.
     */
    PairToken generatePairToken(User user);

    /**
     * Extracts the username from the provided access or refresh token.
     *
     * @param token the access or refresh token.
     * @return the username contained within the access or refresh token.
     */
    String getUsernameFromToken(String token);

    /**
     * Generates a refresh token for the given user and unique identifier.
     *
     * @param user the user for whom the refresh token is generated.
     * @param uuid a unique identifier associated with the token.
     * @return a newly generated refresh token.
     */
    String generateRefreshToken(User user, UUID uuid);

    /**
     * Generates an access token for the given user and unique identifier.
     *
     * @param user the user for whom the access token is generated.
     * @param uuid a unique identifier associated with the token.
     * @return a newly generated access token.
     */
    String generateAccessToken(User user, UUID uuid);

    /**
     * Validates the provided access token.
     *
     * @param accessToken the access token to validate.
     * @return {@code true} if the access token is valid, {@code false} otherwise.
     */
    boolean validateAccessToken(String accessToken);

    /**
     * Validates the provided refresh token.
     *
     * @param refreshToken the refresh token to validate.
     * @return {@code true} if the refresh token is valid, {@code false} otherwise.
     */
    boolean validateRefreshToken(String refreshToken);

    /**
     * Parses the claims contained within the provided token.
     *
     * @param token the token to parse.
     * @return the claims extracted from the token.
     */
    Claims parseClaims(String token);
}