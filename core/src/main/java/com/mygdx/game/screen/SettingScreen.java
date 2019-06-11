package com.mygdx.game.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Player;
import com.mygdx.game.network.NetworkHelper;

public class SettingScreen extends BaseScreen {

    private Stage stage;
    private final BitmapFont font;
    private Table playerListTable;

    public void renderPlayersList() {
        Label.LabelStyle textStyle = new Label.LabelStyle();
        font.getData().setScale(4);
        textStyle.font = font;
        textStyle.fontColor = Color.BLACK;

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.BLACK;

        if (playerListTable != null) {
            playerListTable.remove();
        }
        playerListTable = new Table();

        playerListTable.add(new Label("", textStyle)).width(100);
        Label nameLabel = new Label("Name", textStyle);
        nameLabel.setAlignment(Align.center);
        playerListTable.add(nameLabel).width(500);
        playerListTable.row();

        TextField nameField = new TextField(NetworkHelper.getPlayer().getName(), textFieldStyle);

        //TODO profile photo

        Texture imageTexture = new Texture(Gdx.files.internal("profilePhoto.png"));
        Image profilePhoto = new Image();
        profilePhoto.setDrawable(new TextureRegionDrawable(new TextureRegion(imageTexture)));

        profilePhoto.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Carcassonne.getNativeInterface().getPhoto((byte[] bytes) -> {
                        Pixmap p = new Pixmap(bytes, 0, bytes.length);
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                Texture tex=new Texture(p);
                                Sprite sprite = new Sprite(tex);
                                sprite.setRotation(180f);
                                profilePhoto.setDrawable(new SpriteDrawable(sprite));
                                NetworkHelper.getPlayer().setPhoto(tex);
                            }
                        });
                    });
                }
            });

            playerListTable.add(profilePhoto).width(100).height(100);
            nameField.setAlignment(Align.center);

            nameField.setTextFieldListener(new TextField.TextFieldListener() {
                @Override
                public void keyTyped(TextField textField, char c) {
                    NetworkHelper.getPlayer().setName(nameField.getText());
                }
            });
            playerListTable.add(nameField).width(500);
            playerListTable.add(new Label("" + NetworkHelper.getPlayer().getColor().name(), textStyle));
            playerListTable.row();

        playerListTable.setFillParent(true);
        playerListTable.setY(350);
        stage.addActor(playerListTable);
    }

    public SettingScreen(final Game game) {
        stage = new Stage(new ScreenViewport());

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Texture background = new Texture("background.png");
        Image backgroundImage = new Image(background);
        backgroundImage.setWidth(Gdx.graphics.getWidth());
        backgroundImage.setHeight(Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        int marginTop = 10;

        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        font.getData().setScale(5);

        if(NetworkHelper.getPlayer() == null){
            NetworkHelper.setPlayer(new Player(GameBoard.Color.RED, "Guest"));
        }
        renderPlayersList();

        TextButton back = new TextButton("Back", Carcassonne.skin);
        back.setWidth((float)Gdx.graphics.getWidth() / 5 - 40);
        back.setPosition(Gdx.graphics.getWidth() - back.getWidth() - 20, 40);
        back.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen((Screen)new MainMenuScreen(game));
            }
        });
        stage.addActor(back);
        Gdx.input.setInputProcessor(stage);

    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
