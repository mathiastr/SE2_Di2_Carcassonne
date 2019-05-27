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

    Stage stageMock;
    ArrayList<Player> players;

    public GameBoardTest() {
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
    }

    private void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(GameBoard.Color.black, "A"));
        players.add(new Player(GameBoard.Color.blue, "B"));
    }

    @Test
    public void scoreMonastery() {
    }

    @Test
    public void scoreRoadOrCity() {

        preparePlayers();

        GameBoard gb = new GameBoard(stageMock, stageMock, players, true, players.get(0), null);


        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.right)));
            gb.addTileOnBoard(t, new Position(-1, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.left, Side.right)));
            gb.addTileOnBoard(t, new Position(0, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.left, Side.bottom)));
            gb.addTileOnBoard(t, new Position(1, 0));
        }

        TileActor t = new TileActor();
        {
            t.addFeature(new Road(Arrays.asList(Side.top)));
            t.addFeature(new Road(Arrays.asList(Side.right)));
            t.addFeature(new Road(Arrays.asList(Side.bottom)));
            gb.addTileOnBoard(t, new Position(1, -1));
        }

        int result = gb.getBoard().scoreRoadOrCity(t, t.getFeatureAtSide(Side.top));

        Assert.assertEquals(4, result);


    }

    @Test
    public void testCheating() {
        preparePlayers();
        GameBoard gb = new GameBoard(stageMock, stageMock, players, true, players.get(0), null);
        gb.performCheatAction(players.get(0)); //cheat Meeple
        assert players.get(0).getNumberOfMeeples() == Player.MEEPLE_COUNT + 1;
        assert players.get(1).getNumberOfMeeples() == Player.MEEPLE_COUNT;

        gb.setCurrentPlayer(players.get(1));

        gb.performCheatAction(players.get(0)); //detect cheat rightfully
        assert players.get(0).getNumberOfMeeples() == 0;
        assert players.get(1).getNumberOfMeeples() == Player.MEEPLE_COUNT;

        gb.performCheatAction(players.get(0)); //detect cheat wrongfully
        assert players.get(0).getNumberOfMeeples() == 0;
        assert players.get(1).getNumberOfMeeples() == 0;

    }
}