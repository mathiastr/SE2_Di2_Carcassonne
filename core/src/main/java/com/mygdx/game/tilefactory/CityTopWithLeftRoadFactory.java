package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

import java.util.Arrays;

public class CityTopWithLeftRoadFactory extends AbstractTileFactory {

    public CityTopWithLeftRoadFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor cityTopWithLeftRoad = new TileActor();
        cityTopWithLeftRoad.setTexture(graphicsBackend.loadTexture("city_top_with_left_road_128.jpg"));
        cityTopWithLeftRoad.addFeature(new City(Side.TOP));
        cityTopWithLeftRoad.addFeature(new Road(Arrays.asList(Side.LEFT, Side.BOTTOM)));
        return cityTopWithLeftRoad;
    }
}
