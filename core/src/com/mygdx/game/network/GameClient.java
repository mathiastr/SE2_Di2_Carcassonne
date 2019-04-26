package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class GameClient extends AbstractGameManager{
    Client client;

    public GameClient() {
        client = new Client();
        Network.register(client);
        client.start();
    }

    public List<InetAddress> discover() {
        return client.discoverHosts(Network.UDP, 2000);
    }

    public Client getClient() {
        return client;
    }

    public void initConnection (final InetAddress host, final Object message){
        client.setKeepAliveTCP(1000);
        new Thread("Connect") {
            public void run () {
                try {
                    client.connect(1000, host, Network.TCP, Network.UDP);
                    client.sendTCP(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    @Override
    public void sendToAll(final Object message) {
        new Thread("Sending") {
            public void run () {
                client.sendTCP(message);
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
        client.addListener(listener);
    }
}
