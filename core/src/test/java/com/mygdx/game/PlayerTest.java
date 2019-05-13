package com.mygdx.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testAddScore() {
        Player p = new Player();
        p.addScore(5);
        p.addScore(2);
        p.addScore(7);
        assertEquals(p.getScore(), 5 + 2 + 7);
    }
}