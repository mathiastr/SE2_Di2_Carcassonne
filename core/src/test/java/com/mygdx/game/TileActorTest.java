package com.mygdx.game;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;
public class TileActorTest {

    TileActor tile = new TileActor(new Position(0, 0));

    @Test
    public void testRotation() {
        for (int i = 0; i < 5; ++i) tile.rotate();
        assertSame(tile.getSideAfterRotation(Side.TOP), Side.RIGHT);
        assertSame(tile.getSideAfterRotation(Side.RIGHT), Side.BOTTOM);
        assertSame(tile.getSideAfterRotation(Side.BOTTOM), Side.LEFT);
        assertSame(tile.getSideAfterRotation(Side.LEFT), Side.TOP);
        assertSame(tile.getTileSideAt(Side.TOP), Side.LEFT);
        assertSame(tile.getTileSideAt(Side.RIGHT), Side.TOP);
        assertSame(tile.getTileSideAt(Side.BOTTOM), Side.RIGHT);
        assertSame(tile.getTileSideAt(Side.LEFT), Side.BOTTOM);
    }

    @Test
    public void testAddFeature() {
        Road road = new Road(Arrays.asList(Side.TOP, Side.BOTTOM));
        tile.addFeature(road);
        assertEquals(tile.getFeatureAtSide(Side.TOP), road);
        assertEquals(tile.getFeatureAtSide(Side.BOTTOM), road);
        assertNull(tile.getFeatureAtSide(Side.RIGHT));
    }

    @Test(expected=Exception.class)
    public void testAddFeatureException() {
        Road road1 = new Road(Arrays.asList(Side.TOP, Side.BOTTOM));
        Road road2 = new Road(Arrays.asList(Side.TOP, Side.RIGHT));
        tile.addFeature(road1);
        tile.addFeature(road2);
    }
}