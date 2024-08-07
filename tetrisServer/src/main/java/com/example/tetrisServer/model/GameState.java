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


    //
    public String getPlayerId()
    {
        return playerId;
    }

    public void setPlayerId(String playerId)
    {
        this.playerId = playerId;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }


    //
    public boolean isGameOver()
    {
        return gameOver;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    //
    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString()
    {
        return "GameState{" +
                "playerId='" + playerId + '\'' +
                ", score=" + score +
                ", gameOver=" + gameOver +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}