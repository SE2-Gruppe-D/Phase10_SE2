package com.example.phase10_se2;


import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;


public class DiceTest {
    Dice dice;

    @BeforeEach
    public void init() {
        dice = new Dice();
    }


    @Test
    public void testRoll() {
        ArrayList<Integer> possibleValues = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Assertions.assertTrue(possibleValues.contains(dice.roll()));
    }
}
