package com.mygdx.game;

import java.util.ArrayList;

public class Player {
    private int score;
    private GameBoard.Color color;
    private ArrayList<Meeple> meeples;
    Meeple meeple1;
    Meeple meeple2;
    Meeple meeple3;
    Meeple meeple4;
    Meeple meeple5;
    Meeple meeple6;
    Meeple meeple7;

    public void setUpMeeple() {
        meeple1 = new Meeple();
        meeple2 = new Meeple();
        meeple3 = new Meeple();
        meeple4 = new Meeple();
        meeple5 = new Meeple();
        meeple6 = new Meeple();
        meeple7 = new Meeple();

        meeples = new ArrayList<Meeple>();

        meeples.add(meeple1);
        meeples.add(meeple2);
        meeples.add(meeple3);
        meeples.add(meeple4);
        meeples.add(meeple5);
        meeples.add(meeple6);
        meeples.add(meeple7);

    }
    //new ArrayList<Meeple>(Arrays.asList(meeple1, meeple2, meeple3, meeple4, meeple5, meeple6, meeple7));

    public boolean checkIfMeepleAvailable(){
        setUpMeeple();
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
