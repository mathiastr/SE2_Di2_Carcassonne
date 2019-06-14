package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;

public class CityLeftRightFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor cityLeftRight = new TileActor();
        cityLeftRight.setTexture(new Texture(Gdx.files.internal("city_left_right_128.jpg")));
        cityLeftRight.addFeature(new City(Side.LEFT));
        cityLeftRight.addFeature(new City(Side.RIGHT));
        return cityLeftRight;
    }
}
