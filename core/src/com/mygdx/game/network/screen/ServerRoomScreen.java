package com.mygdx.game.network.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.GameBoard;
import com.mygdx.game.GameScreen;
import com.mygdx.game.MainMenuScreen;
import com.mygdx.game.Player;
import com.mygdx.game.network.GameServer;
import com.mygdx.game.network.NetworkDevice;
import com.mygdx.game.network.NetworkHelper;
import com.mygdx.game.network.TestOutput;
import com.mygdx.game.network.response.InitGameMessage;
import com.mygdx.game.network.response.PlayerGameMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerRoomScreen implements Screen {

    private Game game;
    private Stage stage;
    private Label output;
    private List<TextButton> devices;

    public ServerRoomScreen(final Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        output = new Label("Server room", Carcassonne.skin);
        output.setAlignment(Align.center);
        output.setY(Gdx.graphics.getHeight()/8*7);
        output.setWidth(Gdx.graphics.getWidth());
        output.setFontScale(3);
        stage.addActor(output);

        devices = new ArrayList<TextButton>();

        for (int i = 0; i < 6; i++) {
            TextButton device = new TextButton("Device " +(i+1), Carcassonne.skin);
            device.setWidth(Gdx.graphics.getWidth() / 2 - 40);
            device.setHeight(Gdx.graphics.getHeight()/5 - 40);
            if(i%2 == 0){
                device.setPosition(20, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() /5 * (i/2+2) + 40);
                devices.add(device);
            }else{
                device.setPosition(20 + Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() /5 * (i/2+2) + 40);
                devices.add(device);
            }
            stage.addActor(device);
        }

        try{
            GameServer server = new GameServer();
            server.addDevice(new NetworkDevice("Host", server.getIp()));
            devices.get(0).setText(server.getDeviceList().get(0).getDeviceName()
                    + System.lineSeparator() + server.getDeviceList().get(0).getIp());
            NetworkHelper.setGameManager(server);
            server.getServer().addListener(new Listener(){
                public void received (Connection connection, Object object) {
                    receive(connection,object);
                }
            });

        }catch (IOException io){
            Gdx.app.debug("network","server problem");
        }

        TextButton back = new TextButton("Back", Carcassonne.skin);
        back.setWidth(Gdx.graphics.getWidth() / 5 - 40);
        back.setPosition(Gdx.graphics.getWidth() - back.getWidth() - 20, 40);
        back.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ((GameServer)NetworkHelper.getGameManager()).destroy();
                NetworkHelper.setGameManager(null);
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(back);

        TextButton start = new TextButton("Start", Carcassonne.skin);
        start.setWidth(Gdx.graphics.getWidth() / 5 * 2 - 40);
        start.setHeight(Gdx.graphics.getHeight() / 5 - 60);
        start.setPosition(Gdx.graphics.getWidth() / 2 - start.getWidth() / 2 - 20, 40);
        start.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                // TODO get players from
                ArrayList<Player> players = new ArrayList<>();
                players.add(new Player(GameBoard.Color.green, "Client"));
                players.add(new Player(GameBoard.Color.blue, "Server"));

                ArrayList<PlayerGameMessage> playerGameMessages = new ArrayList<>();
                for (Player p : players) {
                    playerGameMessages.add(new PlayerGameMessage(p));
                }

                InitGameMessage ig = new InitGameMessage();
                ig.setPlayers(playerGameMessages);
                //NetworkHelper.getGameManager().sendToAll(ig);

                SimpleMessage sm = new SimpleMessage();
                sm.message = "hui";

                TestOutput to = new TestOutput("smth");

                NetworkHelper.getGameManager().sendToAll(ig);

                game.setScreen(new GameScreen(game, players, false));
            }
        });
        stage.addActor(start);

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

    public void receive(Connection connection, Object object){
        if (object instanceof TestOutput) {
            NetworkDevice device = new NetworkDevice(((TestOutput) object).getTest(),
                    connection.getRemoteAddressTCP().getAddress());
            GameServer gameServer = ((GameServer)NetworkHelper.getGameManager());
            if(gameServer.getDeviceList().size() > 6){
                connection.sendTCP(new TestOutput("too many clients"));
            }else{
                gameServer.addDevice(device);
                for (int i = 1; i < gameServer.getDeviceList().size(); i++) {
                    // TODO add player name to the player list
                    devices.get(i).setText(device.getDeviceName()
                            + System.lineSeparator() +  device.getIp());
                }
            }
        }
    }

}
