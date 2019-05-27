package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Player;
import com.mygdx.game.Position;
import com.mygdx.game.tile.Side;
import com.mygdx.game.network.GameClient;
import com.mygdx.game.network.NetworkHelper;
import com.mygdx.game.network.TestOutput;

import java.util.List;

// TODO: add the current Tile view (first add UI stage)
// TODO: add Players and turnbased game (also add the playerUIs with scores...)
// TODO: add algorithmic for scoring
// TODO: "Home-Button": goes back to baseTile and resets zoom.

public class GameScreen implements Screen {
    private Game game;
    private Stage stage;
    private Stage stageUI;
    private OrthographicCamera camera;
    private GameBoard gameBoard;


    private Skin skin;
    private boolean isLocal;
    private GameClient gameClient;


    private InputMultiplexer multiplexer;
    private Label labelTilesLeft;
    private Label currentPlayerLabel;
    public  static TextButton placeMeeple;

    public GameScreen(Game aGame, List<Player> players, boolean isLocal, Player me, GameClient gameClient) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        stageUI = new Stage(new ScreenViewport());
        gameBoard = new GameBoard(stage, stageUI, players, isLocal, me, gameClient);
        gameBoard.init();
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        this.isLocal = isLocal;
        this.gameClient = gameClient;

        placeMeeple = new TextButton("place Meeple", Carcassonne.skin, "default");
        placeMeeple.setWidth(Gdx.graphics.getWidth() / 4f);
        placeMeeple.setHeight(Gdx.graphics.getHeight() / 8f);
        placeMeeple.getLabel().setFontScale(0.8f);
        placeMeeple.setPosition(10, 0);
        placeMeeple.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug("touch", "start touch up");
                game.setScreen(new ChosenMeeplePlacementScreen(GameScreen.this, game, gameBoard));
            }
        });
        stageUI.addActor(placeMeeple);

        placeMeeple.setVisible(false);




        Gdx.input.setInputProcessor(stage);

        if (NetworkHelper.getGameManager() != null) {
            NetworkHelper.getGameManager().addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    receive(connection, object);
                }
            });
        }

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
                //return false;
            }

            /* handle swipe camera movement */
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            }
        });

        /* handle standard zoom gesture */
        stage.addListener(new ActorGestureListener() {
            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                super.zoom(event, initialDistance, distance);
                camera.zoom = (initialDistance / distance) * camera.zoom;
                camera.update();
            }


        });

        camera = (OrthographicCamera) stage.getViewport().getCamera();
        camera.translate(-Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight() / 2);


        // TODO currently so we can differentiate between board tiles and newestTile.
        // TODO show newestTile in right corner and make it bigger.
        camera.zoom *= 1.2;
        camera.update();

        multiplexer = new InputMultiplexer();
        /* UI gets click first (call event.handle() in listener to not pass the event down to game stage */
        multiplexer.addProcessor(stageUI);
        multiplexer.addProcessor(stage);

        labelTilesLeft = new Label("", Carcassonne.skin);
        labelTilesLeft.setAlignment(Align.center);
        labelTilesLeft.setWidth(Gdx.graphics.getWidth());
        labelTilesLeft.setFontScale(2);
        labelTilesLeft.setY(20);

        currentPlayerLabel = new Label("", Carcassonne.skin);
        currentPlayerLabel.setAlignment(Align.center);
        currentPlayerLabel.setWidth(Gdx.graphics.getWidth());
        currentPlayerLabel.setFontScale(2);
        currentPlayerLabel.setY(60);

        stageUI.addActor(labelTilesLeft);
        stageUI.addActor(currentPlayerLabel);
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        // clear the screen with dark blue
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        labelTilesLeft.setText("Tiles left: " + gameBoard.tilesLeft());
        currentPlayerLabel.setText("Current player: " + gameBoard.getCurrentPlayer().getName());

        stage.act();
        stage.draw();
        stageUI.act();
        stageUI.draw();
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
        stageUI.dispose();
    }

    public void receive(Connection connection, Object object) {
        //do here what should happen if you get a message of type ...
        //send message with "Networkhelper.getGameManager.sentToAll(message)
        //before register the class in the Network class
        if (object instanceof TestOutput) {
            //do something
        }
    }
}