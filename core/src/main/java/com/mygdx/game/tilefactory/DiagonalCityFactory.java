package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class DiagonalCityFactory extends AbstractTileFactory{
    @Override
    protected TileActor createTile() {
        TileActor diagCity = new TileActor();
        diagCity.setTexture(new Texture(Gdx.files.internal("diagonal_city_128.jpg")));
        diagCity.addFeature(new City(Arrays.asList(Side.TOP, Side.RIGHT)));
        return diagCity;
    }
}
