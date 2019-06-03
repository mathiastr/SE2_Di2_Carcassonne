package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.meeple.MeeplePlacement;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Side;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;

public class MeepleTest2 {

    private Stage stageMock;
    private ArrayList<Player> players;
    private Position posMock;


    public MeepleTest2() {
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        posMock = mock(Position.class);

    }

    private void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(GameBoard.Color.black, "A"));
        players.add(new Player(GameBoard.Color.blue, "B"));
    }

   
    /**
     * Test throws an exception cause of UI in the code in MeeplePlacement.class
     */
    @Test
    public void Test1() {

        preparePlayers();
        GameBoard gb = new GameBoard(stageMock, stageMock, players, true, players.get(0), null);
        MeeplePlacement mp = new MeeplePlacement(gb);

        TileActor t = new TileActor(posMock);
        gb.setCurrentTile(t);
        gb.placeCurrentTileAt(posMock);
        City city = new City(Side.RIGHT);
        t.addFeature(city);
        mp.placeMeeple(Side.RIGHT, city, t.getPosition());
        System.out.print(city.hasMeepleOnIt());
        Assert.assertTrue(city.hasMeepleOnIt());
    }


}


