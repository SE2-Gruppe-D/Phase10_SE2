package com.example.phase10_se2;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class GameTest {
    Game game;

    @Before
    public void init() {
        game = new Game("Test-ID");
    }

    @Test
    public void testPlayerJoined() {
        assertEquals(0, game.getPlayers().size());
        game.playerJoined(new Player());
        game.playerJoined(new Player());
        assertEquals(2, game.getPlayers().size());
    }
}
