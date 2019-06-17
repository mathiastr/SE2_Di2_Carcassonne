package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

import java.util.Arrays;

public class DiagonalCityWithRoadFactory extends AbstractTileFactory {

    public DiagonalCityWithRoadFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor diagCityWithRoad = new TileActor();
        diagCityWithRoad.setTexture(graphicsBackend.loadTexture("diagonal_city_with_road_128.jpg"));
        diagCityWithRoad.addFeature(new City(Arrays.asList(Side.TOP, Side.LEFT)));
        diagCityWithRoad.addFeature(new Road(Arrays.asList(Side.BOTTOM, Side.RIGHT)));
        return diagCityWithRoad;
    }
}
