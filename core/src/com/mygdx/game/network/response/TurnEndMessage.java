package com.mygdx.game.network.response;

import com.mygdx.game.meeple.Meeple;

import java.util.ArrayList;
import java.util.List;

public class TurnEndMessage {
    private List<Meeple> meeples;

    private int playerScore;

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

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
