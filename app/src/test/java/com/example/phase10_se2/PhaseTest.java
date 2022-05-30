package com.example.phase10_se2;



import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PhaseTest {
    private Player player;
    private Cards card8r;
    private Cards card8g;
    private Cards card7y;
    private Cards card7b;
    private Cards card4r;
    private Cards card4b;
    private Cards card2g;
    private Cards card2y;
    private List<Cards> list;

    @BeforeEach
    public void setup ()
    {

    }


    /*private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card card5;
    private Card card6;
    private Card card7;
    private Card card8;
    private Card card9;
    private Card card10;
    private List<Card> list1;
    private List<Card> list2;
    private List<Card> list3;
    private List<Card> list4;
    private Phase phase;



    @Test
    public void testCheckPhase1True(){
        phase = new Phase(card1);
        card1=new Card("red", 4);
        card2=new Card("green", 4);
        list1 = new ArrayList<>();
        list1.add(card1);
        list1.add(card2);
        card3=new Card("red", 5);
        card4=new Card("green", 5);
        list2 = new ArrayList<>();
        list2.add(card3);
        list2.add(card4);
        card5=new Card("red", 6);
        card6=new Card("green", 6);
        list3 = new ArrayList<>();
        list3.add(card5);
        list3.add(card6);
        card7=new Card("red", 7);
        card8=new Card("green", 7);
        list4 = new ArrayList<>();
        list4.add(card7);
        list4.add(card8);
        assertTrue(phase.checkPhase1(list1,list2,list3,list4));
    }

    @Test
    public void testCheckPhase1False(){
        phase = new Phase(card1);
        card1=new Card("red", 4);
        card2=new Card("green", 4);
        list1 = new ArrayList<>();
        list1.add(card1);
        list1.add(card2);
        card3=new Card("red", 5);
        card4=new Card("green", 5);
        list2 = new ArrayList<>();
        list2.add(card3);
        list2.add(card4);
        card5=new Card("red", 6);
        card6=new Card("green", 9);
        list3 = new ArrayList<>();
        list3.add(card5);
        list3.add(card6);
        card7=new Card("red", 7);
        card8=new Card("green", 7);
        list4 = new ArrayList<>();
        list4.add(card7);
        list4.add(card8);
        assertFalse(phase.checkPhase1(list1,list2,list3,list4));
    }

     */

}
