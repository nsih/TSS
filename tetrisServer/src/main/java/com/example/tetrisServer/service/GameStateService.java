package com.example.tetrisServer.service;

import com.example.tetrisServer.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class GameStateService {

    private static final Logger logger = LoggerFactory.getLogger(GameStateService.class);

    private final ConcurrentMap<String, GameState> gameStateMap;
    private final ConcurrentMap<String, String> matchmakingQueue;
    private final RedisTemplate<String, GameState> redisTemplate;

    @Autowired
    public GameStateService(RedisTemplate<String, GameState> redisTemplate)
    {
        this.gameStateMap = new ConcurrentHashMap<>();
        this.matchmakingQueue = new ConcurrentHashMap<>();
        this.redisTemplate = redisTemplate;
    }

    public void saveGameState(GameState gameState) {
        if (gameState == null || gameState.getPlayerId() == null)
        {
            logger.error("잘못된 게임 상태 또는 플레이어 ID가 null입니다.");
            throw new IllegalArgumentException("잘못된 게임 상태 또는 플레이어 ID");
        }
        gameStateMap.put(gameState.getPlayerId(), gameState);
        redisTemplate.opsForValue().set(gameState.getPlayerId(), gameState); // Redis에 게임 상태 저장
        logger.info("플레이어 ID: {}에 대한 게임 상태 저장됨.", gameState.getPlayerId());
    }

    public GameState getGameState(String playerId)
    {
        if (playerId == null || playerId.isEmpty())
        {
            logger.error("플레이어 ID가 null이거나 비어 있습니다.");
            throw new IllegalArgumentException("플레이어 ID는 null이거나 비어 있을 수 없습니다.");
        }

        GameState gameState = gameStateMap.get(playerId);
        if (gameState == null) {
            gameState = redisTemplate.opsForValue().get(playerId); // Redis에서 게임 상태 조회
            if (gameState != null) {
                gameStateMap.put(playerId, gameState); // 캐싱
            } else {
                logger.warn("플레이어 ID: {}에 대한 게임 상태를 찾을 수 없습니다.", playerId);
            }
        }
        return gameState;
    }

    public boolean joinMatchmaking(String playerId)
    {
        if (playerId == null || playerId.isEmpty())
        {
            logger.error("플레이어 ID가 null이거나 비어 있습니다.");
            throw new IllegalArgumentException("플레이어 ID는 null이거나 비어 있을 수 없습니다.");
        }

        // 이미 대기열에 있는지 확인
        if (matchmakingQueue.containsKey(playerId)) {
            logger.info("플레이어 ID: {}는 이미 매칭 대기열에 있습니다.", playerId);
            return false;
        }

        // 플레이어를 매칭 대기열에 추가
        matchmakingQueue.put(playerId, "WAITING");
        logger.info("플레이어 ID: {}가 매칭 대기열에 추가되었습니다.", playerId);
        return true;
    }

    public ConcurrentMap<String, String> getMatchmakingStatus() {
        return matchmakingQueue;
    }

    public GameState getMatchmakingStatus(String playerId) {
        String opponentId = matchmakingQueue.get(playerId);
        if (opponentId != null) {
            return new GameState(playerId, opponentId, 0, 0, false, null);
        }

        return null;
    }

    public boolean startMatch() {
        if (matchmakingQueue.size() >= 2) {
            String[] players = matchmakingQueue.keySet().toArray(new String[0]);
            String player1 = players[0];
            String player2 = players[1];

            matchmakingQueue.remove(player1);
            matchmakingQueue.remove(player2);

            logger.info("매치가 시작되었습니다. 플레이어 1: {}, 플레이어 2: {}", player1, player2);
            return true;
        }
        logger.info("매칭할 적합한 상대방을 찾을 수 없습니다.");
        return false;
    }
}