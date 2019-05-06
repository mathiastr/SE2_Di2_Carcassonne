package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.Image;

public class Meeple extends Actor {
    private MeepleType meepleType;
    private GameBoard.Color color;
    private boolean isBusy;
    private Image img;


    public Meeple(Image img) {
        this.img = img;
    }

    public Meeple(GameBoard.Color color){
        this.color = color;
    }

    public void changeMeepleType(MeepleType mt) {
        meepleType = mt;
    }

    public void makeBusy() {
        this.isBusy = true;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void free() {
        this.isBusy = false;
    }
}
