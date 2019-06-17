package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screen.GameScreen;

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
        gameScreen = mock(GameScreen.class);
    }


    public void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(GameBoard.Color.BLACK, "A"));
        players.add(new Player(GameBoard.Color.BLUE, "B"));
    }

    @Test
    public void getRandomColorTest() {
        preparePlayers();
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);

        List<GameBoard.Color> except = new ArrayList<GameBoard.Color>();
        except.add(GameBoard.Color.RED);
        GameBoard.Color color = GameBoard.Color.getRandomColorExcept(except);

        Assert.assertFalse(except.contains(color));

    }
}
