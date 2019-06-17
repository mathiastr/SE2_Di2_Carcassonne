package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class CityTopWithLeftRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor cityTopWithLeftRoad = new TileActor();
        cityTopWithLeftRoad.setTexture(new Texture(Gdx.files.internal("city_top_with_left_road_128.jpg")));
        cityTopWithLeftRoad.addFeature(new City(Side.TOP));
        cityTopWithLeftRoad.addFeature(new Road(Arrays.asList(Side.LEFT, Side.BOTTOM)));
        return cityTopWithLeftRoad;
    }
}
