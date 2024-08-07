package com.example.tetrisServer.controller;

import com.example.tetrisServer.model.GameState;
import com.example.tetrisServer.service.GameStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController
{

    @Autowired
    private GameStateService gameStateService;

    // 게임 상태 저장 엔드포인트
    @PostMapping("/save")
    public ResponseEntity<String> saveGameState(@RequestBody GameState gameState)
    {
        gameStateService.saveGameState(gameState);
        return new ResponseEntity<>("saved", HttpStatus.OK);
    }

    // 게임 상태 조회 엔드포인트
    @GetMapping("/state/{playerId}")
    public ResponseEntity<GameState> getGameState(@PathVariable String playerId)
    {
        GameState gameState = gameStateService.getGameState(playerId);
        if (gameState != null)
        {
            return new ResponseEntity<>(gameState, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
