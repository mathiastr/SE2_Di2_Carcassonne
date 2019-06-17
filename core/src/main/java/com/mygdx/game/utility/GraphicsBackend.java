package com.mygdx.game.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GraphicsBackend implements IGraphicsBackend {


    @Override
    public Texture loadTexture(String name) {
        return new Texture(Gdx.files.internal(name));
    }
}
