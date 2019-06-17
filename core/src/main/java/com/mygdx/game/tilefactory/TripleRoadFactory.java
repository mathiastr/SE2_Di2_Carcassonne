package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

public class TripleRoadFactory extends AbstractTileFactory {

    public TripleRoadFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor tripleRoad = new TileActor();
        tripleRoad.setTexture(graphicsBackend.loadTexture("triple_road_128.jpg"));
        tripleRoad.addFeature(new Road(Side.LEFT));
        tripleRoad.addFeature(new Road(Side.BOTTOM));
        tripleRoad.addFeature(new Road(Side.RIGHT));
        return tripleRoad;
    }
}
