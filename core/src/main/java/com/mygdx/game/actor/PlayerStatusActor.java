package com.mygdx.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Player;

public class PlayerStatusActor extends Actor {
    private Texture texture;
    public static final float WIDTH = 350;
    public static final float HEIGHT = 270;
    private final BitmapFont font;
    private Player player;
    private Table info;
    private Texture profilePhoto;
    private Texture meeple;
    private Texture score;
    private Label.LabelStyle textStyle;

    public PlayerStatusActor(Player player) {
        this.texture = new Texture("playerStatusBackground.jpg");
        this.player = player;
        setSize(WIDTH, HEIGHT);
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        font.getData().setScale(3);

        //TODO get profile photo from player
        if (player.getPhoto() == null) {
            profilePhoto = new Texture(Gdx.files.internal("profilePhoto.png"));
        } else {
            profilePhoto = player.getPhoto();
        }
        meeple = new Texture(Gdx.files.internal("meeple.png"));
        score = new Texture(Gdx.files.internal("coin.png"));

        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        textStyle.fontColor = Color.BLACK;

        updateInfo();

        info.setDebug(true);
    }

    public void updateInfo(){

         Label meepleLabel;
         Image meepleImg;
         Image scoreImg;
         Label scoreLabel;
         Label nameLabel;
         Label color;

        info = new Table();

        nameLabel = new Label("" + player.getName(), textStyle);
        info.add(nameLabel).colspan(4).center();
        info.row();
        meepleImg = new Image(meeple);
        info.add(meepleImg).width(50).height(50);
        meepleLabel = new Label("" + player.getNumberOfMeeples(), textStyle);
        info.add(meepleLabel).padLeft(20);
        scoreImg = new Image(score);
        info.add(scoreImg).width(50).height(50);
        scoreLabel = new Label("" + player.getScore(), textStyle);
        info.add(scoreLabel)/*.width(WIDTH / 4).height(50)*/.padLeft(20);

        info.row();
        color = new Label("" + player.getColor().name(), textStyle);
        info.add(color);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), WIDTH, HEIGHT);

        int expectedProfilePhotoWidth = 150;
        int expectedProfilePhotoHeight = 150;
        //batch.draw(profilePhoto, getX() + (WIDTH - expectedProfilePhotoWidth) / 2f, getY() + 120, expectedProfilePhotoWidth, expectedProfilePhotoHeight);

        info.draw(batch, parentAlpha);


/*
        batch.draw(meeple, getX() + 20, getY() + 100, 50, 50);
        font.draw(batch, "" + player.getNumberOfMeeples(), getX() + 120, getY() + 100);
        batch.draw(score, getX() + 20, getY() + 50, 50, 50);
        font.draw(batch, "" + player.getScore(), getX() + 120, getY() + 50);
*/
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        info.setPosition(getX() + WIDTH / 2f, getY() + 60);
        info.act(delta);
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

