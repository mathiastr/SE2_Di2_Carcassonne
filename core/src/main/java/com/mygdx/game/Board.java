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
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mygdx.game.meeple.MeeplePlacement;
import com.mygdx.game.network.NetworkHelper;
import com.mygdx.game.network.response.MeeplePlacementMessage;
import com.mygdx.game.network.response.RemoveMeepleMessage;
import com.mygdx.game.screen.GameScreen;

public class Board {
    public List<TileActor> availableTiles = new ArrayList<>();
    public HashMap<Position, TileActor> placedTiles = new HashMap<>();
    private GameBoard gb;
    private GameScreen gs;


    public List<TileActor> getAvailableTiles() {
        return availableTiles;

    }


    public Board(GameBoard gb, GameScreen gs) {
        this.gb = gb;
        this.gs = gs;
    }

    public Map<Position, TileActor> getPlacedTiles() {
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

    public int scoreRoadOrCityRec(TileActor tile, Feature feature, TileActor parent, Set<TileActor> visited) {
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

    // e.g. for TOP we want BOTTOM, for RIGHT we want LEFT ...
    public Side getFacingSideOfSurroundingTile(Side side) {
        return Side.values()[side.ordinal() ^ 2];
    }

    public boolean isValidAtPosition(TileActor tile, Position pos) {
        if (placedTiles.containsKey(pos)) return false;

        boolean connected = false;
        Position[] surroundPositions = pos.getSurroundingPositions();
        for (Side side : Side.values()) {
            if (placedTiles.containsKey(surroundPositions[side.ordinal()])) {
                connected = true;
                TileActor surroundTile = placedTiles.get(surroundPositions[side.ordinal()]);
                Feature feature1 = tile.getFeatureAtSide(side);
                Feature feature2 = surroundTile.getFeatureAtSide(getFacingSideOfSurroundingTile(side));

                // meeple placement probably needs the top feature of a Monastery to be Monastery,
                // so this fixes the placement bug caused by it,
                // without interfering with it.
                if (feature1 instanceof Monastery && feature2 == null) continue;
                if (feature2 instanceof Monastery && feature1 == null) continue;

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
            case TOP:
                return placedTiles.get(tile.getPosition().add(new Position(0, 1)));
            case RIGHT:
                return placedTiles.get(tile.getPosition().add(new Position(1, 0)));
            case BOTTOM:
                return placedTiles.get(tile.getPosition().add(new Position(0, -1)));
            case LEFT:
                return placedTiles.get(tile.getPosition().add(new Position(-1, 0)));
            default:
                return null;
        }
    }

    public int getScore(TileActor tile, Feature feature) {
        return tile.getScore(feature, this);
    }

}