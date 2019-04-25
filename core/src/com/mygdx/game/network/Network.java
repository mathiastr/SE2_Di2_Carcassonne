package com.mygdx.game.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    //Port Numbers randomly chosen
    static public final int TCP = 56773;
    static public final int UDP = 59885;

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(TestOutput.class);
        kryo.register(NetworkDevice.class);
    }
}

