package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Player {
    private int score;
    private GameBoard.Color color;
    private ArrayList<Meeple> meeples;

    public Texture getPhoto() {
        return photo;
    }

    public void setPhoto(Texture photo) {
        this.photo = photo;
    }

    private Texture photo;

    public int getNumberOfMeeples() {
        return meeples.size();
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public GameBoard.Color getColor() {
        return color;
    }

    public void setColor(GameBoard.Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    // TODO meeples inactive and active
    public boolean checkIfMeepleAvailable(){
        for (int i = 0; i < meeples.size(); i++){
            if(
                    meeples.get(i).isBusy() == false){
                meeples.get(i).makeBusy();
                return true;
            }
        }return false;
    }

    public Player(GameBoard.Color color, String name) {
        this.score = 0;
        this.color = color;
        this.meeples = new ArrayList<Meeple>();
        for (int i = 0; i < 7; i++) {
            this.meeples.add(new Meeple(this.color));
        }
        this.name = name;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
