package com.mygdx.game;

import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tilefactory.CityLeftRightFactory;
import com.mygdx.game.tilefactory.CityTopFactory;
import com.mygdx.game.tilefactory.CityTopRightFactory;
import com.mygdx.game.tilefactory.CityTopWithLeftRoadFactory;
import com.mygdx.game.tilefactory.CityTopWithRightRoadFactory;
import com.mygdx.game.tilefactory.CityWithTripleRoadFactory;
import com.mygdx.game.tilefactory.DiagonalCityFactory;
import com.mygdx.game.tilefactory.DiagonalCityWithRoadFactory;
import com.mygdx.game.tilefactory.FullCityFactory;
import com.mygdx.game.tilefactory.JoinedCityLeftRightFactory;
import com.mygdx.game.tilefactory.JoinedCityLeftTopRightFactory;
import com.mygdx.game.tilefactory.JoinedCityLeftTopRightWithRoadFactory;
import com.mygdx.game.tilefactory.MonasteryFactory;
import com.mygdx.game.tilefactory.MonasteryWithRoadFactory;
import com.mygdx.game.tilefactory.StraightRoadFactory;
import com.mygdx.game.tilefactory.StraightRoadUnderCityFactory;
import com.mygdx.game.tilefactory.TripleRoadFactory;
import com.mygdx.game.tilefactory.TurningRoadFactory;
import com.mygdx.game.utility.IGraphicsBackend;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    public IGraphicsBackend graphicsBackend;
    public Deck(IGraphicsBackend graphicsBackend) {
        this.graphicsBackend = graphicsBackend;
    }

    public List<TileActor> createDeckTiles() {
        List<TileActor> availableTiles = new ArrayList<>();
        availableTiles.addAll(new CityWithTripleRoadFactory(graphicsBackend).createTiles(3));
        availableTiles.addAll(new StraightRoadUnderCityFactory(graphicsBackend).createTiles(4));
        availableTiles.addAll(new DiagonalCityFactory(graphicsBackend).createTiles(5));
        availableTiles.addAll(new StraightRoadFactory(graphicsBackend).createTiles(8));
        availableTiles.addAll(new CityLeftRightFactory(graphicsBackend).createTiles(3));
        availableTiles.addAll(new CityTopFactory(graphicsBackend).createTiles(5));
        availableTiles.addAll(new CityTopRightFactory(graphicsBackend).createTiles(2));
        availableTiles.addAll(new CityTopWithLeftRoadFactory(graphicsBackend).createTiles(3));
        availableTiles.addAll(new CityTopWithRightRoadFactory(graphicsBackend).createTiles(3));
        availableTiles.addAll(new DiagonalCityWithRoadFactory(graphicsBackend).createTiles(5));
        availableTiles.addAll(new FullCityFactory(graphicsBackend).createTiles(1));
        availableTiles.addAll(new JoinedCityLeftRightFactory(graphicsBackend).createTiles(3));
        availableTiles.addAll(new JoinedCityLeftTopRightFactory(graphicsBackend).createTiles(4));
        availableTiles.addAll(new JoinedCityLeftTopRightWithRoadFactory(graphicsBackend).createTiles(3));
        availableTiles.addAll(new MonasteryFactory(graphicsBackend).createTiles(4));
        availableTiles.addAll(new MonasteryWithRoadFactory(graphicsBackend).createTiles(2));
        availableTiles.addAll(new TripleRoadFactory(graphicsBackend).createTiles(4));
        availableTiles.addAll(new TurningRoadFactory(graphicsBackend).createTiles(9));
        availableTiles.addAll(new TurningRoadFactory(graphicsBackend).createTiles(1));
        return availableTiles;
    }
}
