package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.actor.PlayerStatusActor;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.meeple.MeeplePlacement;
import com.mygdx.game.meeple.MeepleTextureFactory;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MeeplePlacementTest {

    private Stage stageMock;
    private ArrayList<Player> players;
    private Position posMock;
    private Texture meepleTexture;
    private GameScreen gameScreen;

    public MeeplePlacementTest() {
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        posMock = mock(Position.class);
        gameScreen = mock(GameScreen.class);
        gameScreen.placeMeeple = mock(TextButton.class);
    }
    @Before
    public void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(GameBoard.Color.black, "A"));
        players.add(new Player(GameBoard.Color.blue, "B"));
    }


    /**
     * Test if the hasMeepleOnIt() works
     */
    @Test
    public void hasMeepleOnItTest() throws Exception {

        // TODO: Verify mocks.
        preparePlayers();

        // Set up Player mock (needed by GameBoard mock)
        Player player = mock(Player.class);
        when(player.getColor()).thenReturn(GameBoard.Color.black);

        // Set up GameBoard mock.
        GameBoard gb = mock(GameBoard.class);
        when(gb.getCurrentPlayer()).thenReturn(player);
        when(gb.getUnusedCurrentPlayerMeeple()).thenAnswer((invocationOnMock -> mock(Meeple.class)));
        when(gb.getPlayerActor(Mockito.anyInt())).thenAnswer((invocationOnMock -> mock(PlayerStatusActor.class)));

        // Set up MeepleTextureFactory mock.
        MeepleTextureFactory factoryMock = mock(MeepleTextureFactory.class);
        when(factoryMock.createMeepleImage(Mockito.any())).thenAnswer((invocationOnMock) -> mock(ImageButton.class));

        MeeplePlacement mp = new MeeplePlacement(gb, gameScreen, factoryMock);

        TileActor t = new TileActor(posMock);
        gb.setCurrentTile(t);
        gb.placeCurrentTileAt(posMock);
        City city = new City(Side.RIGHT);
        t.addFeature(city);
        mp.placeMeeple(Side.RIGHT, city, t.getPosition());
        Assert.assertTrue(city.hasMeepleOnIt());

        verify(player, times(1)).getColor();
        verify(factoryMock, times(1)).createMeepleImage(GameBoard.Color.black);
        verify(gb, times(1)).getCurrentPlayer();
        verify(gb, times(1)).getUnusedCurrentPlayerMeeple();

    }

}


