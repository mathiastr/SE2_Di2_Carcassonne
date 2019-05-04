package com.mygdx.game.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.mygdx.game.network.response.InitGameMessage;
import com.mygdx.game.network.response.PlayerGameMessage;

import java.util.ArrayList;

public class Network {
    //Port Numbers randomly chosen 56773 59885
    static public final int TCP = 56773;
    static public final int UDP = 59885;

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(TestOutput.class);
        kryo.register(SimpleMessage.class);
        kryo.register(PlayerGameMessage.class);
        kryo.register(InitGameMessage.class);
        kryo.register(ArrayList.class);
    }
}

