package com.example.phase10_se2;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class DiceTest {
    Dice dice;

    @Before
    public void init() {
        dice = new Dice();
    }


    @Test
    public void testRoll() {
        ArrayList<Integer> possibleValues = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertTrue(possibleValues.contains(dice.roll()));
    }
}
