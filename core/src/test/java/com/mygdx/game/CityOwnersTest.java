package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class CityOwnersTest {

    GameScreen screenMock;
    Stage stageMock;
    ArrayList<Player> players;
    private final GameScreen gameScreen;

    public CityOwnersTest() {
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        gameScreen = mock(GameScreen.class);
    }

    private void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(GameBoard.Color.BLACK, "A"));
        players.add(new Player(GameBoard.Color.GREEN, "B"));
    }

    @Test
    public void scoreMonastery() {
    }

    @Test
    public void scoreCity() {

        preparePlayers();

        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen,  new GraphicsBackend());

        players = new ArrayList<>();
        Player player1 = new Player();
        Player player2 = new Player();

        player1.setColor(GameBoard.Color.BLACK);
        player2.setColor(GameBoard.Color.GREEN);

        player1.getMeeples().add(new Meeple(GameBoard.Color.BLACK));

        player2.getMeeples().add(new Meeple(GameBoard.Color.GREEN));
        player2.getMeeples().add(new Meeple(GameBoard.Color.GREEN));

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.RIGHT)));

            Meeple meeple = player1.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.RIGHT);
            t.addMeeple(meeple);

            gb.addTileOnBoard(t, new Position(0, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.LEFT, Side.RIGHT, Side.BOTTOM)));
            gb.addTileOnBoard(t, new Position(1, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.LEFT, Side.BOTTOM)));
            gb.addTileOnBoard(t, new Position(2, 0));
        }

        {
            TileActor t = new TileActor();
            t.addFeature(new City(Arrays.asList(Side.TOP)));

            Meeple meeple = player2.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.TOP);
            t.addMeeple(meeple);

            gb.addTileOnBoard(t, new Position(1, -1));
        }

        TileActor t = new TileActor();

        {
            t.addFeature(new City(Arrays.asList(Side.TOP)));

            Meeple meeple = player2.getUnusedMeeple();
            meeple.setFeature(t.getFeatures().get(0));
            meeple.setSide(Side.TOP);
            t.addMeeple(meeple);

            gb.addTileOnBoard(t, new Position(2, -1));
        }


        List<Player> owners = gb.getFeatureOwners(t, t.getFeatureAtSide(Side.TOP));

        for (Player owner: owners) {
            Assert.assertFalse(owner.getColor().equals(player1.getColor()));
            Assert.assertTrue(owner.getColor().equals(player2.getColor()));
        }

    }
}