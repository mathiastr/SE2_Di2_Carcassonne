package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.IGraphicsBackend;

public class MonasteryFactory extends AbstractTileFactory {


    public MonasteryFactory(IGraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(IGraphicsBackend graphicsBackend) {
        TileActor monastery = new TileActor();
        monastery.setTexture(graphicsBackend.loadTexture("monastery_128.jpg"));
        monastery.addFeature(new Monastery(Side.TOP));
        monastery.setMonastery();
        return monastery;
    }
}
