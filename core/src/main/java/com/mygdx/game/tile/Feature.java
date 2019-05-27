package com.mygdx.game.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Feature {

    private ArrayList<Side> sides; //Changed Set to ArrayList
    private  HashMap<Side, Side> connections; // probably don't need that
    private boolean hasMeepleOnIt;

    public Feature() {
        this.sides = new ArrayList<>();
        this.connections = new HashMap<Side, Side>();
    }

    public Feature(List<Side> addSides) {
        this();
        sides.addAll(addSides);
    }

    public Feature(Side side) {
        this();
        sides.add(side);
    }

    public ArrayList<Side> getSides() {
        return sides;
    }

    public void addSide(Side side) {
        this.sides.add(side);
    }

    public void addConnection(Side side1, Side side2) {
        // TODO: maybe another way of keeping this
        this.connections.put(side1, side2);
        this.connections.put(side2, side1);
    }

    @Override
    public String toString() {
        return "Feature{" +
                "sides=" + sides +
                '}';
    }

    public boolean isHasMeepleOnIt(){
        return hasMeepleOnIt;
    }

    public void setHasMeepleOnIt(boolean hasMeepleOnIt){
        this.hasMeepleOnIt = hasMeepleOnIt;
    }

    public void addMeeple(){
        if(hasMeepleOnIt == false ){
            setHasMeepleOnIt(true);
        }
    }
}
