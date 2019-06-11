package com.mygdx.game.network.response;

public class CheatMessage {
    private int caller;
    private int callee;
    private CheatType type;

    public CheatMessage() {
    }

    public CheatMessage(int caller, int callee, CheatType type) {
        this.caller = caller;
        this.callee = callee;
        this.type = type;
    }

    public int getCaller() {
        return caller;
    }

    public void setCaller(int caller) {
        this.caller = caller;
    }

    public int getCallee() {
        return callee;
    }

    public void setCallee(int callee) {
        this.callee = callee;
    }

    public CheatType getType() {
        return type;
    }

    public void setType(CheatType type) {
        this.type = type;
    }
}
