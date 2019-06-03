package com.mygdx.game.screen;

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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Carcassonne;
import com.mygdx.game.meeple.MeepleTextureFactory;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.GameBoard;
import com.mygdx.game.meeple.MeeplePlacement;
import com.mygdx.game.tile.Road;
import com.mygdx.game.tile.Side;
import com.mygdx.game.actor.TileActor;

import java.util.ArrayList;
import java.util.List;

public class ChosenMeeplePlacementScreen extends Actor implements Screen{

    private Stage stage;
    private GameBoard gb;
    private TileActor newestTile;
    private List<Feature> features;
    private Label output;
    private List<TextButton> meepleButtons;
    private Game game;
    private Screen previousScreen;
    private MeeplePlacement mp;


    public ChosenMeeplePlacementScreen(GameScreen previousScreen, Game game, GameBoard gb) {
        this.gb = gb;
        this.previousScreen = previousScreen;
        this.game = game;
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        stage = new Stage(new ScreenViewport());
        newestTile = gb.getNewestTile(); //current Tile for Placing Meeple
        features = newestTile.getFeatures();
        meepleButtons = new ArrayList<>();
        mp = new MeeplePlacement(gb, previousScreen, new MeepleTextureFactory());

        output = new Label("Where do you want to place your Meeple?", Carcassonne.skin);
        output.setAlignment(Align.center);
        output.setY(Gdx.graphics.getHeight()/8f*7f);
        output.setWidth(Gdx.graphics.getWidth());
        output.setFontScale(3);
        stage.addActor(output);

        newestTile.updateTileFeatures();
        for(Feature feature : newestTile.getFeatures())
        {
            if(!feature.hasMeepleOnIt())
            {
                meepleButtons.add(createMeeplePlacementButton(feature));
            }
        }

        Gdx.input.setInputProcessor(stage);
        setMeepleTextButtons();

        TextButton back = new TextButton("Back", Carcassonne.skin);
        back.setWidth(Gdx.graphics.getWidth() / 5f - 40f);
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
    }

    private TextButton createMeeplePlacementButton(Feature feature){
        TextButton placeMeepleButton = new TextButton("On " + feature.getClass().getSimpleName() + " " +feature.getSides(), Carcassonne.skin);
        placeMeepleButton.setWidth(Gdx.graphics.getWidth() / 8f);
        placeMeepleButton.setHeight(Gdx.graphics.getHeight() / 8f);

        placeMeepleButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ArrayList<Side> sides;
                Side side = null;
                if(!newestTile.isMonastery() ) {
                    sides = feature.getSides();
                    side = sides.get(0);
                    newestTile = gb.getNewestTile();
                    side = newestTile.getSideAfterRotation(side);}
                else {
                    for(Feature f : features){
                        if (f.equals(Road.class)){
                            sides = feature.getSides();
                            side = sides.get(0);
                            side = newestTile.getSideAfterRotation(side);
                        }
                        else {
                            side = Side.TOP;
                        }
                    }

                }
                mp.placeMeeple(side, feature, gb.getCurrentTile().getPosition());

                game.setScreen(previousScreen);
            }
        });

        return placeMeepleButton;
    }

    private void setMeepleTextButtons() {
        for (int i = 0; i < meepleButtons.size() && i < 6; i++) {
            TextButton button = meepleButtons.get(i);
            button.setWidth(Gdx.graphics.getWidth() / 2f - 40f);
            button.setHeight(Gdx.graphics.getHeight()/5f - 40f);
            if(i%2 == 0){
                button.setPosition(20, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() /5f * (i/2f+2) + 40f);
            }else{
                button.setPosition(20f + Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() /5f * (i/2f+2) + 40f);
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
