package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

public class QuadRoadFactory extends AbstractTileFactory {
    public QuadRoadFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor quadRoad = new TileActor();
        quadRoad.setTexture(graphicsBackend.loadTexture("quad_road_128.jpg"));
        quadRoad.addFeature(new Road(Side.LEFT));
        quadRoad.addFeature(new Road(Side.BOTTOM));
        quadRoad.addFeature(new Road(Side.RIGHT));
        quadRoad.addFeature(new Road(Side.TOP));
        return quadRoad;
    }
}
