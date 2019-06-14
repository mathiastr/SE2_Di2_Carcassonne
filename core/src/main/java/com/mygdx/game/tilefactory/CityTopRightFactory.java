package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;

public class CityTopRightFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor cityTopRight = new TileActor();
        cityTopRight.setTexture(new Texture(Gdx.files.internal("city_top_right_128.jpg")));
        cityTopRight.addFeature(new City(Side.TOP));
        cityTopRight.addFeature(new City(Side.RIGHT));
        return cityTopRight;
    }
}
