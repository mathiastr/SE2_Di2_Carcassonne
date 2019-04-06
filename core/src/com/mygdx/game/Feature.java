package com.mygdx.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Feature {
    private Set<Side> sides;
    private  HashMap<Side, Side> connections;
    private boolean isMonastery;

    public Feature() {
        this.sides = new HashSet<Side>();
        this.connections = new HashMap<Side, Side>();
    }

    public void addSide(Side side) {
        this.sides.add(side);
    }

    public void addConnection(Side side1, Side side2) {
        // TODO: maybe another way of keeping this
        this.connections.put(side1, side2);
        this.connections.put(side2, side1);
    }

}
