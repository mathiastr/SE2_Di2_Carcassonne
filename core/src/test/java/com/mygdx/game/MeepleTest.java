package com.mygdx.game;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import static org.mockito.Mockito.mock;

public class MeepleTest {

    private Position posMock;

    public MeepleTest() {
        posMock = mock(Position.class);
    }

    @Test
    public void hasMeepleOnItTrueTest() {

        TileActor t = new TileActor(posMock);
        City city = new City(Arrays.asList(Side.TOP));
        t.addFeature(city);
        t.getFeatures().get(0).setHasMeepleOnIt(true);
        Assert.assertTrue(t.getFeatures().get(0).hasMeepleOnIt());

    }

    @Test
    public void hasMeepleOnItFalseTest() {

        TileActor t = new TileActor(posMock);
        City city = new City(Arrays.asList(Side.BOTTOM));
        t.addFeature(city);
        t.getFeatures().get(0).setHasMeepleOnIt(false);
        Assert.assertFalse(t.getFeatures().get(0).hasMeepleOnIt());

    }
}
