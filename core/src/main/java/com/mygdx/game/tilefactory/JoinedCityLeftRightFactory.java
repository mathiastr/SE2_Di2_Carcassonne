package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class JoinedCityLeftRightFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor fullCityLeftRight = new TileActor();
        fullCityLeftRight.setTexture(new Texture(Gdx.files.internal("full_city_left_right_128.jpg")));
        fullCityLeftRight.addFeature(new City(Arrays.asList(Side.LEFT, Side.RIGHT)));
        return fullCityLeftRight;
    }
}
