package main.java.com.mygdx.game.network.response;

import main.java.com.mygdx.game.meeple.Meeple;
import main.java.com.mygdx.game.Position;

import java.util.ArrayList;

public class TilePlacementMessage {
    public int placedTileNumber;
    public Position position;
    public ArrayList<Meeple> meeples;
    public boolean monastery = false;
    public int rotation;


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
