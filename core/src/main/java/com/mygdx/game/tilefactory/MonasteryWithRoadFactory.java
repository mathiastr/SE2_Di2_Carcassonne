package com.mygdx.game.tilefactory;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.utility.GraphicsBackend;

public class MonasteryWithRoadFactory extends AbstractTileFactory {

    public MonasteryWithRoadFactory(GraphicsBackend graphicsBackend) {
        super(graphicsBackend);
    }

    @Override
    public TileActor createTile(GraphicsBackend graphicsBackend) {
        TileActor monasteryWithRoad = new TileActor();
        monasteryWithRoad.setTexture(graphicsBackend.loadTexture("monastery_with_road_128.jpg"));
        monasteryWithRoad.addFeature(new Monastery(Side.TOP));
        monasteryWithRoad.addFeature(new Road(Side.BOTTOM));
        monasteryWithRoad.setMonastery();
        return monasteryWithRoad;
    }
}
