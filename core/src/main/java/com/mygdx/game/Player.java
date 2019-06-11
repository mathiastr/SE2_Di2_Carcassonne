package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.network.response.PlayerGameMessage;

import java.util.ArrayList;

public class Player {
    public static final int CHEAT_SCORE = 100;
    private int id;
    public static final int MEEPLE_COUNT = 7;
    private int score;
    private GameBoard.Color color;

    public ArrayList<Meeple> getMeeples() {
        return meeples;
    }

    private ArrayList<Meeple> meeples = new ArrayList<>();
    private Texture photo;
    private String name;
    private int timeToDetectUsedCheats;

    public boolean isCheater() {
        return timeToDetectUsedCheats > 0;
    }

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
        for (int i = 0; i < MEEPLE_COUNT; i++) {
            this.meeples.add(new Meeple(this.color));
        }
        this.name = name;
        this.timeToDetectUsedCheats = 0;
        // default picture
        // TODO change Texture to byte-array, otherwise Texture gets registered.
        //FileHandle defaultPicture = Gdx.files.internal("profilePhoto.png");
        //if (defaultPicture != null) this.photo = new Texture(defaultPicture);
    }

    public Player(PlayerGameMessage p) {
        this.score = p.getScore();
        this.name = p.name;
        this.color = GameBoard.Color.GREEN;
        this.meeples = new ArrayList<Meeple>();
        for (int i = 0; i < MEEPLE_COUNT; i++) {
            this.meeples.add(new Meeple(this.color));
        }
        //standart texture
        this.photo = new Texture(Gdx.files.internal("profilePhoto.png"));

        this.timeToDetectUsedCheats = 0;
    }

    public Meeple getUnusedMeeple() {
        if (this.meeples.size() != 0) {
            Meeple lastMeeple = this.meeples.get(this.meeples.size() - 1);
            this.meeples.remove(this.meeples.size() - 1);
            return lastMeeple;
        } else {
            throw new RuntimeException("No more meeples");
        }
    }

    public void cheat(CheatType type) {
        switch (type) {
            case SCORE:
                addScore(CHEAT_SCORE);
                break;
            case MEEPLE:
                if (meeples != null) {
                    meeples.add(new Meeple(color));
                }
                break;
        }
        setTimeToDetectUsedCheats(3);
    }

    public void detectCheat() {
        meeples = new ArrayList<>();
        subtractScore(CHEAT_SCORE * 3);
        setTimeToDetectUsedCheats(0);
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void subtractScore(int score) {this.score -= score;}

    public int getTimeToDetectUsedCheats() {
        return timeToDetectUsedCheats;
    }

    public void setTimeToDetectUsedCheats(int timeToDetectUsedCheats) {
        this.timeToDetectUsedCheats = timeToDetectUsedCheats;
    }

    public void reduceCheatTimeByOne() {
        if (timeToDetectUsedCheats > 0) {
            timeToDetectUsedCheats--;
        }
    }

    public void addTimeToDetectUsedCheats(int time) {
        this.timeToDetectUsedCheats += time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player() {
    }
}
