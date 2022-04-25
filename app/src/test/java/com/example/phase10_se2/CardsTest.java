package com.example.phase10_se2;

import android.graphics.Color;
import android.media.Image;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CardsTest {
    private CardDrawer cardDrawer;
    private ArrayList<Cards> cardList=new ArrayList<>();

    @Before
    public void init(){
        cardDrawer=new CardDrawer();
        cardList=cardDrawer.generateInitialCards();
    }

    @After
    public void tearDown(){
        cardDrawer=null;
    }

    //95 Karten
    @Test
    public void testNumberOfCards(){
        Assert.assertEquals(96, cardList.size());
    }

}
