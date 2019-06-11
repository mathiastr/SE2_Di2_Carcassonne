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
import com.mygdx.game.Player;
import com.mygdx.game.network.GameClient;
import com.mygdx.game.network.NetworkHelper;
import com.mygdx.game.network.response.ConnectMessage;
import com.mygdx.game.network.response.CurrentTileMessage;
import com.mygdx.game.network.response.ErrorMessage;
import com.mygdx.game.network.response.ErrorNumber;
import com.mygdx.game.network.response.InitGameMessage;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MainMenuScreen;
import com.mygdx.game.utility.Toast;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ClientSearchScreen implements Screen {

    private Game game;
    private Stage stage;
    private Label output;
    private List<TextButton> server;

    private final List<Toast> toasts = new LinkedList<Toast>();
    private Toast.ToastFactory toastFactory;


    public ClientSearchScreen(final Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        output = new Label("Looking for Server", Carcassonne.skin);
        output.setAlignment(Align.center);
        output.setY((float)Gdx.graphics.getHeight() / 8 * 7);
        output.setWidth(Gdx.graphics.getWidth());
        output.setFontScale(3);
        stage.addActor(output);

        toastFactory = new Toast.ToastFactory.Builder()
                .font(Carcassonne.skin.getFont("font-big"))
                .build();

        server = new ArrayList<TextButton>();
        final GameClient gameClient = new GameClient();
        /*
        gameClient.getClient().addListener(new Listener(){
            public void received (Connection connection, Object object) {
                receive(connection,object);
            }
        }); */
        NetworkHelper.setGameManager(gameClient);
        List<InetAddress> hosts = gameClient.discover();
        if (!hosts.isEmpty()) {
            for (final InetAddress host : hosts
            ) {
                TextButton serv = new TextButton(host.getHostName(), Carcassonne.skin);
                serv.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        try {
                            //TODO send player name + color
                            if (NetworkHelper.getPlayer() == null) {
                                gameClient.initConnection(host, new ConnectMessage(new Player(GameBoard.Color.getRandom(), "Guest")));
                            } else {
                                gameClient.initConnection(host, new ConnectMessage(NetworkHelper.getPlayer()));
                            }
                            gameClient.addListener(new Listener() {
                                public void received(Connection connection, Object object) {
                                    System.out.println("DEBUG ::: Client received: " + object.toString());
                                    if (object instanceof InitGameMessage) {
                                        // get the init info from here
                                        InitGameMessage response = (InitGameMessage) object;
                                        ArrayList<Player> players = response.getPlayers();
                                        System.out.println("info is here");
                                        Gdx.app.postRunnable(new Runnable() {
                                            @Override
                                            public void run() {
                                                // TODO get "me" from settings (your user name and etc.)
                                                game.setScreen(new GameScreen(game, players, false, NetworkHelper.getPlayer(), gameClient));
                                            }
                                        });
                                    }

                                    if (object instanceof ConnectMessage) {
                                        ConnectMessage response = (ConnectMessage) object;
                                        if(NetworkHelper.getPlayer().getId() == 0){
                                            NetworkHelper.setPlayer(response.player);

                                            System.out.println("DEBUG ::: Client is now: " + response.player.getName() + " " + connection.getID());
                                            Gdx.app.postRunnable(new Runnable() {
                                                @Override
                                                public void run() {
                                                    toastLong("You are now " + NetworkHelper.getPlayer().getName());
                                                }
                                            });
                                        }
                                    }

                                    if (object instanceof ErrorMessage) {
                                        ErrorMessage response = (ErrorMessage) object;
                                        if (response.errorNumber == ErrorNumber.TOOMANYCLIENTS) {
                                            //toastLong(response.message);
                                        }
                                    }

                                    if (object instanceof CurrentTileMessage) {
                                        NetworkHelper.setLastMessage(object);
                                        NetworkHelper.getGameManager().sendToServer(new ErrorMessage("Game not started", ErrorNumber.GAMENOTSTARTED));
                                    }
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                });
                server.add(serv);
            }
        } else {
            toastShort("No game found.");
        }

        setServerTextButtons();

        TextButton start = new TextButton("Refresh", Carcassonne.skin);
        start.setWidth((float)Gdx.graphics.getWidth() / 5 * 2 - 40);
        start.setHeight((float)Gdx.graphics.getHeight() / 5 - 60);
        start.setPosition((float)Gdx.graphics.getWidth() / 2 - start.getWidth() / 2 - 20, 40);
        start.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //toastShort("Refresh");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameClient client = ((GameClient) NetworkHelper.getGameManager());
                List<InetAddress> hosts = client.discover();
                if (!hosts.isEmpty()) {
                    server = new ArrayList<TextButton>();
                    for (InetAddress host : hosts
                    ) {
                        TextButton serv = new TextButton(host.getHostName(), Carcassonne.skin);
                        server.add(serv);
                    }
                    setServerTextButtons();
                } else {
                    toastShort("No game found.");
                }
            }
        });
        stage.addActor(start);

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
                NetworkHelper.setGameManager(null);
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(back);
        Gdx.input.setInputProcessor(stage);
    }


    private void setServerTextButtons() {
        for (int i = 0; i < server.size() && i < 6; i++) {
            TextButton device = server.get(i);
            device.setWidth((float)Gdx.graphics.getWidth() / 2 - 40);
            device.setHeight((float)Gdx.graphics.getHeight() / 5 - 40);
            if (i % 2 == 0) {
                device.setPosition(20, Gdx.graphics.getHeight() - (float)Gdx.graphics.getHeight() / 5 * ((float)i / 2 + 2) + 40);
            } else {
                device.setPosition(20 + (float)Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - (float)Gdx.graphics.getHeight() / 5 * ((float)i / 2 + 2) + 40);
            }
            stage.addActor(device);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // clear the screen with dark BLUE
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        // handle toast queue and display
        Iterator<Toast> it = toasts.iterator();
        while (it.hasNext()) {
            Toast t = it.next();
            if (!t.render(Gdx.graphics.getDeltaTime())) {
                it.remove(); // toast finished -> remove
            } else {
                break; // first toast still active, break the loop
            }
        }

    }

    /**
     * Displays long toast
     */
    public void toastLong(String text) {
        toasts.add(toastFactory.create(text, Toast.Length.LONG));
    }

    /**
     * Displays short toast
     */
    public void toastShort(String text) {
        toasts.add(toastFactory.create(text, Toast.Length.SHORT));
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

