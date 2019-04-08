package com.mygdx.game.network;

public class NetworkHelper {
    private static AbstractGameManager gameManager;

    public static AbstractGameManager getGameManager() {
        return gameManager;
    }

    public static void setGameManager(AbstractGameManager gameManager) {
        NetworkHelper.gameManager = gameManager;
    }
}
