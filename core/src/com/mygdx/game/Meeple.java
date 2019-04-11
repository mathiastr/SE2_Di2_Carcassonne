package com.mygdx.game;

public class Meeple {
    private MeepleType meepleType;
    private GameBoard.Color color;
    private boolean isBusy;

    public Meeple(MeepleType mt) {
        meepleType = mt;
    }

    public Meeple(GameBoard.Color color){
        this.color = color;
    }

    public void changeMeepleType(MeepleType mt) {
        meepleType = mt;
    }

    public void makeBusy() {
        this.isBusy = true;
    }

    public void free() {
        this.isBusy = false;
    }
}
