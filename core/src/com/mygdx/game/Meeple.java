package com.mygdx.game;

import java.util.Random;

public class Meeple {
    private int id;
    private MeepleType meepleType;
    private GameBoard.Color color;
    private boolean isBusy;

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    private Side side;
    private Feature feature;

    public Meeple() {
        Random rand = new Random();
        this.id = rand.nextInt(1000);
    }

    public Meeple(GameBoard.Color color){
        Random rand = new Random();
        this.id = rand.nextInt(1000);
        this.color = color;
    }

    public void changeMeepleType(MeepleType mt) {
        meepleType = mt;
    }

    public void makeBusy() {
        this.isBusy = true;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void free() {
        this.isBusy = false;
    }
}
