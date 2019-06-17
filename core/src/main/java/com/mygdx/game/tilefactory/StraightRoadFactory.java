package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

import java.util.Arrays;

public class StraightRoadFactory extends AbstractTileFactory {

    public StraightRoadFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor straightRoad = new TileActor();
        straightRoad.setTexture(graphicsBackend.loadTexture("straight_road_128.jpg"));
        straightRoad.addFeature(new Road(Arrays.asList(Side.TOP, Side.BOTTOM)));
        return straightRoad;
    }
}
