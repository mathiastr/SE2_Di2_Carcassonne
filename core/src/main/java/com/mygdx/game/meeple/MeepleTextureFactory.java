package com.mygdx.game.meeple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GameBoard;

public class MeepleTextureFactory {
    private Texture createMeepleTexture(GameBoard.Color color) {
        switch (color) {
            case RED:
                return new Texture(Gdx.files.internal("redmeeple.png"));
            case BLUE:
                return new Texture(Gdx.files.internal("bluemeeple.png"));
            case GREY:
                return new Texture(Gdx.files.internal("greymeeple.png"));
            case BLACK:
                return new Texture(Gdx.files.internal("blackmeeple.png"));
            case GREEN:
                return new Texture(Gdx.files.internal("greenmeeple.png"));
            case YELLOW:
                return new Texture(Gdx.files.internal("yellowmeeple.png"));
            default:
                return new Texture(Gdx.files.internal("greenmeeple.png"));
        }
    }

    public ImageButton createMeepleImage(GameBoard.Color color) {
        Texture meepleTexture = createMeepleTexture(color);
        ImageButton imageButton = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(meepleTexture)),
                new TextureRegionDrawable(new TextureRegion(meepleTexture)));
        imageButton.setSize(Gdx.graphics.getWidth() / 18f, Gdx.graphics.getHeight() / 18f);
        return imageButton;
    }
}
