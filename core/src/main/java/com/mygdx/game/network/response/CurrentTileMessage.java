package com.mygdx.game.network.response;

public class CurrentTileMessage {
    public int getTileNumber() {
        return tileNumber;
    }

    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    private int tileNumber;

    public CurrentTileMessage() {}


}
