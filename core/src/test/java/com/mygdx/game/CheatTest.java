package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.network.response.CheatType;
import com.mygdx.game.network.response.TurnEndMessage;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.utility.GraphicsBackend;
import com.mygdx.game.utility.GraphicsBackendForTests;

import org.junit.Assert;
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
        Gdx.gl20 = mock(GL20.class);
        gameScreen = mock(GameScreen.class);
        Carcassonne.skin = mock(Skin.class);
    }

    public void preparePlayers() {
        players = new ArrayList<>();
        players.add(new Player(1, GameBoard.Color.BLACK, "A"));
        players.add(new Player(2, GameBoard.Color.BLUE, "B"));
    }

    @Test
    public void testScoreCheating() {
        preparePlayers();

        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen, new GraphicsBackend());

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
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen, new GraphicsBackend());
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

    @Test
    public void meepleCheatingWithThreePlayers() {
        preparePlayers();
        players.add(new Player(3, GameBoard.Color.GREEN, "C"));
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen, new GraphicsBackend());

        {
            gb.cheat(CheatType.MEEPLE, players.get(0), players.get(0));

        }
        Assert.assertTrue(players.get(0).getNumberOfMeeples() == Player.MEEPLE_COUNT +1);
        Assert.assertTrue(players.get(1).getNumberOfMeeples() == Player.MEEPLE_COUNT);
        Assert.assertTrue(players.get(2).getNumberOfMeeples() == Player.MEEPLE_COUNT);
    }

    @Test
    public void scoreCheatingWithThreePlayers() {
        preparePlayers();
        players.add(new Player(3, GameBoard.Color.GREEN, "C"));
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen, new GraphicsBackend());

        {
            gb.cheat(CheatType.SCORE, players.get(0), players.get(0));

        }

        Assert.assertTrue(players.get(0).getScore() == Player.CHEAT_SCORE);
        Assert.assertTrue(players.get(1).getScore() == 0);
        Assert.assertTrue(players.get(2).getScore() == 0);
    }

    @Test
    public void detectMeepleCheatingWithThreePlayersRight() {
        preparePlayers();
        players.add(new Player(3, GameBoard.Color.GREEN, "C"));
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen, new GraphicsBackend());

        {
            gb.cheat(CheatType.MEEPLE, players.get(0), players.get(0));
            gb.setCurrentPlayer(players.get(0));
            gb.cheat(CheatType.MEEPLE, players.get(0), players.get(1)); //detect cheat rightfully
        }

        Assert.assertTrue(players.get(0).getNumberOfMeeples() == 0);
        Assert.assertTrue(players.get(1).getNumberOfMeeples() == Player.MEEPLE_COUNT);
        Assert.assertTrue(players.get(2).getNumberOfMeeples() == Player.MEEPLE_COUNT);
    }

    @Test
    public void detectMeepleCheatingWithThreePlayersWrong() {
        preparePlayers();
        players.add(new Player(3, GameBoard.Color.GREEN, "C"));
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen, new GraphicsBackend());

        {
            gb.cheat(CheatType.MEEPLE, players.get(0), players.get(0));
            gb.setCurrentPlayer(players.get(0));
            gb.cheat(CheatType.MEEPLE, players.get(2), players.get(1)); //detect cheat rightfully
        }

        Assert.assertTrue(players.get(0).getNumberOfMeeples() == Player.MEEPLE_COUNT +1);
        Assert.assertTrue(players.get(1).getNumberOfMeeples() == 0);
        Assert.assertTrue(players.get(2).getNumberOfMeeples() == Player.MEEPLE_COUNT);
    }

    @Test
    public void detectScoreCheatingWithThreePlayersRight() {
        preparePlayers();
        players.add(new Player(3, GameBoard.Color.GREEN, "C"));
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen, new GraphicsBackend());

        {
            gb.cheat(CheatType.SCORE, players.get(0), players.get(0));
            gb.setCurrentPlayer(players.get(0));
            gb.cheat(CheatType.SCORE, players.get(0), players.get(1)); //detect cheat rightfully
        }

        Assert.assertTrue(players.get(0).getScore() == Player.CHEAT_SCORE * (-2));
        Assert.assertTrue(players.get(1).getScore() == 0);
        Assert.assertTrue(players.get(2).getScore() == 0);
    }

    @Test
    public void detectScoreCheatingWithThreePlayersWrong() {
        preparePlayers();
        players.add(new Player(3, GameBoard.Color.GREEN, "C"));
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen, new GraphicsBackend());

        {
            gb.cheat(CheatType.SCORE, players.get(0), players.get(0));
            gb.setCurrentPlayer(players.get(0));
            gb.cheat(CheatType.SCORE, players.get(2), players.get(1)); //detect cheat rightfully
        }

        Assert.assertTrue(players.get(0).getScore() == Player.CHEAT_SCORE);
        Assert.assertTrue(players.get(1).getScore() == Player.CHEAT_SCORE *(-3));
        Assert.assertTrue(players.get(2).getScore() == 0);
    }

    @Test
    public void detectCheatingAfterThreeTurn() {
        preparePlayers();
        GameBoard gb = new GameBoard(screenMock, stageMock, stageMock, players, true, players.get(0), null, gameScreen, new GraphicsBackendForTests());

        gb.setCurrentPlayer(players.get(0));
        gb.init();

        TileActor currrent = gb.getCurrentTile();
        {
            gb.cheat(CheatType.SCORE, players.get(0), players.get(0));
            gb.endMyTurn();
            gb.endMyTurn();
            gb.endMyTurn();
            gb.cheat(CheatType.SCORE, players.get(0), players.get(1)); //detect cheat rightfully
        }

        Assert.assertTrue(players.get(0).getScore() == Player.CHEAT_SCORE);
        Assert.assertTrue(players.get(1).getScore() == Player.CHEAT_SCORE *(-3));
    }
}