package com.mygdx.game.network;


import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class GameServer extends AbstractGameManager{

    public static final int WRITE_BUFFER_SIZE = 32768;
    public static final int OBJECT_BUFFER_SIZE = 16384;
    private Server server;
    private List<NetworkDevice> deviceList;

    public GameServer() throws IOException {
        server = new Server(WRITE_BUFFER_SIZE, OBJECT_BUFFER_SIZE);
        Network.register(server);

        deviceList = new ArrayList<>();
        server.bind(Network.TCP,Network.UDP);
        server.start();
        this.setIp(ip());

    }

    public void destroy() {
        server.stop();
    }


    private InetAddress ip() throws SocketException {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        NetworkInterface ni;
        while (nis.hasMoreElements()) {
            ni = nis.nextElement();
            if (!ni.isLoopback() && ni.isUp()) {
                for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                    //filter for ipv4/ipv6
                    if (ia.getAddress().getAddress().length == 4) {
                        //4 for ipv4, 16 for ipv6
                        return ia.getAddress();
                    }
                }
            }
        }
        return null;
    }



    public Server getServer() {
        return server;
    }

    public void setDeviceList(List<NetworkDevice> deviceList) {
        this.deviceList = deviceList;
    }

    public List<NetworkDevice> getDeviceList() {
        return deviceList;
    }

    public void addDevice(NetworkDevice device) {
        deviceList.add(device);
    }

    public void sendToAll(final Object message) {
        new Thread("Sending") {
            public void run () {
                server.sendToAllTCP(message);
            }
        }.start();
    }

    @Override
    public void addListener(Listener listener) {
        server.addListener(listener);
    }

    @Override
    public void sendToHost(Object message) {

    }
}
