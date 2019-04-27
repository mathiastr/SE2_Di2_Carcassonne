package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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

    private InputMultiplexer multiplexer;
    private Label labelTilesLeft;
    private Label currentPlayerLabel;

    public GameScreen(Game aGame, List<Player> players) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        stageUI = new Stage(new ScreenViewport());
        gameBoard = new GameBoard(stage, stageUI, players);

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
                Gdx.app.debug("drag", "draaaged");
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
        camera.translate(-Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);


        // TODO currently so we can differentiate between board tiles and currentTile.
        // TODO currentTile make it bigger.
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
        currentPlayerLabel.setText("Current player: " + gameBoard.getCurrentPlayer());

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
}
