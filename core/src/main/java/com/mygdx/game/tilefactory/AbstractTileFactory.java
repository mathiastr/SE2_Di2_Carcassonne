package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTileFactory {
    public final List<TileActor> createTiles(int count) {
        List<TileActor> created = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            created.add(createTile());
        }
        return created;
    }

    protected abstract TileActor createTile();
}
