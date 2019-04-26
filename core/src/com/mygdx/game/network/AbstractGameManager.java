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

    public void sendToAll(Object message){}

    public void sendToHost(Object message){}

    public void addListener(Listener listener){}
}
