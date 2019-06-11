package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Deck {
    public static List<TileActor> createDeckTilesAndStartTile() {
        List<TileActor> availableTiles = new ArrayList<>();
        int straightRoadUnderCityCount = 4; // 3 + (1 start-tile)
        int diagCityCount = 5;
        int straightRoadCount = 8;
        int cityLeftRightCount = 3;
        int cityTopCount = 5;
        int cityTopRightCount = 2;
        int cityTopWithLeftRoadCount = 3;
        int cityTopWithRightRoadCount = 3;
        int cityWithTripleRoadCount = 3;
        int diagCityWithRoadCount = 5;
        int fullCityCount = 1;
        int fullCityLeftRightCount = 3;
        int fullCityLeftTopRightCount = 4;
        int fullCityLeftTopRightWithRoadCount = 3;
        int monasteryCount = 4;
        int monasteryWithRoadCount = 2;
        int tripleRoadCount = 4;
        int turningRoadCount = 9;
        int quadRoadCount = 1;

        /* road under city */
        for (int i = 0; i < straightRoadUnderCityCount; ++i) {
            TileActor straightRoadUnderCity = new TileActor();
            straightRoadUnderCity.setTexture(new Texture(Gdx.files.internal("road_under_city_128.jpg")));
            straightRoadUnderCity.addFeature(new City(Side.TOP));
            straightRoadUnderCity.addFeature(new Road(Arrays.asList(Side.LEFT, Side.RIGHT)));
            availableTiles.add(straightRoadUnderCity);
        }

        /* diagonal city */
        for (int i = 0; i < diagCityCount; ++i) {
            TileActor diagCity = new TileActor();
            diagCity.setTexture(new Texture(Gdx.files.internal("diagonal_city_128.jpg")));
            diagCity.addFeature(new City(Arrays.asList(Side.TOP, Side.RIGHT)));
            availableTiles.add(diagCity);
        }

        /* straight road */
        for (int i = 0; i < straightRoadCount; ++i) {
            TileActor straightRoad = new TileActor();
            straightRoad.setTexture(new Texture(Gdx.files.internal("straight_road_128.jpg")));
            straightRoad.addFeature(new Road(Arrays.asList(Side.TOP, Side.BOTTOM)));
            availableTiles.add(straightRoad);
        }

        /* city LEFT RIGHT */
        for (int i = 0; i < cityLeftRightCount; ++i) {
            TileActor cityLeftRight = new TileActor();
            cityLeftRight.setTexture(new Texture(Gdx.files.internal("city_left_right_128.jpg")));
            cityLeftRight.addFeature(new City(Side.LEFT));
            cityLeftRight.addFeature(new City(Side.RIGHT));
            availableTiles.add(cityLeftRight);
        }

        /* city TOP */
        for (int i = 0; i < cityTopCount; ++i) {
            TileActor cityTop = new TileActor();
            cityTop.setTexture(new Texture(Gdx.files.internal("city_top_128.jpg")));
            cityTop.addFeature(new City(Side.TOP));
            availableTiles.add(cityTop);
        }

        /* city TOP RIGHT */
        for (int i = 0; i < cityTopRightCount; ++i) {
            TileActor cityTopRight = new TileActor();
            cityTopRight.setTexture(new Texture(Gdx.files.internal("city_top_right_128.jpg")));
            cityTopRight.addFeature(new City(Side.TOP));
            cityTopRight.addFeature(new City(Side.RIGHT));
            availableTiles.add(cityTopRight);
        }

        /* city TOP with LEFT road */
        for (int i = 0; i < cityTopWithLeftRoadCount; ++i) {
            TileActor cityTopWithLeftRoad = new TileActor();
            cityTopWithLeftRoad.setTexture(new Texture(Gdx.files.internal("city_top_with_left_road_128.jpg")));
            cityTopWithLeftRoad.addFeature(new City(Side.TOP));
            cityTopWithLeftRoad.addFeature(new Road(Arrays.asList(Side.LEFT, Side.BOTTOM)));
            availableTiles.add(cityTopWithLeftRoad);
        }

        /* city TOP with RIGHT road */
        for (int i = 0; i < cityTopWithRightRoadCount; ++i) {
            TileActor cityTopWithRightRoad = new TileActor();
            cityTopWithRightRoad.setTexture(new Texture(Gdx.files.internal("city_top_with_right_road_128.jpg")));
            cityTopWithRightRoad.addFeature(new City(Side.TOP));
            cityTopWithRightRoad.addFeature(new Road(Arrays.asList(Side.RIGHT, Side.BOTTOM)));
            availableTiles.add(cityTopWithRightRoad);
        }

        /* city with triple road */
        for (int i = 0; i < cityWithTripleRoadCount; ++i) {
            TileActor cityWithTripleRoad = new TileActor();
            cityWithTripleRoad.setTexture(new Texture(Gdx.files.internal("city_with_triple_road_128.jpg")));
            cityWithTripleRoad.addFeature(new City(Side.TOP));
            cityWithTripleRoad.addFeature(new Road(Side.LEFT));
            cityWithTripleRoad.addFeature(new Road(Side.RIGHT));
            cityWithTripleRoad.addFeature(new Road(Side.BOTTOM));
            availableTiles.add(cityWithTripleRoad);
        }

        /* diagonal city with road */
        for (int i = 0; i < diagCityWithRoadCount; ++i) {
            TileActor diagCityWithRoad = new TileActor();
            diagCityWithRoad.setTexture(new Texture(Gdx.files.internal("diagonal_city_with_road_128.jpg")));
            diagCityWithRoad.addFeature(new City(Arrays.asList(Side.TOP, Side.LEFT)));
            diagCityWithRoad.addFeature(new Road(Arrays.asList(Side.BOTTOM, Side.RIGHT)));
            availableTiles.add(diagCityWithRoad);
        }

        /* full city */
        for (int i = 0; i < fullCityCount; ++i) {
            TileActor fullCity = new TileActor();
            fullCity.setTexture(new Texture(Gdx.files.internal("full_city_128.jpg")));
            fullCity.addFeature(new City(Arrays.asList(Side.LEFT, Side.TOP, Side.RIGHT, Side.BOTTOM)));
            availableTiles.add(fullCity);
        }

        /* full city LEFT/RIGHT */
        for (int i = 0; i < fullCityLeftRightCount; ++i) {
            TileActor fullCityLeftRight = new TileActor();
            fullCityLeftRight.setTexture(new Texture(Gdx.files.internal("full_city_left_right_128.jpg")));
            fullCityLeftRight.addFeature(new City(Arrays.asList(Side.LEFT, Side.RIGHT)));
            availableTiles.add(fullCityLeftRight);
        }

        /* full city LEFT/TOP/RIGHT */
        for (int i = 0; i < fullCityLeftTopRightCount; ++i) {
            TileActor fullCityLeftTopRight = new TileActor();
            fullCityLeftTopRight.setTexture(new Texture(Gdx.files.internal("full_city_left_top_right_128.jpg")));
            fullCityLeftTopRight.addFeature(new City(Arrays.asList(Side.LEFT, Side.TOP, Side.RIGHT)));
            availableTiles.add(fullCityLeftTopRight);
        }

        /* full city LEFT/TOP/RIGHT with road */
        for (int i = 0; i < fullCityLeftTopRightWithRoadCount; ++i) {
            TileActor fullCityLeftTopRightWithRoad = new TileActor();
            fullCityLeftTopRightWithRoad.setTexture(new Texture(Gdx.files.internal("full_city_left_top_right_with_road_128.jpg")));
            fullCityLeftTopRightWithRoad.addFeature(new City(Arrays.asList(Side.LEFT, Side.TOP, Side.RIGHT)));
            fullCityLeftTopRightWithRoad.addFeature(new Road(Side.BOTTOM));
            availableTiles.add(fullCityLeftTopRightWithRoad);
        }

        /* monastery */
        for (int i = 0; i < monasteryCount; ++i) {
            TileActor monastery = new TileActor();
            monastery.setTexture(new Texture(Gdx.files.internal("monastery_128.jpg")));
            monastery.addFeature(new Monastery(Side.TOP));
            availableTiles.add(monastery);
        }

        /* monastery with road */
        for (int i = 0; i < monasteryWithRoadCount; ++i) {
            TileActor monasteryWithRoad = new TileActor();
            monasteryWithRoad.setTexture(new Texture(Gdx.files.internal("monastery_with_road_128.jpg")));
            monasteryWithRoad.addFeature(new Monastery(Side.TOP));
            monasteryWithRoad.addFeature(new Road(Side.BOTTOM));
            availableTiles.add(monasteryWithRoad);
        }

        /* triple road */
        for (int i = 0; i < tripleRoadCount; ++i) {
            TileActor tripleRoad = new TileActor();
            tripleRoad.setTexture(new Texture(Gdx.files.internal("triple_road_128.jpg")));
            tripleRoad.addFeature(new Road(Side.LEFT));
            tripleRoad.addFeature(new Road(Side.BOTTOM));
            tripleRoad.addFeature(new Road(Side.RIGHT));
            availableTiles.add(tripleRoad);
        }

        /* turning road */
        for (int i = 0; i < turningRoadCount; ++i) {
            TileActor turningRoad = new TileActor();
            turningRoad.setTexture(new Texture(Gdx.files.internal("turning_road_128.jpg")));
            turningRoad.addFeature(new Road(Arrays.asList(Side.LEFT, Side.BOTTOM)));
            availableTiles.add(turningRoad);
        }

        /* triple road */
        for (int i = 0; i < quadRoadCount; ++i) {
            TileActor quadRoad = new TileActor();
            quadRoad.setTexture(new Texture(Gdx.files.internal("quad_road_128.jpg")));
            quadRoad.addFeature(new Road(Side.LEFT));
            quadRoad.addFeature(new Road(Side.BOTTOM));
            quadRoad.addFeature(new Road(Side.RIGHT));
            quadRoad.addFeature(new Road(Side.TOP));
            availableTiles.add(quadRoad);
        }
        return availableTiles;
    }
}
