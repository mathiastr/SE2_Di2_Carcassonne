package com.mygdx.game.network.response;

import com.mygdx.game.Player;

public class CheatOnScoreMessage {
    private int cheatTime;
    private int targetId;

    public CheatOnScoreMessage() {
    }

    public CheatOnScoreMessage(int cheatTime, int id) {
        this.cheatTime = cheatTime;
        this.targetId = id;
    }

    public int getCheatTime() {
        return cheatTime;
    }

    public void setCheatTime(int cheatTime) {
        this.cheatTime = cheatTime;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }
}
