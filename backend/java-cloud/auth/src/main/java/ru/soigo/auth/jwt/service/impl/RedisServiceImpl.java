package ru.soigo.auth.jwt.service.impl;

import lombok.RequiredArgsConstructor;
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
        listOps.rightPush(username, tokenUUID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeToken(String username, String tokenUUID) {
        listOps.remove(username, 0, tokenUUID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllTokens(String username) {
        redisTemplate.delete(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllTokens(String username) {
        return listOps.range(username, 0, -1);
    }
}
