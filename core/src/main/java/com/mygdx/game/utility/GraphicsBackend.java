package com.mygdx.game.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Carcassonne;

public class GraphicsBackend implements IGraphicsBackend {


    @Override
    public Texture loadTexture(String name) {
        return new Texture(Gdx.files.internal(name));
    }

    @Override
    public int getWidth() {
        return Gdx.graphics.getWidth();
    }

    @Override
    public int getHeight() {
        return Gdx.graphics.getHeight();
    }

    @Override
    public Skin getSkin() {
        return Carcassonne.skin;
    }

}
