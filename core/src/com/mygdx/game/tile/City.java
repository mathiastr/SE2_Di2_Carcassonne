package com.mygdx.game.tile;

import java.util.List;

public class City extends Feature {
    private boolean coatOfArms;

    public City() {
        super();
        this.coatOfArms = false;
    }

    public City(List<Side> addSides) {
        super(addSides);
    }

    public City(Side side) {
        super(side);
    }

    public void addCoatOfArms() {
        this.coatOfArms = true;
    }
}
