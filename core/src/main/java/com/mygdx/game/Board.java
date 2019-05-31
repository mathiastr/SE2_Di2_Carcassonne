package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Board {
    private ArrayList<TileActor> availableTiles = new ArrayList<>();
    private HashMap<Position, TileActor> placedTiles = new HashMap<>();

    public ArrayList<TileActor> getAvailableTiles() {
        return availableTiles;

    }

    public HashMap<Position, TileActor> getPlacedTiles() {
        return placedTiles;
    }

    // TODO maybe adjust scoring methods for the end of game partial scoring...
    /* mid game scoring */
    /* ---------------- */
    public int scoreMonastery(TileActor tile) {
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                if (!placedTiles.containsKey(tile.getPosition().add(new Position(dx, dy)))) {
                    // there is no tile at this surrounding position => monastery not completed
                    return 0;
                }
            }
        }
        return tile.isMonastery() ? 9 : 0;
    }

    /* returns 0 if road/city is not completed, score of component otherwise */
    /* city score is 2 per tile, road score is 1 per tile */
    public int scoreRoadOrCity(TileActor tile, Feature feature) {
        if (!(feature instanceof Road) && !(feature instanceof City))
            throw new IllegalArgumentException("only road or city allowed!");

        if (!tile.getFeatures().contains(feature))
            throw new IllegalArgumentException("not a feature of the tile!");

        HashSet<TileActor> visited = new HashSet<>();
        int score = scoreRoadOrCityRec(tile, feature, null, visited);
        return score == -1 ? 0 : score;
    }

    public int scoreRoadOrCityRec(TileActor tile, Feature feature, TileActor parent, HashSet<TileActor> visited) {
        int score = feature instanceof City ? 2 : 1; // tile itself

        if (!visited.add(tile)) return 0; // we found a loop => road closed

        for (Side side : feature.getSides()) {
            side = tile.getSideAfterRotation(side);
            TileActor nextTile = getTileInDirectionOfSide(tile, side);
            if (nextTile == null) return -1;
            if (nextTile == parent) continue;

            Feature nextRoad = nextTile.getFeatureAtSide(getFacingSideOfSurroundingTile(side));
            int currScore = scoreRoadOrCityRec(nextTile, nextRoad, tile, visited);
            if (currScore == -1) return -1;

            score += currScore;
        }
        return score;
    }

    public int getScore(TileActor tile) {
        int score = 0;
        for (Feature feature: tile.getFeatures()) {
            if(feature instanceof City || feature instanceof Road) {
                score += scoreRoadOrCity(tile, feature);
            }
            else if (feature instanceof Monastery) {
                score += scoreMonastery(tile);
            }
            // TODO check for monastery around
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                TileActor tileAround = placedTiles.get(tile.getPosition().add(new Position(i, j)));
                if ( tileAround != null) {
                    if (tileAround.isMonastery()) {
                        score += scoreMonastery(tileAround);
                    }
                }
            }
        }
        return score;
    }

    // e.g. for top we want bottom, for right we want left ...
    public Side getFacingSideOfSurroundingTile(Side side) {
        return Side.values()[side.ordinal() ^ 2];
    }

    private boolean isValidAtPosition(TileActor tile, Position pos) {
        if (placedTiles.containsKey(pos)) return false;

        boolean connected = false;
        Position[] surroundPositions = pos.getSurroundingPositions();
        for (Side side : Side.values()) {
            if (placedTiles.containsKey(surroundPositions[side.ordinal()])) {
                connected = true;
                TileActor surroundTile = placedTiles.get(surroundPositions[side.ordinal()]);
                Feature feature1 = tile.getFeatureAtSide(side);
                Feature feature2 = surroundTile.getFeatureAtSide(getFacingSideOfSurroundingTile(side));

                /* check if features line up */
                if (feature1 == null && feature2 == null) continue;
                if (feature1 == null || feature2 == null) return false;
                if (!feature1.getClass().equals(feature2.getClass())) return false;
            }
        }
        return connected; // if the position was not connected to the current board -> return false
    }

    public HashSet<Position> getValidPositionsForTile(TileActor placedTile) {
        HashSet<Position> validPositions = new HashSet<>();
        for (TileActor tile : placedTiles.values()) {
            for (Position pos : tile.getPosition().getSurroundingPositions()) {
                if (!validPositions.contains(pos) && isValidAtPosition(placedTile, pos)) {
                    validPositions.add(pos);
                }
            }
        }
        return validPositions;
    }

    public TileActor getTileInDirectionOfSide(TileActor tile, Side side) {
        switch (side) {
            case top:
                return placedTiles.get(tile.getPosition().add(new Position(0, 1)));
            case right:
                return placedTiles.get(tile.getPosition().add(new Position(1, 0)));
            case bottom:
                return placedTiles.get(tile.getPosition().add(new Position(0, -1)));
            case left:
                return placedTiles.get(tile.getPosition().add(new Position(-1, 0)));
            default:
                return null;
        }
    }

    public int getScore(TileActor tile, Feature feature) {
        int score = 0;
        if((feature instanceof City || feature instanceof Road)) {
            score += scoreRoadOrCity(tile, feature);
        }
        else if (feature instanceof Monastery) {
            score += scoreMonastery(tile);
        }
        // TODO check for monastery around
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                TileActor tileAround = placedTiles.get(tile.getPosition().add(new Position(i, j)));
                if ( tileAround != null) {
                    if (tileAround.isMonastery()) {
                        score += scoreMonastery(tileAround);
                    }
                }
            }
        }
        return score;
    }

    public void createDeckTilesAndStartTile() {
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
            straightRoadUnderCity.addFeature(new City(Side.top));
            straightRoadUnderCity.addFeature(new Road(Arrays.asList(Side.left, Side.right)));
            availableTiles.add(straightRoadUnderCity);
        }

        /* diagonal city */
        for (int i = 0; i < diagCityCount; ++i) {
            TileActor diagCity = new TileActor();
            diagCity.setTexture(new Texture(Gdx.files.internal("diagonal_city_128.jpg")));
            diagCity.addFeature(new City(Arrays.asList(Side.top, Side.right)));
            availableTiles.add(diagCity);
        }

        /* straight road */
        for (int i = 0; i < straightRoadCount; ++i) {
            TileActor straightRoad = new TileActor();
            straightRoad.setTexture(new Texture(Gdx.files.internal("straight_road_128.jpg")));
            straightRoad.addFeature(new Road(Arrays.asList(Side.top, Side.bottom)));
            availableTiles.add(straightRoad);
        }

        /* city left right */
        for (int i = 0; i < cityLeftRightCount; ++i) {
            TileActor cityLeftRight = new TileActor();
            cityLeftRight.setTexture(new Texture(Gdx.files.internal("city_left_right_128.jpg")));
            cityLeftRight.addFeature(new City(Side.left));
            cityLeftRight.addFeature(new City(Side.right));
            availableTiles.add(cityLeftRight);
        }

        /* city top */
        for (int i = 0; i < cityTopCount; ++i) {
            TileActor cityTop = new TileActor();
            cityTop.setTexture(new Texture(Gdx.files.internal("city_top_128.jpg")));
            cityTop.addFeature(new City(Side.top));
            availableTiles.add(cityTop);
        }

        /* city top right */
        for (int i = 0; i < cityTopRightCount; ++i) {
            TileActor cityTopRight = new TileActor();
            cityTopRight.setTexture(new Texture(Gdx.files.internal("city_top_right_128.jpg")));
            cityTopRight.addFeature(new City(Side.top));
            cityTopRight.addFeature(new City(Side.right));
            availableTiles.add(cityTopRight);
        }

        /* city top with left road */
        for (int i = 0; i < cityTopWithLeftRoadCount; ++i) {
            TileActor cityTopWithLeftRoad = new TileActor();
            cityTopWithLeftRoad.setTexture(new Texture(Gdx.files.internal("city_top_with_left_road_128.jpg")));
            cityTopWithLeftRoad.addFeature(new City(Side.top));
            cityTopWithLeftRoad.addFeature(new Road(Arrays.asList(Side.left, Side.bottom)));
            availableTiles.add(cityTopWithLeftRoad);
        }

        /* city top with right road */
        for (int i = 0; i < cityTopWithRightRoadCount; ++i) {
            TileActor cityTopWithRightRoad = new TileActor();
            cityTopWithRightRoad.setTexture(new Texture(Gdx.files.internal("city_top_with_right_road_128.jpg")));
            cityTopWithRightRoad.addFeature(new City(Side.top));
            cityTopWithRightRoad.addFeature(new Road(Arrays.asList(Side.right, Side.bottom)));
            availableTiles.add(cityTopWithRightRoad);
        }

        /* city with triple road */
        for (int i = 0; i < cityWithTripleRoadCount; ++i) {
            TileActor cityWithTripleRoad = new TileActor();
            cityWithTripleRoad.setTexture(new Texture(Gdx.files.internal("city_with_triple_road_128.jpg")));
            cityWithTripleRoad.addFeature(new City(Side.top));
            cityWithTripleRoad.addFeature(new Road(Side.left));
            cityWithTripleRoad.addFeature(new Road(Side.right));
            cityWithTripleRoad.addFeature(new Road(Side.bottom));
            availableTiles.add(cityWithTripleRoad);
        }

        /* diagonal city with road */
        for (int i = 0; i < diagCityWithRoadCount; ++i) {
            TileActor diagCityWithRoad = new TileActor();
            diagCityWithRoad.setTexture(new Texture(Gdx.files.internal("diagonal_city_with_road_128.jpg")));
            diagCityWithRoad.addFeature(new City(Arrays.asList(Side.top, Side.left)));
            diagCityWithRoad.addFeature(new Road(Arrays.asList(Side.bottom, Side.right)));
            availableTiles.add(diagCityWithRoad);
        }

        /* full city */
        for (int i = 0; i < fullCityCount; ++i) {
            TileActor fullCity = new TileActor();
            fullCity.setTexture(new Texture(Gdx.files.internal("full_city_128.jpg")));
            fullCity.addFeature(new City(Arrays.asList(Side.left, Side.top, Side.right, Side.bottom)));
            availableTiles.add(fullCity);
        }

        /* full city left/right */
        for (int i = 0; i < fullCityLeftRightCount; ++i) {
            TileActor fullCityLeftRight = new TileActor();
            fullCityLeftRight.setTexture(new Texture(Gdx.files.internal("full_city_left_right_128.jpg")));
            fullCityLeftRight.addFeature(new City(Arrays.asList(Side.left, Side.right)));
            availableTiles.add(fullCityLeftRight);
        }

        /* full city left/top/right */
        for (int i = 0; i < fullCityLeftTopRightCount; ++i) {
            TileActor fullCityLeftTopRight = new TileActor();
            fullCityLeftTopRight.setTexture(new Texture(Gdx.files.internal("full_city_left_top_right_128.jpg")));
            fullCityLeftTopRight.addFeature(new City(Arrays.asList(Side.left, Side.top, Side.right)));
            availableTiles.add(fullCityLeftTopRight);
        }

        /* full city left/top/right with road */
        for (int i = 0; i < fullCityLeftTopRightWithRoadCount; ++i) {
            TileActor fullCityLeftTopRightWithRoad = new TileActor();
            fullCityLeftTopRightWithRoad.setTexture(new Texture(Gdx.files.internal("full_city_left_top_right_with_road_128.jpg")));
            fullCityLeftTopRightWithRoad.addFeature(new City(Arrays.asList(Side.left, Side.top, Side.right)));
            fullCityLeftTopRightWithRoad.addFeature(new Road(Side.bottom));
            availableTiles.add(fullCityLeftTopRightWithRoad);
        }

        /* monastery */
        for (int i = 0; i < monasteryCount; ++i) {
            TileActor monastery = new TileActor();
            monastery.setTexture(new Texture(Gdx.files.internal("monastery_128.jpg")));
            monastery.setMonastery();
            availableTiles.add(monastery);
        }

        /* monastery with road */
        for (int i = 0; i < monasteryWithRoadCount; ++i) {
            TileActor monasteryWithRoad = new TileActor();
            monasteryWithRoad.setTexture(new Texture(Gdx.files.internal("monastery_with_road_128.jpg")));
            monasteryWithRoad.setMonastery();
            monasteryWithRoad.addFeature(new Road(Side.bottom));
            availableTiles.add(monasteryWithRoad);
        }

        /* triple road */
        for (int i = 0; i < tripleRoadCount; ++i) {
            TileActor tripleRoad = new TileActor();
            tripleRoad.setTexture(new Texture(Gdx.files.internal("triple_road_128.jpg")));
            tripleRoad.addFeature(new Road(Side.left));
            tripleRoad.addFeature(new Road(Side.bottom));
            tripleRoad.addFeature(new Road(Side.right));
            availableTiles.add(tripleRoad);
        }

        /* turning road */
        for (int i = 0; i < turningRoadCount; ++i) {
            TileActor turningRoad = new TileActor();
            turningRoad.setTexture(new Texture(Gdx.files.internal("turning_road_128.jpg")));
            turningRoad.addFeature(new Road(Arrays.asList(Side.left, Side.bottom)));
            availableTiles.add(turningRoad);
        }

        /* triple road */
        for (int i = 0; i < quadRoadCount; ++i) {
            TileActor quadRoad = new TileActor();
            quadRoad.setTexture(new Texture(Gdx.files.internal("quad_road_128.jpg")));
            quadRoad.addFeature(new Road(Side.left));
            quadRoad.addFeature(new Road(Side.bottom));
            quadRoad.addFeature(new Road(Side.right));
            quadRoad.addFeature(new Road(Side.top));
            availableTiles.add(quadRoad);
        }
    }
}