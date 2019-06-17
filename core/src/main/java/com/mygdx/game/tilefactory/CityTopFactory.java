package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

public class CityTopFactory extends AbstractTileFactory {

    public CityTopFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor cityTop = new TileActor();
        cityTop.setTexture(graphicsBackend.loadTexture("city_top_128.jpg"));
        cityTop.addFeature(new City(Side.TOP));
        return cityTop;
    }
}
