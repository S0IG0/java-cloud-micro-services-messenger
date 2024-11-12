package ru.soigo.auth.jwt.model;

/**
 * Enum representing the types of tokens used in the authentication system.
 * <p>
 * This enum defines two types of tokens:
 * </p>
 * <ul>
 *   <li>{@link #REFRESH} - A refresh token used to obtain a new access and refresh tokens without re-authenticating the user.</li>
 *   <li>{@link #ACCESS} - A access token used to authenticate user requests and grant access to protected resources.</li>
 * </ul>
 */
public enum TypeToken {
    /**
     * A refresh token used to obtain a new access and refresh tokens without re-authenticating the user.
     */
    REFRESH,

    /**
     * An access token used to authenticate user requests and grant access to protected resources.
     */
    ACCESS
}
