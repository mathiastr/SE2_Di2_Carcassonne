package com.mygdx.game.network.response;

import com.mygdx.game.Position;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Side;

public class MeeplePlacementMessage {

    private Side side;
    private Feature feature;
    private Position position;

    public MeeplePlacementMessage(){
    }

    public MeeplePlacementMessage(Side side, Feature feature, Position position) {
        this.feature = feature;
        this.position = position;
        this.side = side;
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