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

    //zwei Farben pro Karte
    @Test
    public void testCardsColor(){
        int colorBlueCount = 0;
        int number10Blue = 0;
        int colorRedCount=0;
        int number7Red=0;
        int colorYellowCount=0;
        int number12Yellow=0;
        int colorGreenCount=0;
        int number1Green=0;

        for(int i =0; i<cardList.size();i++){
            if(cardList.get(i).getColor().equals("blue")){
                colorBlueCount++;
                if(cardList.get(i).getValue()==10){
                    number10Blue++;
                }
            }
            if(cardList.get(i).getColor().equals("red")){
                colorRedCount++;
                if(cardList.get(i).getValue()==7){
                    number7Red++;
                }
            }
            if(cardList.get(i).getColor().equals("yellow")){
                colorYellowCount++;
                if(cardList.get(i).getValue()==12){
                    number12Yellow++;
                }
            }
            if(cardList.get(i).getColor().equals("green")){
                colorGreenCount++;
                if(cardList.get(i).getValue()==1){
                    number1Green++;
                }
            }
        }
        //Chek color
        Assert.assertEquals(24, colorBlueCount);
        Assert.assertEquals(24, colorRedCount);
        Assert.assertEquals(24, colorYellowCount);
        Assert.assertEquals(24, colorGreenCount);
        //Check values
        Assert.assertEquals(2, number10Blue);
        Assert.assertEquals(2, number7Red);
        Assert.assertEquals(2, number12Yellow);
        Assert.assertEquals(2, number1Green);
    }

    //Kartenstapel leer -->mischen
    @Test
    public void testEmptyCardsList(){
        Cards cards1= new Cards("blue", 11, null, 1);
        Cards cards2= new Cards("red", 10, null, 2);
        Cards cards3= new Cards("green", 9, null, 3);
        cardDrawer.getDiscardpileList().add(cards1);
        cardDrawer.getDiscardpileList().add(cards2);
        cardDrawer.getDiscardpileList().add(cards3);
        cardList.removeAll(cardList);
        cardDrawer.isInitialCardsEmpty();
        ArrayList<Cards> newList= new ArrayList<>();
        newList.add(cards1);
        newList.add(cards2);
        newList.add(cards3);
        Assert.assertTrue(cardDrawer.getInitialCardsList().containsAll(newList));
    }
}
