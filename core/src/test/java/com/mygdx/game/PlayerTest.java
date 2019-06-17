package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screen.GameScreen;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class PlayerTest {
    private GameScreen screenMock;
    private Stage stageMock;
    private ArrayList<Player> players;
    private final GameScreen gameScreen;

    public PlayerTest() {
        screenMock = mock(GameScreen.class);
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        gameScreen = mock(GameScreen.class);
    }

    @Test
    public void testPayer() {
        Player player = new Player();

        player.setScore(5);
        String name = "P1";
        player.setName(name);
        player.setId(123);

        Assert.assertEquals(player.getName(), name);

    }

}
