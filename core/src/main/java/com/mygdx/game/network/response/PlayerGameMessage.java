package com.mygdx.game.network.response;

import com.mygdx.game.Player;

public class PlayerGameMessage {
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;

    public String name;

    public PlayerGameMessage(Player player) {
        this.score = player.getScore();
        this.name = player.getName();
       // this.color = player.getColor();
    }

    public PlayerGameMessage() {
        this.name = "";
        this.score = 0;
    }
}
