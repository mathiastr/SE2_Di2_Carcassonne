package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

import java.util.Arrays;

public class TurningRoadFactory extends AbstractTileFactory {

    public TurningRoadFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor turningRoad = new TileActor();
        turningRoad.setTexture(graphicsBackend.loadTexture("turning_road_128.jpg"));
        turningRoad.addFeature(new Road(Arrays.asList(Side.LEFT, Side.BOTTOM)));
        return turningRoad;
    }
}
