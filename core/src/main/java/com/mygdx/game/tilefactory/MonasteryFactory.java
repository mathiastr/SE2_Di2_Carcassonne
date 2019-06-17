package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

public class MonasteryFactory extends AbstractTileFactory {


    public MonasteryFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor monastery = new TileActor();
        monastery.setTexture(graphicsBackend.loadTexture("monastery_128.jpg"));
        monastery.addFeature(new Monastery(Side.TOP));
        monastery.setMonastery();
        return monastery;
    }
}
