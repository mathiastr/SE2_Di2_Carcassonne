package com.mygdx.game.network.response;

import com.mygdx.game.Position;

public class RemoveMeepleMessage {
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    private Position position;

    public RemoveMeepleMessage(Position position)
    {
        this.position = position;
    }

    public RemoveMeepleMessage()
    {

    }
}
