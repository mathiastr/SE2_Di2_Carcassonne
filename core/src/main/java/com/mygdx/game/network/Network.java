package com.mygdx.game.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.mygdx.game.Player;
import com.mygdx.game.emotes.Emote;
import com.mygdx.game.network.response.CheatOnScoreMessage;
import com.mygdx.game.network.response.EmoteMessage;
import com.mygdx.game.network.response.ErrorNumber;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Field;
import com.mygdx.game.GameBoard;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.meeple.MeepleType;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.Position;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.network.response.ConnectMessage;
import com.mygdx.game.network.response.CurrentTileMessage;
import com.mygdx.game.network.response.ErrorMessage;
import com.mygdx.game.network.response.InitGameMessage;
import com.mygdx.game.network.response.PlayerGameMessage;
import com.mygdx.game.network.response.TilePlacementMessage;
import com.mygdx.game.network.response.TurnEndMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class Network {
    //Port Numbers randomly chosen 56773 59885
    static public final int TCP = 56773;
    static public final int UDP = 59885;

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(PlayerGameMessage.class);
        kryo.register(InitGameMessage.class);
        kryo.register(ArrayList.class);
        kryo.register(TilePlacementMessage.class);
        kryo.register(Position.class);
        kryo.register(Meeple.class);
        kryo.register(CurrentTileMessage.class);
        kryo.register(TurnEndMessage.class);
        kryo.register(GameBoard.Color.class);
        kryo.register(City.class);
        kryo.register(Road.class);
        kryo.register(Field.class);
        kryo.register(Monastery.class);
        kryo.register(Side.class);
        kryo.register(MeepleType.class);
        kryo.register(HashMap.class);
        kryo.register(ErrorMessage.class);
        kryo.register(ConnectMessage.class);
        kryo.register(Player.class);
        kryo.register(CheatOnScoreMessage.class);
        kryo.register(ErrorNumber.class);
        kryo.register(EmoteMessage.class);
        kryo.register(Emote.class);
    }
}

