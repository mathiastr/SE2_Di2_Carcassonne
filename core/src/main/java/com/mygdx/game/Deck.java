package com.mygdx.game;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tilefactory.*;
import java.util.ArrayList;
import java.util.List;

public class Deck {
    public static List<TileActor> createDeckTiles() {
        List<TileActor> availableTiles = new ArrayList<>();
        availableTiles.addAll(new CityWithTripleRoadFactory().createTiles(3));
        availableTiles.addAll(new StraightRoadUnderCityFactory().createTiles(4));
        availableTiles.addAll(new DiagonalCityFactory().createTiles(5));
        availableTiles.addAll(new StraightRoadFactory().createTiles(8));
        availableTiles.addAll(new CityLeftRightFactory().createTiles(3));
        availableTiles.addAll(new CityTopFactory().createTiles(5));
        availableTiles.addAll(new CityTopRightFactory().createTiles(2));
        availableTiles.addAll(new CityTopWithLeftRoadFactory().createTiles(3));
        availableTiles.addAll(new CityTopWithRightRoadFactory().createTiles(3));
        availableTiles.addAll(new DiagonalCityWithRoadFactory().createTiles(5));
        availableTiles.addAll(new FullCityFactory().createTiles(1));
        availableTiles.addAll(new JoinedCityLeftRightFactory().createTiles(3));
        availableTiles.addAll(new JoinedCityLeftTopRightFactory().createTiles(4));
        availableTiles.addAll(new JoinedCityLeftTopRightWithRoadFactory().createTiles(3));
        availableTiles.addAll(new MonasteryFactory().createTiles(4));
        availableTiles.addAll(new MonasteryWithRoadFactory().createTiles(2));
        availableTiles.addAll(new TripleRoadFactory().createTiles(4));
        availableTiles.addAll(new TurningRoadFactory().createTiles(9));
        availableTiles.addAll(new TurningRoadFactory().createTiles(1));
        return availableTiles;
    }
}
