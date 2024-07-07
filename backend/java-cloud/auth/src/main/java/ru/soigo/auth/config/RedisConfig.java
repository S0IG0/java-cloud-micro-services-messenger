package ru.soigo.auth.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

/**
 * Configuration class for setting up Redis with Spring.
 * This configuration class sets up the necessary beans for working with Redis,
 * including the connection factory, Redis template, and list operations.
 */
@Configuration
public class RedisConfig {

    /**
     * Creates a {@link RedisConnectionFactory} using Lettuce.
     *
     * @return a {@link LettuceConnectionFactory} instance.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * Configures a {@link RedisTemplate} for serializing and deserializing Redis keys and values.
     * The keys are serialized using {@link StringRedisSerializer}, and the values are serialized
     * using {@link GenericToStringSerializer} with {@link List} type.
     *
     * @return a configured {@link RedisTemplate} instance.
     */
    @Bean
    public RedisTemplate<String, List<String>> redisTemplate() {
        RedisTemplate<String, List<String>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(List.class));
        return template;
    }

    /**
     * Creates a {@link ListOperations} bean to enable Redis list operations.
     *
     * @param redisTemplate the {@link RedisTemplate} to use for list operations.
     * @return a {@link ListOperations} instance.
     */
    @Bean
    public ListOperations<String, String> listOperations(@NotNull RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForList();
    }
}
