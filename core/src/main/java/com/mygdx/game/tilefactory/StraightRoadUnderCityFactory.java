package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

import java.util.Arrays;

public class StraightRoadUnderCityFactory extends AbstractTileFactory{

    public StraightRoadUnderCityFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor straightRoadUnderCity = new TileActor();
        straightRoadUnderCity.setTexture(graphicsBackend.loadTexture("road_under_city_128.jpg"));
        straightRoadUnderCity.addFeature(new City(Side.TOP));
        straightRoadUnderCity.addFeature(new Road(Arrays.asList(Side.LEFT, Side.RIGHT)));
        return straightRoadUnderCity;
    }
}
