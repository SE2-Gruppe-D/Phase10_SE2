package com.example.phase10_se2;

import org.junit.jupiter.api.*;

public class GameTest {
    Game game;

    @BeforeEach
    public void init() {
        game = new Game("Test-ID");
    }

    @Test
    public void testPlayerJoined() {
        Assertions.assertEquals(0, game.getPlayers().size());
        game.playerJoined(new Player());
        game.playerJoined(new Player());
        Assertions.assertEquals(2, game.getPlayers().size());
    }
}
