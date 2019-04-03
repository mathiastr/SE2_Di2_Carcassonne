package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Carcassonne extends Game {

    static public Skin skin;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        this.setScreen(new MainMenuScreen(this));
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug("touch", "tezfd");
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
