package ru.soigo.auth.exception;

/**
 * Exception thrown when attempting to create a user that already exists.
 * <p>
 * This exception is typically thrown during user registration or creation
 * operations when a user with the same username or identifier already exists
 * in the system.
 * </p>
 *
 * @see RuntimeException
 */
public class AlreadyUserException extends RuntimeException {

    /**
     * Constructs an AlreadyUserException with the specified error message.
     *
     * @param message The detail message.
     */
    public AlreadyUserException(String message) {
        super(message);
    }
}
