package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

import java.util.Arrays;

public class CityTopWithRightRoadFactory extends AbstractTileFactory {

    public CityTopWithRightRoadFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor cityTopWithRightRoad = new TileActor();
        cityTopWithRightRoad.setTexture(graphicsBackend.loadTexture("city_top_with_right_road_128.jpg"));
        cityTopWithRightRoad.addFeature(new City(Side.TOP));
        cityTopWithRightRoad.addFeature(new Road(Arrays.asList(Side.RIGHT, Side.BOTTOM)));
        return cityTopWithRightRoad;
    }
}
