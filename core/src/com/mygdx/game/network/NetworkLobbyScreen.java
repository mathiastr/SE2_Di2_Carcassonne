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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Connection;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.GameScreen;

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
                Gdx.app.debug("touch", "start touch up");
                try{
                    GameServer server = new GameServer();
                    NetworkHelper.setGameManager(server);
                    output.setText("hosted");
                }catch (IOException io){
                    Gdx.app.debug("network","server problem");
                }
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
                GameClient gameClient = new GameClient();
                if(NetworkHelper.getGameManager() != null &&
                        NetworkHelper.getGameManager().getClass() == GameServer.class){
                    ((GameServer)NetworkHelper.getGameManager()).destroy();
                }
                NetworkHelper.setGameManager(gameClient);
                List<InetAddress> hosts = gameClient.discover();
                if(!hosts.isEmpty()){
                    output.setText("found" + hosts.size());
                    gameClient.connect(hosts.get(0));
                    output.setText("cliented");
                }else {
                    output.setText("nothing found");
                }

                Gdx.app.debug("network","client");
            }
        });
        stage.addActor(client);

        TextButton send = new TextButton("send Message", Carcassonne.skin);
        send.setWidth(Gdx.graphics.getWidth() / 2);
        send.setPosition(Gdx.graphics.getWidth() / 2 - send.getWidth() / 2, Gdx.graphics.getHeight() / 4);
        send.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                if(NetworkHelper.getGameManager() != null){
                    Gdx.app.debug("network",NetworkHelper.getGameManager().getClass().toString());
                    if(NetworkHelper.getGameManager().getClass() == GameClient.class){
                        GameClient gameClient = (GameClient)NetworkHelper.getGameManager();
                        Gdx.app.debug("network",GameClient.class.toString());
                        gameClient.sendMessage("Hi Host");
                    }
                }
                else{
                    Gdx.app.debug("network","No Network");
                }
            }
        });
        stage.addActor(send);
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
