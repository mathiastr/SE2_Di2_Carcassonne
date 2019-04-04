package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Tile {
    private static int size = 128;
    private Rectangle rect;
    private Tile tiles[] = new Tile[4];
    private Texture texture;

    public Tile(int x, int y, Texture t) {
        rect = new Rectangle();
        texture = t;
        rect.x = x;
        rect.y = y;
        rect.width = size;
        rect.height = size;
    }

    public Tile(Tile extendedTile, int addedToPos, Texture t) throws Exception {
        if (extendedTile.tiles[addedToPos] != null) throw new Exception("tile already in this place");

        int placement[] = {2, 3, 0, 1};
        int pos_dx[][] = {{0, size}, {size, 0}, {0, -size}, {-size, 0}};

        texture = t;
        rect = new Rectangle();
        rect.x = extendedTile.rect.x + pos_dx[addedToPos][0];
        rect.y = extendedTile.rect.y + pos_dx[addedToPos][1];
        rect.width = size;
        rect.height = size;

        extendedTile.tiles[addedToPos] = this;
        tiles[placement[addedToPos]] = extendedTile;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Rectangle getRect() {
        return rect;
    }

    public Tile[] getTiles() {
        return tiles;
    }
}
