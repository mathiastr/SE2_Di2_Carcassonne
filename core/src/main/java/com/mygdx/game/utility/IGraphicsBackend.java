package com.mygdx.game.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface IGraphicsBackend {

    Texture loadTexture(String name);

    int getWidth();

    int getHeight();

    Skin getSkin();

}
