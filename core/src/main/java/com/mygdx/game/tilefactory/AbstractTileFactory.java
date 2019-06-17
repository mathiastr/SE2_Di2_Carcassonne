package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.utility.IGraphicsBackend;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTileFactory {

    public IGraphicsBackend graphicsBackend;

    public AbstractTileFactory(IGraphicsBackend graphicsBackend) {
        this.graphicsBackend = graphicsBackend;
    }

    public final List<TileActor> createTiles(int count) {
        List<TileActor> created = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            created.add(createTile(graphicsBackend));
        }
        return created;
    }

    public abstract TileActor createTile(IGraphicsBackend graphicsBackend);
}
