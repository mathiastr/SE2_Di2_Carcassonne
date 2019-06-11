package com.mygdx.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testAddScore() {
        Player player = new Player();
        player.addScore(5);
        player.addScore(2);
        player.addScore(7);
        assertEquals(player.getScore(), 5 + 2 + 7);
    }
}