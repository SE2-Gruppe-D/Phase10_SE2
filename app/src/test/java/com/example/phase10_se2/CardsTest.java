package com.example.phase10_se2;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CardsTest {
    Playfield playfield;
    ArrayList<Cards> cardlist= new ArrayList<>(95);

    @Before
    public void setup(){
        playfield= new Playfield();

        Cards cards1= new Cards("blue", 1, null, 1);
        Cards cards2= new Cards("red", 1, null, 2);
        Cards cards3= new Cards("yellow", 1, null, 3);
        Cards cards4= new Cards("green", 1, null, 4);
        Cards cards5= new Cards("green", 1, null, 5);

        cardlist.add(cards1);
        cardlist.add(cards2);
        cardlist.add(cards3);
        cardlist.add(cards4);
        cardlist.add(cards5);
    }

    @After
    public void tearDown(){
        playfield= null;
    }

    @Test
    public void testNumberMaxTwice(){
        Cards testblue= new Cards("blue", 1, null, 1);
        cardlist.add(testblue);
        //Assert.assertTrue();
    }


}
