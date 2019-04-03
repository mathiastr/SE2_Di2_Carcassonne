package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

// TODO: add the current Tile view (first add UI stage)
// TODO: add Players and turnbased game (also add the playerUIs with scores...)
// TODO: add algorithmic for scoring
// TODO: "Home-Button": goes back to baseTile and resets zoom.

public class GameScreen implements Screen {
    private Game game;
    private Stage stage;
    private Texture tileTexture;
    private Texture addTexture;
    private OrthographicCamera camera;
    private GameBoard gameBoard;

    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        gameBoard = new GameBoard(stage);

        tileTexture = new Texture(Gdx.files.internal("tile.png"));
        addTexture = new Texture(Gdx.files.internal("addtile.png"));

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
