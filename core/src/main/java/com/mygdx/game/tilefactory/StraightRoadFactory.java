package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

import java.util.Arrays;

public class StraightRoadFactory extends AbstractTileFactory {

    public StraightRoadFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor straightRoad = new TileActor();
        straightRoad.setTexture(graphicsBackend.loadTexture("straight_road_128.jpg"));
        straightRoad.addFeature(new Road(Arrays.asList(Side.TOP, Side.BOTTOM)));
        return straightRoad;
    }
}
