package com.mygdx.game.network.response;

import com.mygdx.game.Player;

public class CheatOnScoreMessage {
    private int cheatTime;
    private Player player;

    public CheatOnScoreMessage() {
    }

    public CheatOnScoreMessage(int cheatTime, Player player) {
        this.cheatTime = cheatTime;
        this.player = player;
    }

    public int getCheatTime() {
        return cheatTime;
    }

    public void setCheatTime(int cheatTime) {
        this.cheatTime = cheatTime;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
