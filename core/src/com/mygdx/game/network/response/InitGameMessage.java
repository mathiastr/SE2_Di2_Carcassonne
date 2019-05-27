package com.mygdx.game.network.response;

import com.mygdx.game.Player;

import java.util.ArrayList;

public class InitGameMessage {
    private ArrayList<Player> players;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public InitGameMessage() {

    }

}
