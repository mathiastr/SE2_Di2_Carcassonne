package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.actor.PlayerStatusActor;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.meeple.MeeplePlacement;
import com.mygdx.game.meeple.MeepleTextureFactory;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MeeplePlacementTest {

    private ArrayList<Player> players;
    private Position posMock;
    private GameScreen gameScreen;

    public MeeplePlacementTest() {
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        posMock = mock(Position.class);
        gameScreen = mock(GameScreen.class);
        gameScreen.placeMeeple = mock(TextButton.class);
    }

    @Before
    public void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(GameBoard.Color.BLACK, "A"));
        players.add(new Player(GameBoard.Color.BLUE, "B"));
    }


    /**
     * Test if the hasMeepleOnIt() works
     */
    @Test
    public void hasMeepleOnRightCityTest() throws Exception {

        preparePlayers();

        // Set up Player mock (needed by GameBoard mock)
        Player player = mock(Player.class);
        when(player.getColor()).thenReturn(GameBoard.Color.BLACK);

        // Set up GameBoard mock.
        GameBoard gb = mock(GameBoard.class);
        when(gb.getCurrentPlayer()).thenReturn(player);
        when(gb.getUnusedCurrentPlayerMeeple()).thenAnswer((invocationOnMock -> mock(Meeple.class)));
        when(gb.getPlayerActor(Mockito.anyInt())).thenAnswer((invocationOnMock -> mock(PlayerStatusActor.class)));

        // Set up MeepleTextureFactory mock.
        MeepleTextureFactory factoryMock = mock(MeepleTextureFactory.class);
        when(factoryMock.createMeepleImage(Mockito.any())).thenAnswer((invocationOnMock) -> mock(ImageButton.class));

        MeeplePlacement mp = new MeeplePlacement(gb, gameScreen, factoryMock);

        TileActor ta = new TileActor(posMock);
        gb.setCurrentTile(ta);
        gb.placeCurrentTileAt(posMock);
        City city = new City(Side.RIGHT);
        ta.addFeature(city);
        mp.placeMeeple(Side.RIGHT, city, ta.getPosition());
        Assert.assertTrue(city.hasMeepleOnIt());

        verify(player, times(1)).getColor();
        verify(factoryMock, times(1)).createMeepleImage(GameBoard.Color.BLACK);
        verify(gb, times(1)).getCurrentPlayer();
        verify(gb, times(1)).getUnusedCurrentPlayerMeeple();

    }

    @Test
    public void hasMeepleOnLeftRoadTest() throws Exception {

        preparePlayers();

        // Set up Player mock (needed by GameBoard mock)
        Player player = mock(Player.class);
        when(player.getColor()).thenReturn(GameBoard.Color.BLACK);

        // Set up GameBoard mock.
        GameBoard gb = mock(GameBoard.class);
        when(gb.getCurrentPlayer()).thenReturn(player);
        when(gb.getUnusedCurrentPlayerMeeple()).thenAnswer((invocationOnMock -> mock(Meeple.class)));
        when(gb.getPlayerActor(Mockito.anyInt())).thenAnswer((invocationOnMock -> mock(PlayerStatusActor.class)));

        // Set up MeepleTextureFactory mock.
        MeepleTextureFactory factoryMock = mock(MeepleTextureFactory.class);
        when(factoryMock.createMeepleImage(Mockito.any())).thenAnswer((invocationOnMock) -> mock(ImageButton.class));

        MeeplePlacement mp = new MeeplePlacement(gb, gameScreen, factoryMock);

        TileActor ta = new TileActor(posMock);
        gb.setCurrentTile(ta);
        gb.placeCurrentTileAt(posMock);
        Road road = new Road(Side.LEFT);
        ta.addFeature(road);
        mp.placeMeeple(Side.LEFT, road, ta.getPosition());
        Assert.assertTrue(road.hasMeepleOnIt());

        verify(player, times(1)).getColor();
        verify(factoryMock, times(1)).createMeepleImage(GameBoard.Color.BLACK);
        verify(gb, times(1)).getCurrentPlayer();
        verify(gb, times(1)).getUnusedCurrentPlayerMeeple();

    }

    @Test
    public void hasMeepleOnBottomRoadTest() throws Exception {

        preparePlayers();

        // Set up Player mock (needed by GameBoard mock)
        Player player = mock(Player.class);
        when(player.getColor()).thenReturn(GameBoard.Color.BLACK);

        // Set up GameBoard mock.
        GameBoard gb = mock(GameBoard.class);
        when(gb.getCurrentPlayer()).thenReturn(player);
        when(gb.getUnusedCurrentPlayerMeeple()).thenAnswer((invocationOnMock -> mock(Meeple.class)));
        when(gb.getPlayerActor(Mockito.anyInt())).thenAnswer((invocationOnMock -> mock(PlayerStatusActor.class)));

        // Set up MeepleTextureFactory mock.
        MeepleTextureFactory factoryMock = mock(MeepleTextureFactory.class);
        when(factoryMock.createMeepleImage(Mockito.any())).thenAnswer((invocationOnMock) -> mock(ImageButton.class));

        MeeplePlacement mp = new MeeplePlacement(gb, gameScreen, factoryMock);

        TileActor ta = new TileActor(posMock);
        gb.setCurrentTile(ta);
        gb.placeCurrentTileAt(posMock);
        Road road = new Road(Side.BOTTOM);
        ta.addFeature(road);
        mp.placeMeeple(Side.BOTTOM, road, ta.getPosition());
        Assert.assertTrue(road.hasMeepleOnIt());

        verify(player, times(1)).getColor();
        verify(factoryMock, times(1)).createMeepleImage(GameBoard.Color.BLACK);
        verify(gb, times(1)).getCurrentPlayer();
        verify(gb, times(1)).getUnusedCurrentPlayerMeeple();

    }
    @Test
    public void hasMeepleOnTopMonasteryTest() throws Exception {

        preparePlayers();

        // Set up Player mock (needed by GameBoard mock)
        Player player = mock(Player.class);
        when(player.getColor()).thenReturn(GameBoard.Color.BLACK);

        // Set up GameBoard mock.
        GameBoard gb = mock(GameBoard.class);
        when(gb.getCurrentPlayer()).thenReturn(player);
        when(gb.getUnusedCurrentPlayerMeeple()).thenAnswer((invocationOnMock -> mock(Meeple.class)));
        when(gb.getPlayerActor(Mockito.anyInt())).thenAnswer((invocationOnMock -> mock(PlayerStatusActor.class)));

        // Set up MeepleTextureFactory mock.
        MeepleTextureFactory factoryMock = mock(MeepleTextureFactory.class);
        when(factoryMock.createMeepleImage(Mockito.any())).thenAnswer((invocationOnMock) -> mock(ImageButton.class));

        MeeplePlacement mp = new MeeplePlacement(gb, gameScreen, factoryMock);

        TileActor ta = new TileActor(posMock);
        gb.setCurrentTile(ta);
        gb.placeCurrentTileAt(posMock);
        Monastery monastery = new Monastery(Side.TOP);
        ta.addFeature(monastery);
        mp.placeMeeple(Side.TOP, monastery, ta.getPosition());
        Assert.assertTrue(monastery.hasMeepleOnIt());

        verify(player, times(1)).getColor();
        verify(factoryMock, times(1)).createMeepleImage(GameBoard.Color.BLACK);
        verify(gb, times(1)).getCurrentPlayer();
        verify(gb, times(1)).getUnusedCurrentPlayerMeeple();

    }
}


