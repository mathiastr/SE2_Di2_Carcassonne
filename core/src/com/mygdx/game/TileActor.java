package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class TileActor extends Actor{
    private static int SIZE = 128;
    private Texture texture;
    private Position position;
    private final GameBoard board;
    private ArrayList<Meeple> meeples;

    /* direction of the tile
        for ex. (clockwise)
        0 - straight
        1 - 90
        2 - 180
        3 - 270
     */
    private int rotation;

    /*
     what does this tile have: (city, road, field, monastery)
      */
    private Feature[] features;


    // TODO: add rotation

    public TileActor(Position aPosition, final GameBoard gameBoard) {
        board = gameBoard;
        /* Position is the position of the tile on the 2D-Map [base-tile has (0, 0), upwards is (0, 1) etc.] */
        position = aPosition;

        setWidth(SIZE);
        setHeight(SIZE);
        setPosition(position.getX()*SIZE, position.getY()*SIZE);
        texture = new Texture(Gdx.files.internal("tile.png")); // probably should'nt always create it again.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
    }



    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public GameBoard getBoard() {
        return board;
    }

    // Do not forget about rotation!

    public boolean areSidesConnected(Side side1, Side side2, FeatureType type) {

        return true;
    }

    public boolean hasFeautureOnSide(Side side) {

        return true;
    }

    public boolean isFeatureHaMeeple(Feature feature, Side side) {

        return true;
    }

    public void placeMeepleOnFeature(Feature feature, Meeple meeple) {

    }
}
