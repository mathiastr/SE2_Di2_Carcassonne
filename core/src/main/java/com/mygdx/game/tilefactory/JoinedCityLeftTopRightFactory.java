package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

import java.util.Arrays;

public class JoinedCityLeftTopRightFactory extends AbstractTileFactory {

    public JoinedCityLeftTopRightFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor fullCityLeftTopRight = new TileActor();
        fullCityLeftTopRight.setTexture(graphicsBackend.loadTexture("full_city_left_top_right_128.jpg"));
        fullCityLeftTopRight.addFeature(new City(Arrays.asList(Side.LEFT, Side.TOP, Side.RIGHT)));
        return fullCityLeftTopRight;
    }
}
