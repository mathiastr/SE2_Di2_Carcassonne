package com.mygdx.game.network.response;

import java.util.ArrayList;

public class InitGameMessage {
    private ArrayList<PlayerGameMessage> players;

    public ArrayList<PlayerGameMessage> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerGameMessage> players) {
        this.players = players;
    }

    public InitGameMessage() {

    }

}
