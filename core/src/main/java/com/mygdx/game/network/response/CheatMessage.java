package com.mygdx.game.network.response;

import com.mygdx.game.Player;

public class CheatMessage {
    private Player caller;
    private Player callee;
    private CheatType type;

    public CheatMessage() {
    }

    public CheatMessage(Player caller, Player callee, CheatType type) {
        this.caller = caller;
        this.callee = callee;
        this.type = type;
    }

    public Player getCaller() {
        return caller;
    }

    public void setCaller(Player caller) {
        this.caller = caller;
    }

    public CheatType getType() {
        return type;
    }

    public void setType(CheatType type) {
        this.type = type;
    }

    public Player getCallee() {
        return callee;
    }

    public void setCallee(Player callee) {
        this.callee = callee;
    }
}
