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
import java.util.Enumeration;

public class GameServer extends AbstractGameManager{

    Server server;

    public GameServer() throws IOException {
        server = new Server(32768,16384);
        Network.register(server);

        server.bind(Network.TCP,Network.UDP);
        server.start();
        this.setIp(ip());
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof TestOutput) {
                    TestOutput request = (TestOutput) object;
                    System.out.println(request.getTest());
                    Gdx.app.debug("network",request.getTest());

                    TestOutput response = new TestOutput("hi Client");
                    connection.sendTCP(response);
                }
            }
        });
    }

    public void destroy() {
        server.stop();
    }

    public InetAddress ip() throws SocketException {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        NetworkInterface ni;
        while (nis.hasMoreElements()) {
            ni = nis.nextElement();
            if (!ni.isLoopback()/*not loopback*/ && ni.isUp()/*it works now*/) {
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


}
