package com.mygdx.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Player;
import com.mygdx.game.emotes.Emote;

public class PlayerStatusActor extends Group {
    private Image texture;
    public static final float WIDTH = 350;
    public static final float HEIGHT = 270;
    private final BitmapFont font;
    private Player player;
    private Table info;
    private Texture profilePhoto;
    private Texture meeple;
    private Texture score;
    private Label.LabelStyle textStyle;
    private Label meepleLabel;
    private Image meepleImg;
    private Image scoreImg;
    private Label scoreLabel;
    private Label nameLabel;

    public PlayerStatusActor(Player player) {
        this.texture = new Image(new Texture("playerStatusBackground.jpg"));
        this.addActor(texture);
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

        createInfo();
    }

    public void createInfo()
    {

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
        info.setPosition(this.getX() + WIDTH/2, this.getY() + HEIGHT/2 - 60);
        this.addActor(info);
    }

    public void updateInfo(){
        nameLabel.setText("" + player.getName());
        meepleLabel.setText("" + player.getNumberOfMeeples());
        scoreLabel.setText("" + player.getScore());
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

