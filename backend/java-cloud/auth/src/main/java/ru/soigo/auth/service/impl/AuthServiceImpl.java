package ru.soigo.auth.service.impl;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.soigo.auth.jwt.dto.PairToken;
import ru.soigo.auth.jwt.service.JwtService;
import ru.soigo.auth.model.User;
import ru.soigo.auth.service.AuthService;
import ru.soigo.auth.service.UserService;

/**
 * Implementation of {@link AuthService} interface.
 * <p>
 * Provides methods for user registration, login, and refreshing
 * authentication tokens using {@link JwtService} and {@link UserService}.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    final UserService userService;
    final JwtService jwtService;
    final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public PairToken register(User user) {
        User createUser = userService.create(user);
        return jwtService.generatePairToken(createUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PairToken login(String username, String rawPassword) {
        User findUser = userService.findByUsername(username);

        if (!passwordEncoder.matches(rawPassword, findUser.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtService.generatePairToken(findUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PairToken updatePairTokenByRefreshToken(String refreshToken) {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw new JwtException("Invalid refresh token");
        }
        String username = jwtService.getUsernameFromToken(refreshToken);
        User findUser = userService.findByUsername(username);
        return jwtService.generatePairToken(findUser);
    }
}
