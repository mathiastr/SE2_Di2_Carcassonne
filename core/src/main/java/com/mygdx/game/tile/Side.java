package com.mygdx.game.tile;

public enum Side {
    TOP, RIGHT, BOTTOM, LEFT;

    public Side getOppositeSide() {
        return Side.values()[this.ordinal() ^ 2];
    }
}

