package com.example.phase10_se2;

import android.util.Log;

import java.util.Random;

public class Dice {
    private static final int sides = 6;

    protected int roll() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis()); //to assure randomness
        int randomValue = random.nextInt(sides) + 1;

        //TODO: only set new value, if none has been assigned yet
        if (true) {
            Log.i("DiceActivity", "dice value set");
            return randomValue;
        }
        return -1; //return -1 if dice value has already been assigned
    }
}
