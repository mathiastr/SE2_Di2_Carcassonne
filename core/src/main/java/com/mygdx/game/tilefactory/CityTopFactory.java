package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

public class CityTopFactory extends AbstractTileFactory {

    public CityTopFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor cityTop = new TileActor();
        cityTop.setTexture(graphicsBackend.loadTexture("city_top_128.jpg"));
        cityTop.addFeature(new City(Side.TOP));
        return cityTop;
    }
}
