package com.mygdx.game;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.City;
import com.mygdx.game.Feature;
import com.mygdx.game.Field;
import com.mygdx.game.GameBoard;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Road;
import com.mygdx.game.TileActor;
import com.mygdx.game.network.GameServer;
import com.mygdx.game.network.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

public class ChosenMeeplePlacementScreen extends Actor implements Screen{

    private Stage stage;
    private Skin skin;
    private Stage stageUi;
    GameBoard gb;
    TileActor currentTile;
    private List<Feature> features;
    private Label output;
    private List<TextButton> meepleButtons;
    private Game game;
    private Screen previousScreen;

    public  ChosenMeeplePlacementScreen(Screen previousScreen, Game game, GameBoard gb) {
        this.gb = gb;
        this.previousScreen = previousScreen;
        this.game = game;
        stage = new Stage(new ScreenViewport());
        stageUi = new Stage(new ScreenViewport());
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        currentTile = gb.getCurrentTile();
        features = currentTile.getFeatures();
        meepleButtons = new ArrayList<TextButton>();



        output = new Label("What do you want to do?", Carcassonne.skin);
        output.setAlignment(Align.center);
        output.setY(Gdx.graphics.getHeight()/8*7);
        output.setWidth(Gdx.graphics.getWidth());
        output.setFontScale(3);
        stage.addActor(output);

        for (Feature feature: features) {
            meepleButtons.add(createMeeplePlacementButton(feature));
        }

        Gdx.input.setInputProcessor(stage);
        setMeepleTextButtons();

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
                game.setScreen(previousScreen);
            }
        });
        stage.addActor(back);
        //Gdx.input.setInputProcessor(stage);

    }

    public TextButton createMeeplePlacementButton(Feature feature)
    {
        TextButton placeMeepleButton = new TextButton("Place Meeple on " + feature.getClass().getSimpleName(), Carcassonne.skin);

        placeMeepleButton.setWidth(Gdx.graphics.getWidth() / 8);
        placeMeepleButton.setHeight(Gdx.graphics.getHeight() / 8);

        placeMeepleButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (gb.getCurrentPlayer().checkIfMeepleAvailable() == true) {
                    feature.addMeeple();

                }
            }
        });

        return placeMeepleButton;
    }

    private void setMeepleTextButtons() {
        for (int i = 0; i < meepleButtons.size() && i < 6; i++) {
            TextButton button = meepleButtons.get(i);
            button.setWidth(Gdx.graphics.getWidth() / 2 - 40);
            button.setHeight(Gdx.graphics.getHeight()/5 - 40);
            if(i%2 == 0){
                button.setPosition(20, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() /5 * (i/2+2) + 40);
            }else{
                button.setPosition(20 + Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() /5 * (i/2+2) + 40);
            }
            stage.addActor(button);
        }
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

    }
}