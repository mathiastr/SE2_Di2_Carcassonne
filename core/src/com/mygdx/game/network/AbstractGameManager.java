package com.mygdx.game.network;

import java.net.InetAddress;

public abstract class AbstractGameManager {
    private InetAddress ip;

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }
}
