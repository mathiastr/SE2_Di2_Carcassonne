package com.mygdx.game.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.network.screen.ClientSearchScreen;
import com.mygdx.game.network.screen.ServerRoomScreen;

public class MainMenuScreen extends BaseScreen {
    private Game game;
    private Stage stage;
    private String touch = "touch";
    private String start = "start touch up";

    public MainMenuScreen(final Game game1) {
        game = game1;
        stage = new Stage(new ScreenViewport());
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Texture background = new Texture("background.png");
        Image bg = new Image(background);
        bg.setWidth(Gdx.graphics.getWidth());
        bg.setHeight(Gdx.graphics.getHeight());
        stage.addActor(bg);

        /* TODO: make labels less pixelated */
        Label title = getLabel();
        stage.addActor(title);

        TextButton jgButton = getJgTextButton();
        stage.addActor(jgButton);

        TextButton lgButton = getLgTextButton(jgButton);
        stage.addActor(lgButton);

        TextButton cgButton = getCgTextButton();
        stage.addActor(cgButton);

        TextButton settButton = getSetButton();
        stage.addActor(settButton);

        Gdx.input.setInputProcessor(stage);
    }

    private TextButton getSetButton() {
        TextButton settButton = new TextButton("Settings", Carcassonne.skin, "menu");
        settButton.setWidth((float) Gdx.graphics.getWidth() / 4f);
        settButton.setPosition((float) Gdx.graphics.getWidth() / 2f - settButton.getWidth() / 2, (float) Gdx.graphics.getHeight() * 6f / 9 - settButton.getHeight() * 3);
        settButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug(touch, start);
                game.setScreen(new SettingScreen(game));
            }
        });
        return settButton;
    }

    private TextButton getCgTextButton() {
        TextButton cgButton = new TextButton("Create Game", Carcassonne.skin, "menu");
        cgButton.setWidth((float) Gdx.graphics.getWidth() / 4f);
        cgButton.setPosition((float) Gdx.graphics.getWidth() / 2f - cgButton.getWidth() / 2, (float) Gdx.graphics.getHeight() * 6f / 9 - cgButton.getHeight() * 3 / 2);
        cgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug(touch, start);
                game.setScreen(new ServerRoomScreen(game));
            }
        });
        return cgButton;
    }

    private TextButton getLgTextButton(TextButton jgButton) {
        TextButton lgButton = new TextButton("Local Game", Carcassonne.skin, "menu");
        lgButton.setWidth((float) Gdx.graphics.getWidth() / 4f);
        lgButton.setPosition((float) Gdx.graphics.getWidth() / 2f - jgButton.getWidth() / 2, (float) Gdx.graphics.getHeight() * 6f / 9 - lgButton.getHeight() * 9 / 2);
        lgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug(touch, "local game touch up");
                game.setScreen(new CreatePlayersScreen(game));

            }
        });
        return lgButton;
    }

    private TextButton getJgTextButton() {
        TextButton jgButton = new TextButton("Join Game", Carcassonne.skin, "menu");
        jgButton.setWidth((float) Gdx.graphics.getWidth() / 4f);
        jgButton.setPosition((float) Gdx.graphics.getWidth() / 2f - jgButton.getWidth() / 2, (float) Gdx.graphics.getHeight() * 6f / 9);
        jgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug(touch, start);
                game.setScreen(new ClientSearchScreen(game));
            }
        });
        return jgButton;
    }

    private Label getLabel() {
        Label title = new Label("CARCASSONNE", Carcassonne.skin, "menu");
        title.setAlignment(Align.center);
        title.setY((float) Gdx.graphics.getHeight() * 7f / 8);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(5);
        return title;
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void dispose() {
        stage.dispose();
    }
}
