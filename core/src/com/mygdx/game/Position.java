package com.mygdx.game;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position() {

    }

    public Position add(Position other) {
        return new Position(x+other.x, y+other.y);
    }

    public Position[] getSurroundingPositions() {
        return new Position[] {new Position(x, y+1),
                new Position(x+1, y),
                new Position(x, y-1),
                new Position(x-1, y)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + " " + y + ")";
    }
}
