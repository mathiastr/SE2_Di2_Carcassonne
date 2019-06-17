package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

public class CityLeftRightFactory extends AbstractTileFactory {

    public CityLeftRightFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor cityLeftRight = new TileActor();
        cityLeftRight.setTexture(graphicsBackend.loadTexture("city_left_right_128.jpg"));
        cityLeftRight.addFeature(new City(Side.LEFT));
        cityLeftRight.addFeature(new City(Side.RIGHT));
        return cityLeftRight;
    }
}
