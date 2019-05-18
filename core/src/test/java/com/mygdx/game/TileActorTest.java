package com.mygdx.game;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TileActorTest {

    private TileActor tile = new TileActor(new Position(0, 0));

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testRotation() {
        for (int i = 0; i < 5; ++i) tile.rotate();
        assertSame(tile.getSideAfterRotation(Side.top), Side.right);
        assertSame(tile.getSideAfterRotation(Side.right), Side.bottom);
        assertSame(tile.getSideAfterRotation(Side.bottom), Side.left);
        assertSame(tile.getSideAfterRotation(Side.left), Side.top);
        assertSame(tile.getTileSideAt(Side.top), Side.left);
        assertSame(tile.getTileSideAt(Side.right), Side.top);
        assertSame(tile.getTileSideAt(Side.bottom), Side.right);
        assertSame(tile.getTileSideAt(Side.left), Side.bottom);
    }

    @Test
    public void testAddFeature() {
        Road road = new Road(Arrays.asList(Side.top, Side.bottom));
        tile.addFeature(road);
        assertEquals(tile.getFeatureAtSide(Side.top), road);
        assertEquals(tile.getFeatureAtSide(Side.bottom), road);
        assertNull(tile.getFeatureAtSide(Side.right));
    }

    @Test(expected=Exception.class)
    public void testAddFeatureException() {
        Road road1 = new Road(Arrays.asList(Side.top, Side.bottom));
        Road road2 = new Road(Arrays.asList(Side.top, Side.right));
        tile.addFeature(road1);
        tile.addFeature(road2);
    }
}