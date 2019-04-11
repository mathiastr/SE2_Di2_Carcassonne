package com.mygdx.game;

public class City extends Feature {
    private boolean coatOfArms;

    public City() {
        super();
        this.coatOfArms = false;
    }

    public void addCoatOfArms() {
        this.coatOfArms = true;
    }

}
