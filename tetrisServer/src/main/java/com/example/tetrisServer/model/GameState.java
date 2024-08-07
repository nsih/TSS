package com.example.tetrisServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameState implements Serializable
{
    private String playerId;    // 플레이어 ID
    private String opponentId;  // 상대방 ID
    private int time;           // 시간
    private int score;          // 점수
    private boolean isEnd;      // 게임 오버 여부
    private String imageUrl;    // 이미지 파일 URL

    @Override
    public String toString()
    {
        return "GameState{" +
                "playerId='" + playerId + '\'' +
                ", opponentId='" + opponentId + '\'' +
                ", time=" + time +
                ", score=" + score +
                ", isEnd=" + isEnd +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}