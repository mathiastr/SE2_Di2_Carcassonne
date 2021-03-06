package com.mygdx.game.network;

import com.mygdx.game.Player;

public class NetworkHelper {
    private static AbstractGameManager gameManager;
    private static Player player;

    private static Object lastMessage;

    public static AbstractGameManager getGameManager() {
        return gameManager;
    }

    public static void setGameManager(AbstractGameManager gameManager) {
        NetworkHelper.gameManager = gameManager;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        NetworkHelper.player = player;
    }

    public static Object getLastMessage() {
        return lastMessage;
    }

    public static void setLastMessage(Object lastMessage) {
        NetworkHelper.lastMessage = lastMessage;
    }
}
