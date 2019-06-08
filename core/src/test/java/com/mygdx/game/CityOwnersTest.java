package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class CityOwnersTest {

    Stage stageMock;
    ArrayList<Player> players;

    public CityOwnersTest() {
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
    }

    private void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(GameBoard.Color.black, "A"));
        players.add(new Player(GameBoard.Color.green, "B"));
    }

    @Test
    public void scoreMonastery() {
    }

    @Test
    public void scoreCity() {

        preparePlayers();

        GameBoard gb = new GameBoard(stageMock, stageMock, players, true, players.get(0), null);

        players = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();

        player1.setColor(GameBoard.Color.black);
        player2.setColor(GameBoard.Color.green);

        player1.getMeeples().add(new Meeple(GameBoard.Color.black));

        player2.getMeeples().add(new Meeple(GameBoard.Color.green));
        player2.getMeeples().add(new Meeple(GameBoard.Color.green));

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.right)));

            Meeple meeple = player1.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.right);
            t.addMeeple(meeple);

            gb.addTileOnBoard(t, new Position(0, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.left, Side.right, Side.bottom)));
            gb.addTileOnBoard(t, new Position(1, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.left, Side.bottom)));
            gb.addTileOnBoard(t, new Position(2, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.top)));

            Meeple meeple = player2.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.top);
            t.addMeeple(meeple);

            gb.addTileOnBoard(t, new Position(1, -1));
        }

        TileActor t = new TileActor();

        {
            t.addFeature(new City(Arrays.asList(Side.top)));

            Meeple meeple = player2.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.top);
            t.addMeeple(meeple);

            gb.addTileOnBoard(t, new Position(2, -1));
        }


        List<Player> owners = gb.getFeatureOwners(t, t.getFeatureAtSide(Side.top));

        for (Player owner: owners) {
            Assert.assertFalse(owner.getColor().equals(player1.getColor()));
            Assert.assertTrue(owner.getColor().equals(player2.getColor()));
        }

    }
}