package com.example.tetrisServer.service;

import com.example.tetrisServer.model.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class GameStateService
{

    private static final String KEY_PREFIX = "game:"; // Redis 키 프리픽스

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 저장 (1초 TTL 설정)
    public void saveGameState(GameState gameState)
    {
        String key = KEY_PREFIX + gameState.getPlayerId(); // 고유한 키 생성
        redisTemplate.opsForValue().set(key, gameState, 1, TimeUnit.SECONDS);
    }

    // 조회
    public GameState getGameState(String playerId)
    {
        String key = KEY_PREFIX + playerId; // 조회할 키 생성
        return (GameState) redisTemplate.opsForValue().get(key);
    }
}
