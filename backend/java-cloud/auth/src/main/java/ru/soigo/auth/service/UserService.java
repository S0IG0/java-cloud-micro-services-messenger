package ru.soigo.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.soigo.auth.model.User;

/**
 * Service interface for managing user-related operations.
 * <p>
 * This interface extends {@link UserDetailsService} to provide methods for loading user-specific data
 * in the context of Spring Security. It also includes methods for creating users, finding users by username,
 * and changing user passwords.
 * </p>
 *
 * <p><b>Methods:</b></p>
 * <ul>
 *   <li>{@link #create(User)} - Creates a new user in the system.</li>
 *   <li>{@link #findByUsername(String)} - Finds a user by their username.</li>
 *   <li>{@link #changePassword(String, String)} - Changes the password for a user identified by their username.</li>
 * </ul>
 *
 * @see UserDetailsService
 * @see User
 */
public interface UserService extends UserDetailsService {

    /**
     * Creates a new user in the system.
     *
     * @param user the user entity to be created
     * @return the created user entity
     */
    User create(User user);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found
     * @return the user entity if found
     */
    User findByUsername(String username);

    /**
     * Changes the password for a user identified by their username.
     *
     * @param username the username of the user whose password is to be changed
     * @param newRawPassword the new raw password to be set
     * @return the user entity with the updated password
     */
    User changePassword(String username, String newRawPassword);
}
