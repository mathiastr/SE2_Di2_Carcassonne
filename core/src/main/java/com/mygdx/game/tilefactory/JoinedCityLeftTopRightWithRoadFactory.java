package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

import java.util.Arrays;

public class JoinedCityLeftTopRightWithRoadFactory extends AbstractTileFactory {

    public JoinedCityLeftTopRightWithRoadFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor fullCityLeftTopRightWithRoad = new TileActor();
        fullCityLeftTopRightWithRoad.setTexture(graphicsBackend.loadTexture("full_city_left_top_right_with_road_128.jpg"));
        fullCityLeftTopRightWithRoad.addFeature(new City(Arrays.asList(Side.LEFT, Side.TOP, Side.RIGHT)));
        fullCityLeftTopRightWithRoad.addFeature(new Road(Side.BOTTOM));
        return fullCityLeftTopRightWithRoad;
    }
}
