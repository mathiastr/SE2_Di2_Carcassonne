package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.network.response.PlayerGameMessage;

import java.util.ArrayList;

public class Player {
    private int score;
    private GameBoard.Color color;
    private ArrayList<Meeple> meeples;
    private Texture photo;
    private String name;

    public Texture getPhoto() {
        return photo;
    }

    public void setPhoto(Texture photo) {
        this.photo = photo;
    }


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

    public Player(GameBoard.Color color, String name) {
        this.score = 0;
        this.color = color;
        this.meeples = new ArrayList<Meeple>();
        for (int i = 0; i < 7; i++) {
            this.meeples.add(new Meeple(this.color));
        }
        this.name = name;
    }

    public Player(PlayerGameMessage p) {
        this.score = p.score;
        this.name = p.name;
        this.color = GameBoard.Color.green;
        this.meeples = new ArrayList<Meeple>();
        for (int i = 0; i < 7; i++) {
            this.meeples.add(new Meeple(this.color));
        }
        //standart texture
        this.photo = new Texture(Gdx.files.internal("profilePhoto.png"));
    }
    public Meeple getUnusedMeeple() throws Exception {
        if (this.meeples.size() != 0) {
            Meeple lastMeeple = this.meeples.get(this.meeples.size() - 1);
            this.meeples.remove(this.meeples.size() - 1);
            return lastMeeple;
        } else {
            //TODO: PopUp No more Meeple
            throw new Exception("No more meeples");
        }
    }

    public void addScore(int score) {
        this.score += score;
    }



    public Player(){}
}
