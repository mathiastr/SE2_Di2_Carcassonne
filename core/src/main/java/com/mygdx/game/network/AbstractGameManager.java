package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Listener;

import java.net.InetAddress;

public abstract class AbstractGameManager {
    private InetAddress ip;

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public abstract void sendToServer(Object message);

    public abstract void addListener(Listener listener);
}
