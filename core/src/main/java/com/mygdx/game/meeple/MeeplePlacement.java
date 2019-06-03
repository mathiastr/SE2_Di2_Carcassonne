package com.mygdx.game.meeple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.actor.TileActor;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.tile.Feature;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Position;
import com.mygdx.game.tile.Side;

import java.util.logging.Logger;


public class MeeplePlacement {
    private static final Logger LOGGER = Logger.getLogger(TileActor.class.getSimpleName());

    private GameBoard gb;


    public MeeplePlacement(GameBoard gb) {
        this.gb = gb;

    }


    public boolean placeMeeple(Side side, Feature feature, Position pos) {
        try {
            Meeple meepleForPlacement = gb.getCurrentPlayer().getUnusedMeeple();
            meepleForPlacement.setSide(side);
            meepleForPlacement.setFeature(feature);
            gb.getCurrentTile().addMeeple(meepleForPlacement);
            feature.setHasMeepleOnIt(true);
            drawMeeple(side, pos);
            GameScreen.placeMeeple.setVisible(false);
        } catch (Exception e) {
            LOGGER.warning("Exception");
        }
        return true;
    }



    public void drawMeeple(Side side, Position pos) {

        GameBoard.Color color = gb.getCurrentPlayer().getColor();
        Texture meepleTexture;

        switch (color) {
            case red:
                meepleTexture = new Texture(Gdx.files.internal("redmeeple.png"));
                break;
            case blue:
                meepleTexture = new Texture(Gdx.files.internal("bluemeeple.png"));
                break;
            case grey:
                meepleTexture = new Texture(Gdx.files.internal("greymeeple.png"));
                break;
            case black:
                meepleTexture = new Texture(Gdx.files.internal("blackmeeple.png"));
                break;
            case green:
                meepleTexture = new Texture(Gdx.files.internal("greenmeeple.png"));
                break;
            case yellow:
                meepleTexture = new Texture(Gdx.files.internal("yellowmeeple.png"));
                break;
            default:
                meepleTexture = new Texture(Gdx.files.internal("greenmeeple.png"));
                break;
        }


        ImageButton meepleImg = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(meepleTexture)),
                new TextureRegionDrawable(new TextureRegion(meepleTexture)));
        meepleImg.setSize(Gdx.graphics.getWidth() / 18f, Gdx.graphics.getHeight() / 18f);
        float x = (pos.getX() * 128f) + (128f / 2f) - (meepleImg.getWidth() / 2f);
        float y = (pos.getY() * 128f) + (128f / 2f) - (meepleImg.getHeight() / 2f);

        switch (side) {
            case TOP:
                y += 42f;
                break;
            case LEFT:
                x -= 42f;
                break;
            case RIGHT:
                x += 42f;
                break;
            case BOTTOM:
                y -= 42f;
                break;
        }

        meepleImg.setPosition(x, y);


        for (int i = 0; i < gb.getPlayers().size()
                ; i++) {
            gb.getPlayerActorList().get(i).update();
        }

        gb.getStageOfBoard().addActor(meepleImg);


    }
}