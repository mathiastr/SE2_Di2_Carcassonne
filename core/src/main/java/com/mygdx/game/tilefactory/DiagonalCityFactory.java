package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

import java.util.Arrays;

public class DiagonalCityFactory extends AbstractTileFactory{
    public DiagonalCityFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor diagCity = new TileActor();
        diagCity.setTexture(graphicsBackend.loadTexture("diagonal_city_128.jpg"));
        diagCity.addFeature(new City(Arrays.asList(Side.TOP, Side.RIGHT)));
        return diagCity;
    }
}
