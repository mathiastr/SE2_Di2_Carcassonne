package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CreatePlayersScreen implements Screen {

    private Game game;
    private Stage stage;
    private Texture background;


    public CreatePlayersScreen(final Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        // TODO: do I need this?
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        background = new Texture("background.png");
        Image backgroundImage = new Image(background);
        backgroundImage.setWidth(Gdx.graphics.getWidth());
        backgroundImage.setHeight(Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        // TODO another styleName
        TextButton startGameButton = new TextButton("Start Game", Carcassonne.skin, "menu");
        startGameButton.setWidth(Gdx.graphics.getWidth() / 4);
        startGameButton.setPosition(Gdx.graphics.getWidth() / 2 - startGameButton.getWidth() / 2, Gdx.graphics.getHeight() * 6 / 9);
        startGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("touch", "start button is touched");
                game.setScreen(new GameScreen(game));
            }
        });

        stage.addActor(startGameButton);
        // TODO what is that?
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
