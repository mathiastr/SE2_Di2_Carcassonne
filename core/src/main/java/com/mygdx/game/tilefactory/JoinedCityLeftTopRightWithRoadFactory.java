package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class JoinedCityLeftTopRightWithRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor fullCityLeftTopRightWithRoad = new TileActor();
        fullCityLeftTopRightWithRoad.setTexture(new Texture(Gdx.files.internal("full_city_left_top_right_with_road_128.jpg")));
        fullCityLeftTopRightWithRoad.addFeature(new City(Arrays.asList(Side.LEFT, Side.TOP, Side.RIGHT)));
        fullCityLeftTopRightWithRoad.addFeature(new Road(Side.BOTTOM));
        return fullCityLeftTopRightWithRoad;
    }
}
