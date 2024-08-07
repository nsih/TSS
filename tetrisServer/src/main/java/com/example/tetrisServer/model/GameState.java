// GameState.java
package com.example.tetrisServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameState implements Serializable {
    private String playerId; // 플레이어 ID
    private int score; // 점수
    private boolean gameOver; // 게임 오버 여부
    private String imageUrl; // 이미지 파일 URL
}