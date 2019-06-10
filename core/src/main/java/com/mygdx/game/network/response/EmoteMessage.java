package com.mygdx.game.network.response;

import com.mygdx.game.Player;
import com.mygdx.game.emotes.Emote;

public class EmoteMessage {
    private Emote emote;
    private Player player;

    public EmoteMessage() {}

    public EmoteMessage(Emote emote, Player player) {
        this.emote = emote;
        this.player = player;
    }

    public Emote getEmote() {
        return emote;
    }

    public void setEmote(Emote emote) {
        this.emote = emote;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
