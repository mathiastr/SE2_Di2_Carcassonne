package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class MeeplePlacement {
    ArrayList<TileActor> usedTiles;
    GameBoard gb;

    public MeeplePlacement(GameBoard gb){
        this.gb = gb;
        usedTiles = gb.getUsedTiles();
    }

    public void placeMeeple(Side side){

        Texture meepleTexture= new Texture(Gdx.files.internal("redmeeple.png"));
        Position pos = gb.getPreviousTile().getPosition();
        ImageButton meepleImg = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(meepleTexture)),
                new TextureRegionDrawable(new TextureRegion(meepleTexture)));
        meepleImg.setSize(Gdx.graphics.getWidth()/18f,Gdx.graphics.getHeight()/18f);
        float x = (pos.getX()*128f)+(128f/2f)-(meepleImg.getWidth()/2f);
        float y = (pos.getY()*128f)+(128f/2f)-(meepleImg.getHeight()/2f);

        if (side == Side.top){
            y += 42f;
        } else if (side == Side.left){
            x -= 42f;
        } else if (side == Side.right){
            x += 42f;
        } else if (side == Side.bottom){
            y -= 42f;
        }

        /*
        switch (side) {
            case top:
                y += 42f;
                break;
            case left:
                x -= 42f;
                break;
            case right:
                x += 42f;
                break;
            case bottom:
                y -= 42f;
                break;
        }
        */

        meepleImg.setPosition(x, y);
        gb.getStageOfBoard().addActor(meepleImg);
    }
}
