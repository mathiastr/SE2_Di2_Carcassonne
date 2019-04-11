package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AddTileActor extends TileActor {
    public AddTileActor(Position aPosition, final GameBoard gameBoard) {
        super(aPosition, gameBoard);
        setTexture(new Texture(Gdx.files.internal("addtile.png")));

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                AddTileActor clicked = (AddTileActor)event.getListenerActor();
                Gdx.app.log("hmm", "clicked tileActor");

                // TODO pass a specific tile here
                TileActor newTile = gameBoard.replaceTileAt(clicked);
                gameBoard.addSurroundingAddTiles(newTile);
            }
        });
    }
}
