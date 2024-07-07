package ru.soigo.auth.service;

import ru.soigo.auth.jwt.dto.PairToken;
import ru.soigo.auth.model.User;

/**
 * Service interface defining authentication operations.
 * <p>
 * This interface provides methods for user registration, login, refreshing
 * authentication tokens, and logging out.
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

    /**
     * Logs out the current device using the provided access token.
     * <p>
     * This method invalidates the access token, effectively logging out the user
     * from the current device.
     * </p>
     *
     * @param accessToken The access token of the current device to be logged out.
     */
    void logoutCurrentDevice(String accessToken);

    /**
     * Logs out from all devices using the provided access token.
     * <p>
     * This method invalidates all access tokens associated with the user, effectively
     * logging out the user from all devices.
     * </p>
     *
     * @param accessToken The access token used to identify the user and log out from all devices.
     */
    void logoutAllDevice(String accessToken);
}
