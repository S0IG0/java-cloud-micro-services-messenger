package ru.soigo.auth.jwt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.soigo.auth.jwt.service.RedisService;

import java.util.List;

/**
 * Implementation of the {@link RedisService} interface.
 * This service provides methods for managing JWT tokens in Redis,
 * including adding, removing, and retrieving tokens associated with a specific username.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    final ListOperations<String, String> listOps;
    final RedisTemplate<String, List<String>> redisTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToken(String username, String tokenUUID) {
        log.info("Adding token for username: {}", username);
        listOps.rightPush(username, tokenUUID);
        log.debug("Token {} added for username: {}", tokenUUID, username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeToken(String username, String tokenUUID) {
        log.info("Removing token for username: {}", username);
        listOps.remove(username, 0, tokenUUID);
        log.debug("Token {} removed for username: {}", tokenUUID, username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllTokens(String username) {
        log.info("Removing all tokens for username: {}", username);
        redisTemplate.delete(username);
        log.debug("All tokens removed for username: {}", username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllTokens(String username) {
        log.info("Retrieving all tokens for username: {}", username);
        List<String> tokens = listOps.range(username, 0, -1);
        log.debug("Retrieved tokens for username {}: {}", username, tokens);
        return tokens;
    }
}
