package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

public class CityLeftRightFactory extends AbstractTileFactory {

    public CityLeftRightFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor cityLeftRight = new TileActor();
        cityLeftRight.setTexture(graphicsBackend.loadTexture("city_left_right_128.jpg"));
        cityLeftRight.addFeature(new City(Side.LEFT));
        cityLeftRight.addFeature(new City(Side.RIGHT));
        return cityLeftRight;
    }
}
