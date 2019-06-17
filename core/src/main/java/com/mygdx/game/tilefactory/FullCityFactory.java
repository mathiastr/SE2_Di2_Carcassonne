package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class FullCityFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor fullCity = new TileActor();
        fullCity.setTexture(new Texture(Gdx.files.internal("full_city_128.jpg")));
        fullCity.addFeature(new City(Arrays.asList(Side.LEFT, Side.TOP, Side.RIGHT, Side.BOTTOM)));
        return fullCity;
    }
}
