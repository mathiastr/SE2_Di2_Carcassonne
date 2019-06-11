package com.mygdx.game.network.response;

public class BlameCheatMessage {
    private CheatType type;
    private int targetId;
    private int sourceId;

    public BlameCheatMessage() {
        type = CheatType.SCORE;
        targetId = 0;
        sourceId = 0;
    }

    public BlameCheatMessage(int targetId, int sourceId) {
        this.targetId = targetId;
        this.sourceId = sourceId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public CheatType getType() {
        return type;
    }

    public void setType(CheatType type) {
        this.type = type;
    }

}
