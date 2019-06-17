package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

public class CityTopRightFactory extends AbstractTileFactory {
    public CityTopRightFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor cityTopRight = new TileActor();
        cityTopRight.setTexture(graphicsBackend.loadTexture("city_top_right_128.jpg"));
        cityTopRight.addFeature(new City(Side.TOP));
        cityTopRight.addFeature(new City(Side.RIGHT));
        return cityTopRight;
    }
}
