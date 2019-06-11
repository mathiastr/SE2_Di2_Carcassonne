package com.mygdx.game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends AbstractGameManager{
    Client client;

    public GameClient() {
        client = new Client();
        Network.register(client);
        client.start();
    }

    public List<InetAddress> discover() {
        try {
            ArrayList<InetAddress> l = new ArrayList<>();
            // home         192.168.1.2
            // uni          143.205.187.139
            // other uni    192.168.43.187
            // mobile       10.74.199.188
            l.add(InetAddress.getByName("192.168.1.2"));
            l.add(InetAddress.getByName("10.0.0.4"));
            l.add(InetAddress.getByName("10.0.0.13"));
            l.add(InetAddress.getByName("143.205.186.57"));
            return l;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client.discoverHosts(Network.UDP, 5000);
    }

    public Client getClient() {
        return client;
    }

    public void initConnection (final InetAddress host, final Object message){
        client.setKeepAliveTCP(10000);
        new Thread("Connect") {
            public void run () {
                try {
                    //
                    client.connect(3000, host, Network.TCP, Network.UDP);
/*                    new Thread("Updating") {
                        public void run () {
                            try {
                                client.update(1000);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start(); */
                    client.sendTCP(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    @Override
    public void sendToServer(final Object message) {
        System.out.println("DEGUG ::: sendtoAll   " + message.toString());
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
