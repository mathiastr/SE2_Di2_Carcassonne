package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Listener;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public abstract class AbstractGameManager {
    private InetAddress ip;

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public abstract void addListener(Listener listener);

    public abstract void sendToHost(final Object message);

    public abstract void sendToAll(final Object message);
}
