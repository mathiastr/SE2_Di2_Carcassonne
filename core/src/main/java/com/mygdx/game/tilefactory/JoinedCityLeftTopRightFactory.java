package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class JoinedCityLeftTopRightFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor fullCityLeftTopRight = new TileActor();
        fullCityLeftTopRight.setTexture(new Texture(Gdx.files.internal("full_city_left_top_right_128.jpg")));
        fullCityLeftTopRight.addFeature(new City(Arrays.asList(Side.LEFT, Side.TOP, Side.RIGHT)));
        return fullCityLeftTopRight;
    }
}
