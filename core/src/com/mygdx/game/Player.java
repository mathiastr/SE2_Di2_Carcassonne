package com.mygdx.game;

import java.util.ArrayList;

public class Player {
    private int score;
    private GameBoard.Color color;
    private ArrayList<Meeple> meeples;

    public boolean checkIfMeepleAvailable(){
        for (int i = 0; i < meeples.size(); i++){
            if(
                    meeples.get(i).isBusy() == false){
                meeples.get(i).makeBusy();
                return true;
            }
        }return false;
    }

    public Player(GameBoard.Color color) {
        this.score = 0;
        this.color = color;
        this.meeples = new ArrayList<Meeple>();
        for (int i = 0; i < 7; i++) {
            this.meeples.add(new Meeple(this.color));
        }

    }

    public void addScore(int score) {
        this.score += score;
    }
}
