package com.mygdx.game.tilefactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;

public class MonasteryFactory extends AbstractTileFactory {
    @Override
    protected TileActor createTile() {
        TileActor monastery = new TileActor();
        monastery.setTexture(new Texture(Gdx.files.internal("monastery_128.jpg")));
        //monastery.addFeature(new Monastery(Side.TOP));
        monastery.setMonastery();
        return monastery;
    }
}
