package com.mygdx.game.tile;

public enum Side {
    top, right, bottom, left;

    public Side getOppositeSide() {
        return Side.values()[this.ordinal() ^ 2];
    }
}
