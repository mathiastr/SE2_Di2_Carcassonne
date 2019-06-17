package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

public class QuadRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor quadRoad = new TileActor();
        quadRoad.setTexture(new Texture(Gdx.files.internal("quad_road_128.jpg")));
        quadRoad.addFeature(new Road(Side.LEFT));
        quadRoad.addFeature(new Road(Side.BOTTOM));
        quadRoad.addFeature(new Road(Side.RIGHT));
        quadRoad.addFeature(new Road(Side.TOP));
        return quadRoad;
    }
}
