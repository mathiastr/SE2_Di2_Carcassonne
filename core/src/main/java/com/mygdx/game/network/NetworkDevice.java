package main.java.com.mygdx.game.network;

import java.net.InetAddress;

public class NetworkDevice {
    private String deviceName;
    private InetAddress ip;

    public NetworkDevice(String deviceName, InetAddress ip){
        this.deviceName = deviceName;
        this.ip = ip;
    }

    public InetAddress getIp() {
        return ip;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }
}
