package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.network.response.CheatType;
import com.mygdx.game.screen.GameScreen;

import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class CheatTest {

    private GameScreen screenMock;
    private Stage stageMock;
    private ArrayList<Player> players;
    private final GameScreen gameScreen;

    public CheatTest() {
        screenMock = mock(GameScreen.class);
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        gameScreen = mock(GameScreen.class);
    }

    public void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(1, GameBoard.Color.BLACK, "A"));
        players.add(new Player(2, GameBoard.Color.BLUE, "B"));
    }

    @Test
    public void testScoreCheating() {
        preparePlayers();

        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);

        gb.cheat(CheatType.SCORE, players.get(0), players.get(0)); //cheat Meeple
        assert players.get(0).getScore() == Player.CHEAT_SCORE;
        assert players.get(1).getScore() == 0;

        gb.cheat(CheatType.SCORE, players.get(0), players.get(1)); //detect cheat rightfully
        assert players.get(0).getScore() == Player.CHEAT_SCORE * (-2);
        assert players.get(1).getScore() == 0;

        gb.cheat(CheatType.SCORE, players.get(0), players.get(1)); //detect cheat wrongfully
        assert players.get(0).getScore() == Player.CHEAT_SCORE * (-2);
        assert players.get(1).getScore() == Player.CHEAT_SCORE * (-3);


    }

    @Test
    public void testMeepleCheating() {
        preparePlayers();
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen);
        gb.cheat(CheatType.MEEPLE, players.get(0), players.get(0)); //cheat Meeple
        assert players.get(0).getNumberOfMeeples() == Player.MEEPLE_COUNT + 1;
        assert players.get(1).getNumberOfMeeples() == Player.MEEPLE_COUNT;

        gb.setCurrentPlayer(players.get(1));

        gb.cheat(CheatType.MEEPLE, players.get(0), players.get(1)); //detect cheat rightfully
        assert players.get(0).getNumberOfMeeples() == 0;
        assert players.get(1).getNumberOfMeeples() == Player.MEEPLE_COUNT;

        gb.cheat(CheatType.MEEPLE, players.get(0), players.get(1)); //detect cheat wrongfully
        assert players.get(0).getNumberOfMeeples() == 0;
        assert players.get(1).getNumberOfMeeples() == 0;

    }
}