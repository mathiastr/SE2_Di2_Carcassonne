package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ScoreRoadTest {

    private GameScreen screenMock;
    private Stage stageMock;
    private ArrayList<Player> players;
    private final GameScreen gameScreen;

    public ScoreRoadTest() {
        screenMock = mock(GameScreen.class);
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        gameScreen = mock(GameScreen.class);
    }

    public void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(GameBoard.Color.BLACK, "A"));
        players.add(new Player(GameBoard.Color.BLUE, "B"));
    }

    @Test
    public void scoreMonastery() {
    }

    @Test
    public void scoreRoadOrCity() {

        preparePlayers();

        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);

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
    public void isValidAtPosition() {

        preparePlayers();

        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.RIGHT)));
            gb.addTileOnBoard(t, new Position(0, 0));
        }

        TileActor t;

        {
            t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.LEFT)));
           // gb.addTileOnBoard(t, new Position(1, 0));
        }
        boolean result = gb.getBoard().isValidAtPosition(t, new Position(1, 0));
        Assert.assertEquals(true, result);
    }

    @Test
    public void getValidPositions() {

        preparePlayers();

        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.RIGHT, Side.BOTTOM)));
            gb.addTileOnBoard(t, new Position(0, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.RIGHT, Side.LEFT)));
            gb.addTileOnBoard(t, new Position(1, 0));
        }


        TileActor t;

        {
            t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.LEFT, Side.RIGHT)));
            // gb.addTileOnBoard(t, new Position(1, 0));
        }

        HashSet<Position> expectedPositions = new HashSet<>();
        expectedPositions.add(new Position(2, 0));
        expectedPositions.add(new Position(1, -1));
        expectedPositions.add(new Position(1, 1));
        expectedPositions.add(new Position(0, 1));

        Assert.assertTrue(expectedPositions.containsAll(gb.getBoard().getValidPositionsForTile(t)));
        Assert.assertEquals(expectedPositions.size(), gb.getBoard().getValidPositionsForTile(t).size());
    }


    @Test
    public void circleRoad() {

        preparePlayers();

        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.RIGHT, Side.BOTTOM)));
            gb.addTileOnBoard(t, new Position(0, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.LEFT, Side.BOTTOM)));
            gb.addTileOnBoard(t, new Position(1, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.TOP, Side.LEFT)));
            gb.addTileOnBoard(t, new Position(1, -1));
        }

        TileActor t = new TileActor();
        {
            t.addFeature(new Road(Arrays.asList(Side.RIGHT, Side.TOP)));
            gb.addTileOnBoard(t, new Position(0, -1));
        }

        int result = gb.getBoard().scoreRoadOrCity(t, t.getFeatureAtSide(Side.TOP));
        Assert.assertEquals(4, result);

    }


    @Test
    public void getScore() {

        preparePlayers();

        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.RIGHT, Side.BOTTOM)));
            gb.addTileOnBoard(t, new Position(0, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.LEFT, Side.BOTTOM)));
            gb.addTileOnBoard(t, new Position(1, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.TOP, Side.LEFT)));
            gb.addTileOnBoard(t, new Position(1, -1));
        }

        TileActor t = new TileActor();
        {
            t.addFeature(new City(Arrays.asList(Side.RIGHT, Side.TOP)));
            gb.addTileOnBoard(t, new Position(0, -1));
        }

        int result = gb.getBoard().getScore(t, t.getFeatures().get(0));
        Assert.assertEquals(8, result);

    }

    @Test
    public void roadOwners() {

        players = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();

        player1.setColor(GameBoard.Color.BLACK);
        player2.setColor(GameBoard.Color.GREEN);

        player1.getMeeples().add(new Meeple(GameBoard.Color.BLACK));
        player2.getMeeples().add(new Meeple(GameBoard.Color.GREEN));

        players.add(player1);
        players.add(player2);

        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);


        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.RIGHT)));
            Meeple meeple = player1.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.RIGHT);
            t.addMeeple(meeple);
            gb.addTileOnBoard(t, new Position(-1, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new Road(Arrays.asList(Side.LEFT, Side.RIGHT)));
            Meeple meeple = player2.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.RIGHT);
            t.addMeeple(meeple);
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

        List<Player> owners = gb.getFeatureOwners(t, t.getFeatureAtSide(Side.TOP));
        Assert.assertTrue(owners.contains(player1));
        Assert.assertTrue(owners.contains(player2));
    }

    // @Test
    public void testCheating() {
        preparePlayers();
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);
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
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);

        gb.setCurrentTile(newCurrentTile);
        Assert.assertEquals(newCurrentTile, gb.getCurrentTile());
    }

}