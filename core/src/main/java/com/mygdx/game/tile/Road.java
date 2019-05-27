package com.mygdx.game.tile;

import java.util.List;

public class Road extends Feature {
    public Road() {
        super();
    }

    public Road(List<Side> addSides) {
        super(addSides);
    }

    public Road(Side side) {
        super(side);
    }
}
