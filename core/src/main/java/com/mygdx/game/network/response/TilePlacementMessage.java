package com.mygdx.game.network.response;

import com.mygdx.game.Position;
import com.mygdx.game.meeple.Meeple;

import java.util.ArrayList;

public class TilePlacementMessage {
    public int getPlacedTileNumber() {
        return placedTileNumber;
    }

    public void setPlacedTileNumber(int placedTileNumber) {
        this.placedTileNumber = placedTileNumber;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ArrayList<Meeple> getMeeples() {
        return meeples;
    }

    public void setMeeples(ArrayList<Meeple> meeples) {
        this.meeples = meeples;
    }

    public boolean isMonastery() {
        return monastery;
    }

    public void setMonastery(boolean monastery) {
        this.monastery = monastery;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    private int placedTileNumber;
    private Position position;
    private ArrayList<Meeple> meeples;
    private boolean monastery = false;
    private int rotation;


    public TilePlacementMessage() {

    }

    public TilePlacementMessage(Position position, int rotation) {
        this.position = position;
        this.rotation = rotation;
    }


    public String toString() {
        return "Position " + position + " rotation " + rotation;
    }
}
