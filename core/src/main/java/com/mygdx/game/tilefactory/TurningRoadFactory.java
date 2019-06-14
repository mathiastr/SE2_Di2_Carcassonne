package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import java.util.Arrays;

public class TurningRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor turningRoad = new TileActor();
        turningRoad.setTexture(new Texture(Gdx.files.internal("turning_road_128.jpg")));
        turningRoad.addFeature(new Road(Arrays.asList(Side.LEFT, Side.BOTTOM)));
        return turningRoad;
    }
}
