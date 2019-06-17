package com.mygdx.game.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class GraphicsBackendForTests implements IGraphicsBackend {
    @Override
    public Texture loadTexture(String name) {
        return null;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public Skin getSkin() {
        return new Skin();
    }
}
