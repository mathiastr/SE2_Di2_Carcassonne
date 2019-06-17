package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class DiagonalCityWithRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor diagCityWithRoad = new TileActor();
        diagCityWithRoad.setTexture(new Texture(Gdx.files.internal("diagonal_city_with_road_128.jpg")));
        diagCityWithRoad.addFeature(new City(Arrays.asList(Side.TOP, Side.LEFT)));
        diagCityWithRoad.addFeature(new Road(Arrays.asList(Side.BOTTOM, Side.RIGHT)));
        return diagCityWithRoad;
    }
}
