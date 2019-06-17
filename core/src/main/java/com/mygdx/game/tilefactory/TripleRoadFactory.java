package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

public class TripleRoadFactory extends AbstractTileFactory {

    public TripleRoadFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor tripleRoad = new TileActor();
        tripleRoad.setTexture(graphicsBackend.loadTexture("triple_road_128.jpg"));
        tripleRoad.addFeature(new Road(Side.LEFT));
        tripleRoad.addFeature(new Road(Side.BOTTOM));
        tripleRoad.addFeature(new Road(Side.RIGHT));
        return tripleRoad;
    }
}
