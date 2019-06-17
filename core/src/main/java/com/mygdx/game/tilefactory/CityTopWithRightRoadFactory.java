package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class CityTopWithRightRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor cityTopWithRightRoad = new TileActor();
        cityTopWithRightRoad.setTexture(new Texture(Gdx.files.internal("city_top_with_right_road_128.jpg")));
        cityTopWithRightRoad.addFeature(new City(Side.TOP));
        cityTopWithRightRoad.addFeature(new Road(Arrays.asList(Side.RIGHT, Side.BOTTOM)));
        return cityTopWithRightRoad;
    }
}
