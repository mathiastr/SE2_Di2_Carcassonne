package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class GameBoardTest {
    public GameBoardTest() {
    }

    @Test
    public void scoreMonastery() {
    }

    @Test
    public void scoreRoadOrCity() {
        Stage stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);

        List<Player> players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());

        GameBoard gb = new GameBoard(stageMock, stageMock, players, true, players.get(0), null);


        {
            TileActor t = new TileActor(gb);
            t.addFeature(new Road(Arrays.asList(Side.right)));
            gb.addTileOnBoard(t, new Position(-1, 0));
        }

        {
            TileActor t = new TileActor(gb);
            t.addFeature(new Road(Arrays.asList(Side.left, Side.right)));
            gb.addTileOnBoard(t, new Position(0, 0));
        }

        {
            TileActor t = new TileActor(gb);
            t.addFeature(new Road(Arrays.asList(Side.left, Side.bottom)));
            gb.addTileOnBoard(t, new Position(1, 0));
        }

        TileActor t = new TileActor(gb);
        {
            t.addFeature(new Road(Arrays.asList(Side.top)));
            t.addFeature(new Road(Arrays.asList(Side.right)));
            t.addFeature(new Road(Arrays.asList(Side.bottom)));
            gb.addTileOnBoard(t, new Position(1, -1));
        }

        int result = gb.scoreRoadOrCity(t, t.getFeatureAtSide(Side.top));

        Assert.assertEquals(4, result);


    }
}