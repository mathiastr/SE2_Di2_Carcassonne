package com.mygdx.game.network.response;

public class SimpleMessage {
    public int getLastTileNumber() {
        return lastTileNumber;
    }

    public void setLastTileNumber(int lastTileNumber) {
        this.lastTileNumber = lastTileNumber;
    }

    private int lastTileNumber;
    public String message;
    public SimpleMessage() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
