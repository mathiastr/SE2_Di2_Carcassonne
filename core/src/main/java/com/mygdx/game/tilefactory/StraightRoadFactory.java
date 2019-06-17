package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class StraightRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor straightRoad = new TileActor();
        straightRoad.setTexture(new Texture(Gdx.files.internal("straight_road_128.jpg")));
        straightRoad.addFeature(new Road(Arrays.asList(Side.TOP, Side.BOTTOM)));
        return straightRoad;
    }
}
