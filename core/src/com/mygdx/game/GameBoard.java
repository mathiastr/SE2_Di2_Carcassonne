package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard {
    private HashMap<Position, TileActor> tiles;
    private Stage stageOfBoard;

    public enum Color {
        yellow, red, green, blue, black
    }

    /*
        Map with tile type number (coreesponding to the rules) and tile by it self
     */
    private HashMap<Integer, TileActor> allTileTypes;

    /*
        The number of each tile type is specified in rules
     */
    private HashMap<Integer, Integer> numberOfTileTypeLeft;

    private ArrayList<Player> players;


    public GameBoard(Stage stage) {
        tiles = new HashMap<Position, TileActor>();
        Position initialPosition = new Position(0, 0);
        AddTileActor initialTile = new AddTileActor(initialPosition, this);
        tiles.put(initialPosition, initialTile);
        stageOfBoard = stage;
        stageOfBoard.addActor(initialTile);

        // TODO create all tile types
    }

    // TODO add a TileActor as a parameter
    public TileActor replaceTileAt(AddTileActor tileToReplace) {

        TileActor newTile = new TileActor(tileToReplace.getPosition(), tileToReplace.getBoard());
        stageOfBoard.addActor(newTile);
        tiles.put(newTile.getPosition(), newTile);
        tileToReplace.remove();
        return newTile;
    }

    public void addSurroundingAddTiles(TileActor tile) {
        Position dxdy_arr[] = {new Position(0, 1), new Position(1, 0), new Position(0, -1), new Position(-1, 0)};
        for (Position dxdy : dxdy_arr) {
            Position newPosition = tile.getPosition().add(dxdy);
            if (!tiles.containsKey(newPosition)) {
                AddTileActor newAddTile = new AddTileActor(newPosition, this);
                tiles.put(tile.getPosition().add(dxdy), newAddTile);
                stageOfBoard.addActor(newAddTile);
            }
        }
    }

    /*
        method for dynamic checking owners of the city/road/field
        if two or more players own the same property, score is splitted between
     */
    public ArrayList<Player> getFeatureOwners(Feature feature, Side side, TileActor tile) {

        // count number of meeples of each player

        return new ArrayList<Player>();
    }


}
