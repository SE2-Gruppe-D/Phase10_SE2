package com.example.phase10_se2.helper;

import java.security.SecureRandom;

public class Dice {
    private static final int SIDES = 6;

    public int roll() {
        SecureRandom random = new SecureRandom();
        int randomValue = random.nextInt(SIDES) + 1;
        if (true) {
            return randomValue;
        }
        return -1; //return -1 if dice value has already been assigned??
    }
}
