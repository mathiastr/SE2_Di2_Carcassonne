package com.mygdx.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Player;
import com.mygdx.game.emotes.EmoteManager;
import com.mygdx.game.meeple.Meeple;
import com.mygdx.game.network.GameClient;
import com.mygdx.game.network.response.EmoteMessage;
import com.mygdx.game.tile.City;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.tile.Monastery;
import com.mygdx.game.tile.Road;
import com.mygdx.game.utility.GraphicsBackend;
import com.mygdx.game.utility.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameScreen extends BaseScreen {
    private Game game;
    private Stage stage;
    private Stage stageUI;
    private Stage stageEmote;
    private OrthographicCamera camera;
    private GameBoard gameBoard;

    private InputMultiplexer multiplexer;
    private Label labelTilesLeft;
    private Label currentPlayerLabel;
    public TextButton placeMeeple;
    private Toast.ToastFactory meeplePlaced;
    private final List<Toast> toasts = new LinkedList<Toast>();

    private boolean show_emote = false;
    private EmoteManager emoteManager;
    private EmoteMessage emoteMessage;

    public GameScreen(Game aGame, List<Player> players, boolean isLocal, Player me, GameClient gameClient) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        stageUI = new Stage(new ScreenViewport());
        stageEmote = new Stage(new ScreenViewport());
        for (Player player : players) {
            player.setColor(GameBoard.Color.values()[players.indexOf(player)]);
            for (Meeple m : player.getMeeples()) {
                m.setColor(player.getColor());
            }
        }
        GraphicsBackend graphicsBackend = new GraphicsBackend();
        gameBoard = new GameBoard(this, stage, stageUI, players, isLocal, me, gameClient, this, graphicsBackend);
        gameBoard.init();

        populatePlaceMeeple();
        stageUI.addActor(placeMeeple);

        placeMeeple.setVisible(false);

        Gdx.input.setInputProcessor(stage);

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
        camera.translate(-Gdx.graphics.getWidth() / 2f, -Gdx.graphics.getHeight() / 2f);

        multiplexer = new InputMultiplexer();
        /* UI gets click first (call event.handle() in listener to not pass the event down to game stage */
        multiplexer.addProcessor(stageEmote);
        multiplexer.addProcessor(stageUI);
        multiplexer.addProcessor(stage);

        populateLabelTilesLeft();

        populateCurrentPlayerLabel();

        stageUI.addActor(labelTilesLeft);
        stageUI.addActor(currentPlayerLabel);

        if (!isLocal) emoteManager = new EmoteManager(gameBoard, stageEmote);
    }

    private void populateCurrentPlayerLabel() {
        currentPlayerLabel = new Label("", Carcassonne.skin);
        currentPlayerLabel.setAlignment(Align.center);
        currentPlayerLabel.setWidth(Gdx.graphics.getWidth());
        currentPlayerLabel.setFontScale(2);
        currentPlayerLabel.setY(60);
    }

    private void populateLabelTilesLeft() {
        labelTilesLeft = new Label("", Carcassonne.skin);
        labelTilesLeft.setAlignment(Align.center);
        labelTilesLeft.setWidth(Gdx.graphics.getWidth());
        labelTilesLeft.setFontScale(2);
        labelTilesLeft.setY(20);
    }

    private void populatePlaceMeeple() {
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
    }

    public void createMeepleIsPlacedToast(Feature feature) {
        BitmapFont font = Carcassonne.skin.getFont("font-big");
        font.getData().setScale(0.8f, 0.8f);
        Color backgroundColor = new Color(55f / 256, 55f / 256, 55f / 256, 1);
        Color fontColor = new Color(1, 1, 1, 1);
        String meepleType;

        if (feature.getClass().equals(City.class)) {
            meepleType = "knight";
        } else if (feature.getClass().equals(Monastery.class)) {
            meepleType = "monk";
        } else if (feature.getClass().equals(Road.class)) {
            meepleType = "highwayman";
        } else {
            meepleType = "meeple";
        }

        String stringForToast = gameBoard.getCurrentPlayer().getName() + " has placed a " + meepleType;

        Toast meeplePlaced = new Toast(stringForToast, Toast.Length.LONG, font, backgroundColor, 0.5f, 1f, fontColor, Gdx.graphics.getHeight() / 2f, 10);
        toasts.add(meeplePlaced);
    }

    public void showEmote(EmoteMessage em) {
        emoteMessage = em;
        show_emote = true;
    }

    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void render(float delta) {
        if (gameBoard.tilesLeft() == 0)
            game.setScreen(new GameOverScreen(game, gameBoard.getWinningPlayer()));

        // clear the screen with dark BLUE
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        labelTilesLeft.setText("Tiles LEFT: " + gameBoard.tilesLeft());
        currentPlayerLabel.setText("Current player: " + gameBoard.getCurrentPlayer().getName());

        if (show_emote) {
            emoteManager.showEmoteFromPlayer(emoteMessage);
            show_emote = false;
        }

        stageEmote.act();
        stage.act();
        stageUI.act();
        stage.draw();
        stageUI.draw();
        stageEmote.draw();

        //TODO: figur out if you be able to display a toast without list and iterator

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


    public void dispose() {
        stage.dispose();
        stageUI.dispose();
    }
}