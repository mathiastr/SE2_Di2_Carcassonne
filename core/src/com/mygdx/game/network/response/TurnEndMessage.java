package com.mygdx.game.network.response;

import com.mygdx.game.Meeple;

import java.util.ArrayList;
import java.util.List;

public class TurnEndMessage {
    private List<Meeple> meeples;

    public TurnEndMessage() {
        this.meeples = new ArrayList();
    }

    public List<Meeple> getMeeples() {
        return meeples;
    }

    public void setMeeples(List<Meeple> meeples) {
        this.meeples = meeples;
    }
}
