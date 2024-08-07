package com.example.tetrisServer.config;

import com.example.tetrisServer.model.GameState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, GameState> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, GameState> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // String Redis Serializer를 key로 사용합니다.
        template.setKeySerializer(new StringRedisSerializer());

        // Jackson2Json Redis Serializer를 value로 사용합니다.
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(GameState.class));

        return template;
    }
}
