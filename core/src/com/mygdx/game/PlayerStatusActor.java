package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerStatusActor extends Actor {
    private Texture texture;
    public static final int WIDTH = 200;
    public static final int HEIGHT = 200;

    public PlayerStatusActor() {
        this.texture = new Texture("playerStatusBackground.jpg");
        setSize(WIDTH, HEIGHT);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), WIDTH, HEIGHT);
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
