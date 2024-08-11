package com.example.tetrisServer.controller;

import com.example.tetrisServer.model.GameState;
import com.example.tetrisServer.service.GameStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentMap;

@RestController

public class GameController {

    private final GameStateService gameStateService;

    @Autowired
    public GameController(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }


    @GetMapping("")
    public String doGetHelloWorld()
    {
        return "Hello World";
    }

    @GetMapping("/TSS")
    public String doGetHelloWorldDemo()
    {
        return "Hello World (TSS)";
    }




    // 게임 상태 저장 엔드포인트
    @PostMapping("/save")
    public ResponseEntity<String> saveGameState(@RequestBody GameState gameState)
    {
        try {
            gameStateService.saveGameState(gameState);
            return new ResponseEntity<>("게임 상태가 성공적으로 저장되었습니다.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("잘못된 게임 상태 데이터입니다.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 게임 상태 조회 엔드포인트
    @GetMapping("/state/{playerId}")
    public ResponseEntity<GameState> getGameState(@PathVariable String playerId)
    {
        try {
            GameState gameState = gameStateService.getGameState(playerId);
            if (gameState != null) {
                return new ResponseEntity<>(gameState, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 매칭 대기 엔드포인트
    @PostMapping("/matchmaking")
    public ResponseEntity<String> joinMatchmaking(@RequestParam String playerId)
    {
        try {
            boolean success = gameStateService.joinMatchmaking(playerId);
            if (success) {
                return new ResponseEntity<>("플레이어가 매칭 대기열에 추가되었습니다.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("플레이어가 이미 매칭 대기열에 있습니다.", HttpStatus.CONFLICT);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("잘못된 플레이어 ID입니다.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @GetMapping("/matchmaking-status")
    public ResponseEntity<GameState> getMatchmakingStatus(@RequestParam String playerId) {
        try {
            GameState gameState = gameStateService.getMatchmakingStatus(playerId);
            if (gameState != null && gameState.getOpponentId() != null) {
                return new ResponseEntity<>(gameState, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 매칭 시작 엔드포인트
    // 큐 두개중에 내꺼 아닌걸 찾아서 상대방 id로 뱉기
    @PostMapping("/start-match")
    public ResponseEntity<String> startMatch()
    {
        try {
            boolean success = gameStateService.startMatch();
            if (success) {
                return new ResponseEntity<>("매치가 성공적으로 시작되었습니다.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("적절한 상대방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
