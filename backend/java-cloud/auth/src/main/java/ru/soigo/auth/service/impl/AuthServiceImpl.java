package ru.soigo.auth.service.impl;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.soigo.auth.jwt.dto.PairToken;
import ru.soigo.auth.jwt.service.JwtService;
import ru.soigo.auth.jwt.service.RedisService;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    final UserService userService;
    final JwtService jwtService;
    final RedisService redisService;
    final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public PairToken register(@NotNull User user) {
        log.info("Registering user with username: {}", user.getUsername());
        User createUser = userService.create(user);
        PairToken pairToken = jwtService.generatePairToken(createUser);
        log.debug("Generated tokens for user {}: {}", user.getUsername(), pairToken);
        return pairToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PairToken login(String username, String rawPassword) {
        log.info("User attempting to login with username: {}", username);
        User findUser = userService.findByUsername(username);

        if (!passwordEncoder.matches(rawPassword, findUser.getPassword())) {
            log.warn("Invalid password for user: {}", username);
            throw new BadCredentialsException("Invalid password");
        }
        PairToken pairToken = jwtService.generatePairToken(findUser);
        log.debug("Generated tokens for user {}: {}", username, pairToken);
        return pairToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PairToken updatePairTokenByRefreshToken(String refreshToken) {
        log.info("Updating pair token using refresh token");
        if (!jwtService.validateRefreshToken(refreshToken)) {
            log.warn("Invalid refresh token provided");
            throw new JwtException("Invalid refresh token");
        }
        String username = jwtService.getUsernameFromToken(refreshToken);
        String uuid = jwtService.getUUIDFormToken(refreshToken);

        redisService.removeToken(username, uuid);
        log.debug("Token with UUID {} removed for user: {}", uuid, username);

        User findUser = userService.findByUsername(username);
        PairToken pairToken = jwtService.generatePairToken(findUser);
        log.debug("Generated new tokens for user {}: {}", username, pairToken);
        return pairToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logoutCurrentDevice(String accessToken) {
        String username = jwtService.getUsernameFromToken(accessToken);
        String uuid = jwtService.getUUIDFormToken(accessToken);

        log.info("Logging out current device for user: {}", username);
        redisService.removeToken(username, uuid);
        log.debug("Token with UUID {} removed for user: {}", uuid, username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logoutAllDevice(String accessToken) {
        String username = jwtService.getUsernameFromToken(accessToken);

        log.info("Logging out all devices for user: {}", username);
        redisService.removeAllTokens(username);
        log.debug("All tokens removed for user: {}", username);
    }

}
