package com.mygdx.game.tile;

import java.util.List;

public class City extends Feature {
    private boolean coatOfArms;

    public City(Side right, Side left, Side top, Side bottom) {
        super();
        this.coatOfArms = false;
    }

    public City(List<Side> addSides) {
        super(addSides);
    }

    public City(Side side) {
        super(side);
    }

    public City(){}

    public void addCoatOfArms() {
        this.coatOfArms = true;
    }
}
