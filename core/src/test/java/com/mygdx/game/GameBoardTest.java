package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;

public class GameBoardTest {

    Stage stageMock;
    ArrayList<Player> players;
    private final GameScreen gameScreen;

    public GameBoardTest() {
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        gameScreen = mock(GameScreen.class);
    }

    public void preparePlayers() {
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

        GameBoard gb = new GameBoard(stageMock, stageMock, players, true, players.get(0), null, gameScreen);

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.RIGHT)));
            gb.addTileOnBoard(t, new Position(-1, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.LEFT, Side.RIGHT)));
            gb.addTileOnBoard(t, new Position(0, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.LEFT, Side.BOTTOM)));
            gb.addTileOnBoard(t, new Position(1, 0));
        }

        TileActor t = new TileActor();
        {
            t.addFeature(new Road(Arrays.asList(Side.TOP)));
            t.addFeature(new Road(Arrays.asList(Side.RIGHT)));
            t.addFeature(new Road(Arrays.asList(Side.BOTTOM)));
            gb.addTileOnBoard(t, new Position(1, -1));
        }

        int result = gb.getBoard().scoreRoadOrCity(t, t.getFeatureAtSide(Side.TOP));

        Assert.assertEquals(4, result);


    }

    @Test
    public void roadOwners() {

        players = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();

        player1.setColor(GameBoard.Color.black);
        player2.setColor(GameBoard.Color.green);

        player1.getMeeples().add(new Meeple(GameBoard.Color.black));
        player2.getMeeples().add(new Meeple(GameBoard.Color.green));

        players.add(player1);
        players.add(player2);

        GameBoard gb = new GameBoard(stageMock, stageMock, players, true, players.get(0), null);


        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.right)));
            Meeple meeple = player1.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.right);
            t.addMeeple(meeple);
            gb.addTileOnBoard(t, new Position(-1, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.left, Side.right)));
            Meeple meeple = player2.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.right);
            t.addMeeple(meeple);
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

        List<Player> owners = gb.getFeatureOwners(t, t.getFeatureAtSide(Side.top));
        Assert.assertTrue(owners.contains(player1));
        Assert.assertTrue(owners.contains(player2));
    }

    // @Test
    public void testCheating() {
        preparePlayers();
        GameBoard gb = new GameBoard(stageMock, stageMock, players, true, players.get(0), null, gameScreen);
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

    /**
     * Tests the setter of the current tile
     */
    @Test
    public void setCurrentTileTest(){
        preparePlayers();

        TileActor newCurrentTile = new TileActor();
        GameBoard gb = new GameBoard(stageMock, stageMock, players, true, players.get(0), null, gameScreen);

        gb.setCurrentTile(newCurrentTile);
        Assert.assertEquals(newCurrentTile, gb.getCurrentTile());
    }
}