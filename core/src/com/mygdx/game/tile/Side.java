package com.mygdx.game.tile;

public enum Side {
    top, right, bottom, left;

    /**
     * Gets the opposite side of this one.
     * @return the opposite side of this one.
     */
    public Side getOppositeSide() {
        return Side.values()[this.ordinal() ^ 2];
    }
}
