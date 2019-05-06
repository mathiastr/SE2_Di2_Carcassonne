package com.mygdx.game.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.mygdx.game.Meeple;
import com.mygdx.game.Player;
import com.mygdx.game.Position;
import com.mygdx.game.network.response.ConnectMessage;
import com.mygdx.game.network.response.CurrentTileMessage;
import com.mygdx.game.network.response.ErrorMessage;
import com.mygdx.game.network.response.InitGameMessage;
import com.mygdx.game.network.response.PlayerGameMessage;
import com.mygdx.game.network.response.SimpleMessage;
import com.mygdx.game.network.response.TilePlacementMessage;
import com.mygdx.game.network.response.TurnEndMessage;

import java.util.ArrayList;

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
        kryo.register(SimpleMessage.class);
        kryo.register(CurrentTileMessage.class);
        kryo.register(TurnEndMessage.class);
        kryo.register(ErrorMessage.class);
        kryo.register(ConnectMessage.class);
    }
}

