package com.mygdx.game;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import static org.junit.Assert.*;
public class TileActorTest {

    TileActor tile = new TileActor(new Position(0, 0));

    @Test
    public void testRotation() {
        for (int i = 0; i < 5; ++i) tile.rotate();
        assertSame(Side.RIGHT, tile.getSideAfterRotation(Side.TOP));
        assertSame(Side.BOTTOM, tile.getSideAfterRotation(Side.RIGHT));
        assertSame(Side.LEFT, tile.getSideAfterRotation(Side.BOTTOM));
        assertSame(Side.TOP, tile.getSideAfterRotation(Side.LEFT));
        assertSame(Side.LEFT, tile.getTileSideAt(Side.TOP));
        assertSame(Side.TOP, tile.getTileSideAt(Side.RIGHT));
        assertSame(Side.RIGHT, tile.getTileSideAt(Side.BOTTOM));
        assertSame(Side.BOTTOM, tile.getTileSideAt(Side.LEFT));
    }

    @Test
    public void testAddFeature() {
        Road road = new Road(Arrays.asList(Side.TOP, Side.BOTTOM));
        tile.addFeature(road);
        assertEquals(tile.getFeatureAtSide(Side.TOP), road);
        assertEquals(tile.getFeatureAtSide(Side.BOTTOM), road);
        assertNull(tile.getFeatureAtSide(Side.RIGHT));
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testAddFeatureException() {
        Road road1 = new Road(Arrays.asList(Side.TOP, Side.BOTTOM));
        Road road2 = new Road(Arrays.asList(Side.TOP, Side.RIGHT));
        tile.addFeature(road1);

        exception.expect(IllegalArgumentException.class);
        tile.addFeature(road2);
    }
}