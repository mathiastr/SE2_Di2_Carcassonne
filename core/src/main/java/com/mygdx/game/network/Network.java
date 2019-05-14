package main.java.com.mygdx.game.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import main.java.com.mygdx.game.Player;
import main.java.com.mygdx.game.tile.City;
import main.java.com.mygdx.game.tile.Field;
import main.java.com.mygdx.game.GameBoard;
import main.java.com.mygdx.game.meeple.Meeple;
import main.java.com.mygdx.game.meeple.MeepleType;
import main.java.com.mygdx.game.tile.Monastery;
import main.java.com.mygdx.game.Position;
import main.java.com.mygdx.game.tile.Road;
import main.java.com.mygdx.game.tile.Side;
import main.java.com.mygdx.game.network.response.ConnectMessage;
import main.java.com.mygdx.game.network.response.CurrentTileMessage;
import main.java.com.mygdx.game.network.response.ErrorMessage;
import main.java.com.mygdx.game.network.response.InitGameMessage;
import main.java.com.mygdx.game.network.response.PlayerGameMessage;
import main.java.com.mygdx.game.network.response.SimpleMessage;
import main.java.com.mygdx.game.network.response.TilePlacementMessage;
import main.java.com.mygdx.game.network.response.TurnEndMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class Network {
    //Port Numbers randomly chosen 56773 59885
    static public final int TCP = 56773;
    static public final int UDP = 59885;

    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(PlayerGameMessage.class);
        kryo.register(InitGameMessage.class);
        kryo.register(ArrayList.class);
        kryo.register(TilePlacementMessage.class);
        kryo.register(Position.class);
        kryo.register(Meeple.class);
        kryo.register(SimpleMessage.class);
        kryo.register(CurrentTileMessage.class);
        kryo.register(TurnEndMessage.class);
        kryo.register(GameBoard.Color.class);
        kryo.register(City.class);
        kryo.register(Road.class);
        kryo.register(Field.class);
        kryo.register(Monastery.class);
        kryo.register(Side.class);
        kryo.register(MeepleType.class);
        kryo.register(HashMap.class);
        kryo.register(ErrorMessage.class);
        kryo.register(ConnectMessage.class);
        kryo.register(Player.class);
    }
}

