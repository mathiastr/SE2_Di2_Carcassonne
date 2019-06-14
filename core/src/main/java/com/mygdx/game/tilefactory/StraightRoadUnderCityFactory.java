package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class StraightRoadUnderCityFactory extends AbstractTileFactory{
    @Override
    protected TileActor createTile() {
        TileActor straightRoadUnderCity = new TileActor();
        straightRoadUnderCity.setTexture(new Texture(Gdx.files.internal("road_under_city_128.jpg")));
        straightRoadUnderCity.addFeature(new City(Side.TOP));
        straightRoadUnderCity.addFeature(new Road(Arrays.asList(Side.LEFT, Side.RIGHT)));
        return straightRoadUnderCity;
    }
}
