package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

public class TripleRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor tripleRoad = new TileActor();
        tripleRoad.setTexture(new Texture(Gdx.files.internal("triple_road_128.jpg")));
        tripleRoad.addFeature(new Road(Side.LEFT));
        tripleRoad.addFeature(new Road(Side.BOTTOM));
        tripleRoad.addFeature(new Road(Side.RIGHT));
        return tripleRoad;
    }
}
