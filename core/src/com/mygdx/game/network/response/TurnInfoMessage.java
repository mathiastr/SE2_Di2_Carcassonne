package com.mygdx.game.network.response;

import com.mygdx.game.Meeple;
import com.mygdx.game.Position;
import com.mygdx.game.TileActor;

import java.util.ArrayList;

public class TurnInfoMessage {
    public int tileNumber;
    public Position position;
    public ArrayList<Meeple> meeples;
    public boolean monastery = false;

    public TurnInfoMessage() {

    }

    public TurnInfoMessage(TileActor tileActor, int tileNumber) {
        this.position = tileActor.getPosition();
        this.monastery = tileActor.isMonastery();
        this.meeples = new ArrayList<>();
        if (tileActor.getMeeples() != null) {

        for (Meeple m : tileActor.getMeeples()) {
            this.meeples.add(m);
        }
        }
        this.tileNumber = tileNumber;
    }

    public String toString() {
        return "Tile number " + tileNumber + " position " + position + " is monastery " + monastery;
    }
}
