package main.java.com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import main.java.com.mygdx.game.GameBoard;
import main.java.com.mygdx.game.Player;
import main.java.com.mygdx.game.Position;
import main.java.com.mygdx.game.meeple.Meeple;
import main.java.com.mygdx.game.network.response.TilePlacementMessage;
import main.java.com.mygdx.game.tile.Feature;
import main.java.com.mygdx.game.tile.FeatureType;
import main.java.com.mygdx.game.tile.Side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TileActor extends Actor {
    private int SIZE = 128;
    private Texture texture;
    private Texture textureBig;
    private Position position;
    private static GameBoard board;

    public ArrayList<Meeple> getMeeples() {
        return meeples;
    }

    public void setMeeples(ArrayList<Meeple> meeples) {
        this.meeples = meeples;
    }

    public void addMeeple(Meeple meeple) {
        this.meeples.add(meeple);
    }

    public void removeMeeple(Meeple meeple) {
        for (Meeple m: meeples) {
            // todo
        }
    }

    private ArrayList<Meeple> meeples = new ArrayList<>(); // TODO: maybe add meeple field to Feature class
    private HashMap<Side, Feature> featureAtSide = new HashMap<>();
    private boolean monastery = false;
    private Player player1;

    public static GameBoard getGameboard(){
        return board;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /* direction of the tile
                for ex. (clockwise)
                0 - straight
                1 - 90
                2 - 180
                3 - 270
             */
    private int rotation;

    /* what does this tile have: (city, road, monastery) */
    private List<Feature> features = new ArrayList<>();

    public int getSize() {
        return SIZE;
    }

    public void setSize(int size) {
        this.SIZE = size;
        setWidth(size);
        setHeight(size);
    }


    public List<Feature> getFeatures() {
        return features;
    }

    public TileActor(Position aPosition, final GameBoard gameBoard) {
        board = gameBoard;
        /* Position is the position of the tile on the 2D-Map [base-tile has (0, 0), upwards is (0, 1) etc.] */
        position = aPosition;
        setWidth(SIZE);
        setHeight(SIZE);
        setPosition(position.getX() * SIZE, position.getY() * SIZE);
    }

    public TileActor(final GameBoard gameBoard) {
        this(new Position(0, 0), gameBoard);
    }

    public TileActor(TilePlacementMessage tilePlacementMessage) {
        if (tilePlacementMessage.meeples != null) {
            this.meeples = new ArrayList<>();
            for (Meeple m : tilePlacementMessage.meeples) {
                this.meeples.add(m);
            }
        }
        this.monastery = tilePlacementMessage.monastery;
        this.position = tilePlacementMessage.position;

    }

    public void addFeature(Feature feature) {
        for (Side side : feature.getSides())
            if (featureAtSide.containsKey(side))
                throw new IllegalArgumentException("side already has a feature on it...");
            else
                featureAtSide.put(side, feature);
        this.features.add(feature);
    }

    public Feature getFeatureAtSide(Side side) {
        return featureAtSide.get(getTileSideAt(side));
    }

    public int getRotationValue() {
        return rotation;
    }

    // get the tile-side which after rotation points to the given side.
    public Side getTileSideAt(Side side) {
        return Side.values()[(side.ordinal() + (4 - rotation)) % 4];
    }

    // get the side which the tile-side points after rotation.
    public Side getSideAfterRotation(Side side) {
        return Side.values()[(side.ordinal() + rotation) % 4];
    }

    public void rotate() {
        this.rotation = (this.rotation + 1) % 4;
        //rotateBy(90); // doesn't matter for the hitbox if we rotate by 90 degrees
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, 360 - rotation * 90, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
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
        setPosition(position.getX() * SIZE, position.getY() * SIZE);
    }

    public GameBoard getBoard() {
        return board;
    }

    public void setMonastery() {
        monastery = true;
    }

    public boolean isMonastery() { return monastery; }

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
