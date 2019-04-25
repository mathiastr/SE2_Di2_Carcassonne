package com.mygdx.game.network;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Connection;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.GameScreen;
import com.mygdx.game.network.screen.ClientSearchScreen;
import com.mygdx.game.network.screen.ServerRoomScreen;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class NetworkLobbyScreen implements Screen {

    private Game game;
    private Stage stage;
    private Label output;


    public NetworkLobbyScreen(final Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        output = new Label("Output", Carcassonne.skin);
        output.setAlignment(Align.center);
        output.setName("Output");
        output.setY(Gdx.graphics.getHeight()/8*7);
        output.setWidth(Gdx.graphics.getWidth());
        output.setFontScale(3);
        stage.addActor(output);

        final TextButton host = new TextButton("Host", Carcassonne.skin);
        host.setWidth(Gdx.graphics.getWidth() / 2);
        host.setPosition(Gdx.graphics.getWidth() / 2 - host.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        host.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ServerRoomScreen(game));
            }
        });
        stage.addActor(host);

        TextButton client = new TextButton("Client", Carcassonne.skin);
        client.setWidth(Gdx.graphics.getWidth() / 2);
        client.setPosition(Gdx.graphics.getWidth() / 2 - client.getWidth() / 2, Gdx.graphics.getHeight() / 4 * 3);
        client.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ClientSearchScreen(game));
            }
        });
        stage.addActor(client);

        TextField name = new TextField("Rudi", Carcassonne.skin);
        name.setWidth(Gdx.graphics.getWidth() / 2);
        name.setPosition(Gdx.graphics.getWidth() / 2 - client.getWidth() / 2, Gdx.graphics.getHeight() / 4);

        stage.addActor(name);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // clear the screen with dark blue
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

    }

}
