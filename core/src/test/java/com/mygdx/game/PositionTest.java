package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.tile.Side;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class PositionTest {
    private GameScreen screenMock;
    private Stage stageMock;
    private ArrayList<Player> players;
    private final GameScreen gameScreen;

    public PositionTest() {
        screenMock = mock(GameScreen.class);
        stageMock = mock(Stage.class);
        Gdx.files = mock(Files.class);
        Gdx.gl = mock(GL20.class);
        gameScreen = mock(GameScreen.class);
    }

    @Test
    public void testPosition() {
        Position position1 = new Position();

        position1.setX(5);
        position1.setY(5);

        String positionString  = position1.toString();
        Assert.assertEquals("(5 5)", positionString);

        Position position2 = new Position(5, 6);

        Assert.assertFalse(position1.equals(position2));
        position2.setY(5);
        Assert.assertTrue(position1.equals(position2));

        Assert.assertEquals(position1.getPositionOnSide(Side.TOP).toString(), "(5 6)");
        Assert.assertEquals(position1.getPositionOnSide(Side.RIGHT).toString(), "(6 5)");
        Assert.assertEquals(position1.getPositionOnSide(Side.BOTTOM).toString(), "(5 4)");
        Assert.assertEquals(position1.getPositionOnSide(Side.LEFT).toString(), "(4 5)");

    }
}
