package com.mygdx.game.network.response;


import com.mygdx.game.Player;
import com.mygdx.game.meeple.Meeple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TurnEndMessage {
    private ArrayList meeples;

    public HashMap<Player, Integer> getScoreChanges() {
        return scoreChanges;
    }

    private HashMap<Player, Integer> scoreChanges = new HashMap<>();

    public TurnEndMessage() {
        this.meeples = new ArrayList();
    }

    public List<Meeple> getMeeples() {
        return meeples;
    }

    public void setMeeples(List<Meeple> meeples) {
        this.meeples.clear();
        for (Meeple m : meeples) {
            this.meeples.add(m);
        }
    }

}
