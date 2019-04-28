package com.mygdx.game.network.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.City;
import com.mygdx.game.Feature;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Road;
import com.mygdx.game.TileActor;

import java.util.ArrayList;
import java.util.List;

public class ChooseMeeplePlacementActor extends Actor implements Screen {
    //TODO: Ist implements Screen notwendig?

    Stage stage;
    Skin skin;
    Game game;
    Stage stageUi;
    GameBoard gb;
    TileActor currentTile;
    private List<Feature> features = new ArrayList<>();
    private Texture texture;

    public void set() {
        gb = TileActor.getGameboard();
        currentTile = gb.getCurrentTile();
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        features = currentTile.getFeatures();

        Button placeMonk = new TextButton("place Monk", skin, "small");
        placeMonk.setWidth(Gdx.graphics.getWidth() / 8);
        placeMonk.setHeight(Gdx.graphics.getHeight() / 8);
        //TODO: Button Position ändern!
        placeMonk.setPosition(Gdx.graphics.getWidth() / 2 - placeMonk.getWidth() / 2, Gdx.graphics.getHeight() * 6 / 9);
        //TODO: Transparenz auf Null
        placeMonk.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                placeMeepleOnMonastry();
            }
        });

        Button placeHighwayman = new TextButton("place Highwayman", skin, "small");
        placeHighwayman.setWidth(Gdx.graphics.getWidth() / 8);
        placeHighwayman.setHeight(Gdx.graphics.getHeight() / 8);
        //TODO: Button Position ändern!
        placeHighwayman.setPosition(Gdx.graphics.getWidth() / 2 - placeHighwayman.getWidth() / 2, Gdx.graphics.getHeight() * 6 / 9);
        //TODO: Transparenz auf Null
        placeHighwayman.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                placeMeepleOnRoad();
            }
        });

        Button placeFarmer = new TextButton("place Farmer", skin, "small");
        placeFarmer.setWidth(Gdx.graphics.getWidth() / 8);
        placeFarmer.setHeight(Gdx.graphics.getHeight() / 8);
        //TODO: Button Position ändern!
        placeFarmer.setPosition(Gdx.graphics.getWidth() / 2 - placeFarmer.getWidth() / 2, Gdx.graphics.getHeight() * 6 / 9);
        //TODO: Transparenz auf Null
        placeFarmer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                placeMeepleOnField();
            }
        });

        Button placeKnight = new TextButton("place Knight", skin, "small");
        placeKnight.setWidth(Gdx.graphics.getWidth() / 8);
        placeKnight.setHeight(Gdx.graphics.getHeight() / 8);
        //TODO: Button Position ändern!
        placeKnight.setPosition(Gdx.graphics.getWidth() / 2 - placeKnight.getWidth() / 2, Gdx.graphics.getHeight() * 6 / 9);
        //TODO: Transparenz auf Null
        placeKnight.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                placeMeepleOnCity();
            }
        });
    }

    public void placeMeepleOnMonastry() {
        for (int i = 0; i < features.size(); ++i) {
            for (Feature f : features) {
                if (f.equals(City.class) && currentTile.isMonastery() == true) {
                    //TODO: Transparenz auf 100
                    dispose();
                }
            }
        }
    }

    public void placeMeepleOnRoad() {
        for (int i = 0; i < features.size(); ++i) {
            for (Feature f : features) {
                if (f.equals(Road.class)) {
                    //TODO: Transparenz auf 100
                    dispose();
                }
            }
        }
    }


    public void placeMeepleOnField() {
        for (int i = 0; i < features.size(); ++i) {
            for (Feature f : features) {
                if (f.equals(Road.class)) {
                    //TODO: Transparenz auf 100
                    dispose();

                }
            }
        }
    }

    public void placeMeepleOnCity() {
        for (int i = 0; i < features.size(); ++i) {
            for (Feature f : features) {
                if (f.equals(City.class)) {
                    //TODO: Transparenz auf 100
                    dispose();
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //TODO: Richtig überschreiben
        super.draw(batch, parentAlpha);
        //batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1);
        //batch.draw(texture, getX(), getY());
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
