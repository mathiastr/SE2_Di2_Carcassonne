package com.mygdx.game.network.response;

import com.mygdx.game.Meeple;
import com.mygdx.game.Position;
import com.mygdx.game.TileActor;

import java.util.ArrayList;

public class TurnInfoMessage {
    public int placedTileNumber;
    public Position position;
    public ArrayList<Meeple> meeples;
    public boolean monastery = false;
    public int rotation;


    public TurnInfoMessage() {

    }

    public TurnInfoMessage(TileActor tileActor, int placedTileNumber, Position position, int rotation) {
        this.position = position;
        this.monastery = tileActor.isMonastery();
        this.meeples = new ArrayList<>();
        this.rotation = rotation;
        if (tileActor.getMeeples() != null) {

        for (Meeple m : tileActor.getMeeples()) {
            this.meeples.add(m);
        }
        }
        this.placedTileNumber = placedTileNumber;
    }

    public String toString() {
        return "Tile number " + placedTileNumber + " position " + position + " is monastery " + monastery;
    }
}
