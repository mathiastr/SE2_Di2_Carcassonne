package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

import java.util.Arrays;

public class JoinedCityLeftRightFactory extends AbstractTileFactory {

    public JoinedCityLeftRightFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor fullCityLeftRight = new TileActor();
        fullCityLeftRight.setTexture(graphicsBackend.loadTexture("full_city_left_right_128.jpg"));
        fullCityLeftRight.addFeature(new City(Arrays.asList(Side.LEFT, Side.RIGHT)));
        return fullCityLeftRight;
    }
}
