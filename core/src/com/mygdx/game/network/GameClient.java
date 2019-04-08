package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class GameClient extends AbstractGameManager{
    Client client;

    public GameClient() {
        client = new Client();
        Network.register(client);
        client.start();
        client.addListener(new Listener(){
            public void received (Connection connection, Object object) {
                if (object instanceof TestOutput) {
                    TestOutput response = (TestOutput)object;
                    System.out.println(response.getTest());
                }
            }
        });
    }

    public void connect(final InetAddress host){
        client.setKeepAliveTCP(1000);
        new Thread("Connect") {
            public void run () {
                try {
                    client.connect(10000000, host, Network.TCP, Network.UDP);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void sendMessage(final String message){
        if (!client.isConnected()) {
            try {
                client.reconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new Thread("Sending") {
            public void run() {
                client.sendTCP(new TestOutput(message));
            }
        }.start();
    }

    public List<InetAddress> discover() {
        return client.discoverHosts(Network.UDP, 2000);
    }
}
