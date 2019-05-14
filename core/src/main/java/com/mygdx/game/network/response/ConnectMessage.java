package main.java.com.mygdx.game.network.response;

import main.java.com.mygdx.game.Player;

public class ConnectMessage {
    public Player player;

    public ConnectMessage(){

    }

    public ConnectMessage(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
