package com.mygdx.game.network.response;

import com.mygdx.game.Position;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Side;

public class RemoveMeepleMessage {

    private Side side;
    private Feature feature;
    private Position position;

    public RemoveMeepleMessage() {

    }

    public RemoveMeepleMessage(Feature feature, Position position) {
        this.side = side;
        this.feature = feature;
        this.position = position;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}