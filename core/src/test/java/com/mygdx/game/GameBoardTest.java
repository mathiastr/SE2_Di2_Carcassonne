package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.utility.GraphicsBackend;
import com.mygdx.game.utility.GraphicsBackendForTests;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class GameBoardTest {
    private GameScreen screenMock;
    private Stage stageMock;
    private ArrayList<Player> players;
    private final GameScreen gameScreen;

    public GameBoardTest() {
        screenMock = mock(GameScreen.class);
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        Gdx.gl20 = mock(GL20.class);
        gameScreen = mock(GameScreen.class);
        Carcassonne.skin = mock(Skin.class);
    }


    public void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(GameBoard.Color.BLACK, "A"));
        players.add(new Player(GameBoard.Color.BLUE, "B"));
    }

    @Test
    public void getRandomColorTest() {
        preparePlayers();
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, new GraphicsBackend());

        List<GameBoard.Color> except = new ArrayList<GameBoard.Color>();
        except.add(GameBoard.Color.RED);
        GameBoard.Color color = GameBoard.Color.getRandomColorExcept(except);

        Assert.assertFalse(except.contains(color));

    }

    @Test
    public void overall() {
        preparePlayers();
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, new GraphicsBackendForTests());
        gb.setCurrentPlayer(players.get(0));
        gb.init();
        TileActor currrent = gb.getCurrentTile();

        Player currentPlayer = gb.getCurrentPlayer();
        Assert.assertTrue(currentPlayer.getName().equals(players.get(0).getName()));

        List<Player> createdPlayers = gb.getPlayers();
        Assert.assertEquals(createdPlayers, players);

        try {
            TileActor randomTile = gb.getRandomElement(gb.getBoard().availableTiles);
            gb.setCurrentTile(randomTile);

            Meeple m = new Meeple(GameBoard.Color.BLACK);
            gb.addMeepleOnCurrentTile(m);
            Assert.assertTrue(gb.getCurrentTile().getMeeples().contains(m));
        } catch (Exception e) {

        }

        int a = 5;
    }
}
