package ru.soigo.auth.service;

import ru.soigo.auth.jwt.dto.PairToken;
import ru.soigo.auth.model.User;

/**
 * Service interface defining authentication operations.
 * <p>
 * This interface provides methods for user registration, login, and refreshing
 * authentication tokens.
 * </p>
 */
public interface AuthService {

    /**
     * Registers a new user and returns a pair of authentication tokens.
     *
     * @param user The user to register.
     * @return A PairToken containing access and refresh tokens.
     */
    PairToken register(User user);

    /**
     * Authenticates a user with the provided username and password, and returns a pair of authentication tokens.
     *
     * @param username    The username of the user.
     * @param rawPassword The raw password entered by the user.
     * @return A PairToken containing access and refresh tokens.
     */
    PairToken login(String username, String rawPassword);

    /**
     * Updates the authentication tokens using a refresh token.
     *
     * @param refreshToken The refresh token used to generate new tokens.
     * @return A PairToken containing new access and refresh tokens.
     */
    PairToken updatePairTokenByRefreshToken(String refreshToken);
}