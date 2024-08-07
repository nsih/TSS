// RedisConfig.java
package com.example.tetrisServer.config;

import com.example.tetrisServer.model.GameState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, GameState> redisTemplate(RedisConnectionFactory connectionFactory)
    {
        RedisTemplate<String, GameState> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Key, Value 직렬화
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}
