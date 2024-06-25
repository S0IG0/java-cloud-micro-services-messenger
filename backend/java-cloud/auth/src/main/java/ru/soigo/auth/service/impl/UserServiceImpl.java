package ru.soigo.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        return findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(@NotNull User user) {
        user.setRoles(defaultRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found username: " + username));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User changePassword(String username, String newRawPassword) {
        User user = findByUsername(username);
        user.setPassword(passwordEncoder.encode(newRawPassword));
        return userRepository.save(user);
    }
}
