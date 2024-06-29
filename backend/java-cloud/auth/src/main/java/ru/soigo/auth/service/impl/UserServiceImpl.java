package ru.soigo.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.soigo.auth.exception.AlreadyUserException;
import ru.soigo.auth.model.Role;
import ru.soigo.auth.model.User;
import ru.soigo.auth.repository.UserRepository;
import ru.soigo.auth.service.UserService;

import java.util.Set;

/**
 * Implementation of the {@link UserService} interface providing methods for user management.
 * <p>
 * This service is responsible for loading user details, creating new users, finding users by username,
 * and changing user passwords. It integrates with Spring Security and uses a {@link PasswordEncoder}
 * to handle password encryption.
 * </p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@link UserRepository} - Repository for accessing user data.</li>
 *   <li>{@link PasswordEncoder} - Encoder for encrypting user passwords.</li>
 * </ul>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code userRepository} - Final reference to the user repository.</li>
 *   <li>{@code passwordEncoder} - Final reference to the password encoder.</li>
 *   <li>{@code defaultRoles} - Static set of default roles assigned to new users.</li>
 * </ul>
 *
 * @see UserService
 * @see UserDetails
 * @see UserRepository
 * @see PasswordEncoder
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    static final Set<Role> defaultRoles = Set.of(Role.ROLE_USER);

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        UserDetails userDetails = findByUsername(username);
        log.debug("Loaded user details by username: {}", userDetails.getUsername());
        return userDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(@NotNull User user) {
        log.info("Creating new user with username: {}", user.getUsername());

        if (userRepository.existsByUsername(user.getUsername())) {
            log.warn("Attempt to create a user with an existing username: {}", user.getUsername());
            throw new AlreadyUserException(String.format("Username: %s already exist", user.getUsername()));
        }

        user.setRoles(defaultRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        log.debug("Created user with username: {}", createdUser.getUsername());
        return createdUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByUsername(String username) {
        log.info("Finding user by username: {}", username);
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Username not found: {}", username);
                    return new UsernameNotFoundException("Not found username: " + username);
                });
        log.debug("Found user with username: {}", user.getUsername());
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User changePassword(String username, String newRawPassword) {
        log.info("Changing password for user: {}", username);
        User user = findByUsername(username);
        user.setPassword(passwordEncoder.encode(newRawPassword));
        User updatedUser = userRepository.save(user);
        log.debug("Password changed for user: {}", updatedUser.getUsername());
        return updatedUser;
    }
}
