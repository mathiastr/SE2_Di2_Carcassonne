package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

public class MonasteryWithRoadFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor monasteryWithRoad = new TileActor();
        monasteryWithRoad.setTexture(new Texture(Gdx.files.internal("monastery_with_road_128.jpg")));
        monasteryWithRoad.addFeature(new Monastery(Side.TOP));
        monasteryWithRoad.addFeature(new Road(Side.BOTTOM));
        monasteryWithRoad.setMonastery();
        return monasteryWithRoad;
    }
}
