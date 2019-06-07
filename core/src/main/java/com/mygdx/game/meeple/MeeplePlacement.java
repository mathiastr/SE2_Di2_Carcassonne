package com.mygdx.game.meeple;


import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
    private GameScreen gameScreen;
    private MeepleTextureFactory textureFactory;
    private Feature featureForMT = null;


    public MeeplePlacement(GameBoard gb, GameScreen gameScreen, MeepleTextureFactory textureFactory) {
        this.gb = gb;
        this.gameScreen = gameScreen;
        this.textureFactory = textureFactory;
    }


    public void placeMeeple(Side side, Feature feature, Position pos) {
        try {
            featureForMT = feature;
            Meeple meepleForPlacement = gb.getUnusedCurrentPlayerMeeple();
            meepleForPlacement.setSide(side);
            meepleForPlacement.setFeature(feature);
            gb.addMeepleOnCurrentTile(meepleForPlacement);
            feature.setHasMeepleOnIt(true);
            drawMeeple(side, pos);
            gameScreen.placeMeeple.setVisible(false);
        } catch (NullPointerException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.warning("Exception");
        }
    }



    public void drawMeeple(Side side, Position pos) {

        GameBoard.Color color = gb.getCurrentPlayer().getColor();

        ImageButton meepleImg = textureFactory.createMeepleImage(color);

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


        for (int i = 0; i < gb.getPlayers().size(); i++) {
            gb.getPlayerActor(i).updateInfo();
        }

        gb.addActorToBoardStage(meepleImg);

        gameScreen.createMeepleIsPlacedToast(featureForMT);
    }
}