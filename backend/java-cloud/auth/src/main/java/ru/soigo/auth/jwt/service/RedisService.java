package ru.soigo.auth.jwt.service;

import java.util.List;

/**
 * Service interface for managing JWT tokens in Redis.
 * This interface defines the operations for adding, removing, and retrieving JWT tokens
 * associated with a specific username, leveraging Redis for storage.
 */
public interface RedisService {

    /**
     * Adds a JWT token to the list of tokens associated with the specified username.
     *
     * @param username the username to associate with the token.
     * @param tokenUUID the UUID of the token to be added.
     */
    public void addToken(String username, String tokenUUID);

    /**
     * Removes a specific JWT token associated with the specified username.
     *
     * @param username the username associated with the token.
     * @param tokenUUID the UUID of the token to be removed.
     */
    public void removeToken(String username, String tokenUUID);

    /**
     * Removes all JWT tokens associated with the specified username.
     *
     * @param username the username whose tokens are to be removed.
     */
    public void removeAllTokens(String username);

    /**
     * Retrieves all JWT tokens associated with the specified username.
     *
     * @param username the username whose tokens are to be retrieved.
     * @return a list of token UUIDs associated with the specified username.
     */
    public List<String> getAllTokens(String username);
}
