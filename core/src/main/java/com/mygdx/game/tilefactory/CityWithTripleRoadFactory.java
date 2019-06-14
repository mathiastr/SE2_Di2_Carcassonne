package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

public class CityWithTripleRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor cityWithTripleRoad = new TileActor();
        cityWithTripleRoad.setTexture(new Texture(Gdx.files.internal("city_with_triple_road_128.jpg")));
        cityWithTripleRoad.addFeature(new City(Side.TOP));
        cityWithTripleRoad.addFeature(new Road(Side.LEFT));
        cityWithTripleRoad.addFeature(new Road(Side.RIGHT));
        cityWithTripleRoad.addFeature(new Road(Side.BOTTOM));
        return cityWithTripleRoad;
    }
}
