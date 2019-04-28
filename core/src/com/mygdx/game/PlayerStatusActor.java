package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerStatusActor extends Actor {
    private Texture texture;
    public static final int WIDTH = 350;
    public static final int HEIGHT = 270;
    private final BitmapFont font;
    private Player player;

    public PlayerStatusActor(Player player) {
        this.texture = new Texture("playerStatusBackground.jpg");
        this.player = player;

        setSize(WIDTH, HEIGHT);
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        font.getData().setScale(3);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), WIDTH, HEIGHT);


        //TODO get profile photo from player
        Texture profilePhoto = new Texture(Gdx.files.internal("profilePhoto.png"));
        int expectedProfilePhotoWidth = 150;
        int expectedProfilePhotoHeight = 150;
        batch.draw(profilePhoto, getX() + (WIDTH - expectedProfilePhotoWidth) / 2, getY() + 120, expectedProfilePhotoWidth, expectedProfilePhotoHeight);
        font.draw(batch, "Score: " + player.getScore(), getX() + 20, getY() + 100);
        font.draw(batch, "Meeples: " + player.getNumberOfMeeples(), getX() + 20, getY() + 50);
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }


}
