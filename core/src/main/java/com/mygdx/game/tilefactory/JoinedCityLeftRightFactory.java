package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

import java.util.Arrays;

public class JoinedCityLeftRightFactory extends AbstractTileFactory {

    public JoinedCityLeftRightFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor fullCityLeftRight = new TileActor();
        fullCityLeftRight.setTexture(graphicsBackend.loadTexture("full_city_left_right_128.jpg"));
        fullCityLeftRight.addFeature(new City(Arrays.asList(Side.LEFT, Side.RIGHT)));
        return fullCityLeftRight;
    }
}
