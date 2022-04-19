package com.example.phase10_se2;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class PlayerTest {
    Player player;

    @Before
    public void init() {
        player = new Player("Test-Player", PlayerColor.GREEN);
    }

    @Test
    public void testMove_simple1() {
        player.move(5);
        assertEquals(5, player.getCurrentPosition());
        assertEquals(4, player.getPositionX());
        assertEquals(1, player.getPositionY());
    }

    @Test
    public void testMove_simple2() {
        player.move(13);
        assertEquals(13, player.getCurrentPosition());
        assertEquals(0, player.getPositionX());
        assertEquals(3, player.getPositionY());
    }

    @Test
    public void testMove_roundFinished() {
        player.move(18);
        assertEquals(2, player.getCurrentPosition());
        assertEquals(2, player.getPositionX());
        assertEquals(0, player.getPositionY());
    }
}
