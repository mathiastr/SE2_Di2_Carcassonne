package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Side;

public class CityTopFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor cityTop = new TileActor();
        cityTop.setTexture(new Texture(Gdx.files.internal("city_top_128.jpg")));
        cityTop.addFeature(new City(Side.TOP));
        return cityTop;
    }
}
