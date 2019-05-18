package com.mygdx.game.network;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Player;
import com.mygdx.game.network.response.PlayerGameMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class GameServer extends AbstractGameManager{

    private Server server;
    private List<NetworkDevice> deviceList;
    public GameClient localClient;

    public GameServer() throws IOException {
        server = new Server(32768,16384);
        Network.register(server);

        deviceList = new ArrayList<NetworkDevice>();
        server.bind(Network.TCP,Network.UDP);
        server.start();
        this.setIp(ip());

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                server.sendToAllExceptTCP(connection.getID(), object);
            }
        });

        localClient = new GameClient();
        localClient.initConnection(InetAddress.getLocalHost(), new PlayerGameMessage(new Player(GameBoard.Color.getRandom(), "Server")));
    }

    public void destroy() {
        server.stop();
    }


    public InetAddress ip() throws SocketException {
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

    @Override
    public void sendToServer(final Object message) {
        System.out.println("DEGUG :::  server   sendtoAll   " + message.toString());
        new Thread("Sending") {
            public void run () {
                localClient.sendToServer(message);
            }
        }.start();
    }


    @Override
    public void sendToHost(final Object message){
        super.sendToHost(message);
        //to do
    }

    @Override
    public void addListener(Listener listener) {
        server.addListener(listener);
    }
}
