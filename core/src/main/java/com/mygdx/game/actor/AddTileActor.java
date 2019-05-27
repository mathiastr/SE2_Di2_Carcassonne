package com.mygdx.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameBoard;
import com.mygdx.game.Position;

public class AddTileActor extends TileActor {
    private GameBoard gameBoard;
    public AddTileActor(Position aPosition, final GameBoard gb) {
        super(aPosition);
        gameBoard = gb;
        setTexture(new Texture(Gdx.files.internal("addtile3.png")));

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                AddTileActor clicked = (AddTileActor)event.getListenerActor();

                gameBoard.placeMyTile(clicked.getPosition());
            }
        });
    }
}
