package com.mygdx.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Position;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.network.response.TilePlacementMessage;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.FeatureType;
import com.mygdx.game.tile.Side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class TileActor extends Actor {
    private static final Logger LOGGER = Logger.getLogger(TileActor.class.getSimpleName());
    private int SIZE = 128;
    private Texture texture;
    private Texture textureBig;
    private Position position;
    private ArrayList<Meeple> meeples = new ArrayList<>(); // TODO: maybe add meeple field to Feature class
    private HashMap<Side, Feature> featureAtSide = new HashMap<>();
    private boolean monastery = false;

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
    private int rotation = 0;

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

    public TileActor(Position aPosition) {
        /* Position is the position of the tile on the 2D-Map [base-tile has (0, 0), upwards is (0, 1) etc.] */
        position = aPosition;
        setWidth(SIZE);
        setHeight(SIZE);
        setPosition((float)position.getX() * SIZE, (float)position.getY() * SIZE);
    }

    public TileActor() {
        this(new Position(0, 0));
    }

    public TileActor(TilePlacementMessage tilePlacementMessage) {
        if (tilePlacementMessage.getMeeples() != null) {
            this.meeples = new ArrayList<>();
            for (Meeple m : tilePlacementMessage.getMeeples()) {
                this.meeples.add(m);
            }
        }
        this.monastery = tilePlacementMessage.isMonastery();
        this.position = tilePlacementMessage.getPosition();

    }

    public void addFeature(Feature feature) {
        for (Side side : feature.getSides())
            if (featureAtSide.containsKey(side))
                throw new IllegalArgumentException("side already has a feature on it...");
            else
                featureAtSide.put(side, feature);
        this.features.add(feature);
    }

    public TileActor getTileOnSide(Side side)
    {
        return GameBoard.getUsedTileHash().get(this.position.getPositionOnSide(side));
    }

    public void updateTileFeatures()
    {
        for(Side side : Side.values()) {
            TileActor borderingTile = this.getTileOnSide(side);
            Feature feature = this.getFeatureAtSide(side);
            try {
                if (borderingTile != null) {
                    Feature borderingFeature = borderingTile.getFeatureAtSide(side.getOppositeSide());

                    // if features are of the same type
                    if (feature.getClass().equals(borderingFeature.getClass())) {
                        // then act like the feature of this tile has a meeple on it
                        // if the bordering feature has a meeple on it.
                        feature.setHasMeepleOnIt(borderingFeature.hasMeepleOnIt());
                    }
                }
            }catch (NullPointerException e){

                LOGGER.warning("NullPointerException" );

            }
        }
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
        batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, (float)360 - rotation * 90, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
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
        setPosition((float)position.getX() * SIZE, (float)position.getY() * SIZE);
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
