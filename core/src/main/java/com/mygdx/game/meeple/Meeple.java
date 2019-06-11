package com.mygdx.game.meeple;

import com.mygdx.game.GameBoard;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Side;

public class Meeple {
    private MeepleType meepleType;
    private GameBoard.Color color;
    private boolean isBusy;

    public GameBoard.Color getColor() {
        return color;
    }

    public void setColor(GameBoard.Color color) {
        this.color = color;
    }


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
    }

    public Meeple(GameBoard.Color color){
        this.color = color;
    }

    public void setMeepleType(MeepleType mt) {
        meepleType = mt;
    }

    public void makeBusy() {
        this.isBusy = true;
    }


    public void setFree() {
        this.isBusy = false;
    }
}
