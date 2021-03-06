package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

import java.util.Arrays;

public class FullCityFactory extends AbstractTileFactory {

    public FullCityFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor fullCity = new TileActor();
        fullCity.setTexture(graphicsBackend.loadTexture("full_city_128.jpg"));
        fullCity.addFeature(new City(Arrays.asList(Side.LEFT, Side.TOP, Side.RIGHT, Side.BOTTOM)));
        return fullCity;
    }
}
