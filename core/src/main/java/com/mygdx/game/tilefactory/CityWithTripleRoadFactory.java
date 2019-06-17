package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;
import com.mygdx.game.utility.IGraphicsBackend;

public class CityWithTripleRoadFactory extends AbstractTileFactory {

    public CityWithTripleRoadFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor cityWithTripleRoad = new TileActor();
        cityWithTripleRoad.setTexture(graphicsBackend.loadTexture("city_with_triple_road_128.jpg"));
        cityWithTripleRoad.addFeature(new City(Side.TOP));
        cityWithTripleRoad.addFeature(new Road(Side.LEFT));
        cityWithTripleRoad.addFeature(new Road(Side.RIGHT));
        cityWithTripleRoad.addFeature(new Road(Side.BOTTOM));
        return cityWithTripleRoad;
    }
}
