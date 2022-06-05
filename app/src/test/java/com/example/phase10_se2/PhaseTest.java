package com.example.phase10_se2;



import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PhaseTest {
    private Player player;
    private Phase phaseCheck;
    private Cards card8r;
    private Cards card8g;
    private Cards card7y;
    private Cards card7b;
    private Cards card4r;
    private Cards card4b;
    private Cards card2g;
    private Cards card2y;
    private Cards card10y;
    private Cards card1y;
    private Cards card5y;
    private Cards card12y;
    private Cards card11y;
    private ArrayList<Cards> list;

    @BeforeEach
    public void setup ()
    {
        player = new Player("player1", PlayerColor.BLUE, 1);
        phaseCheck = new Phase();
        card8r = new Cards("RED", 8,null,8);
        card8g = new Cards("GREEN", 8,null,32);
        card7y = new Cards("YELLOW", 7,null,7);
        card7b = new Cards("BLUE", 7,null,31);
        card4r = new Cards("RED", 4,null,4);
        card4b = new Cards("BLUE", 4,null,28);
        card2g = new Cards("GREEN", 2,null,2);
        card2y = new Cards("YELLOW", 2,null,26);
        card10y = new Cards("YELLOW", 10,null,10);
        card1y = new Cards("YELLOW", 1,null,1);
        card5y = new Cards("YELLOW", 5,null,5);
        card12y = new Cards("YELLOW", 12,null,12);
        card11y = new Cards("YELLOW", 11,null,11);
        list = new ArrayList<>();
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsRightAndCardsAreRightAndSorted_ThenReturnTrue ()
    {
        list.add(card2g);
        list.add(card2y);
        list.add(card4b);
        list.add(card4r);
        list.add(card7y);
        list.add(card7b);
        list.add(card8g);
        list.add(card8r);
        assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsRightAndCardsAreRightButUnsorted_ThenReturnTrue ()
    {
        list.add(card2g);
        list.add(card8g);
        list.add(card8r);
        list.add(card7y);
        list.add(card4b);
        list.add(card4r);
        list.add(card2y);
        list.add(card7b);
        assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsWrong_ThenReturnFalse ()
    {
        list.add(card2g);
        assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhaseNumberButItsAWrongNumber_ThenReturnFalse ()
    {
        list.add(card2g);
        player.setPhaseNumber(0);
        assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhase2AndListLengthIsRightAndAllCardsAreOfOneColor_ThenReturnTrue ()
    {
        list.add(card1y);
        list.add(card5y);
        list.add(card10y);
        list.add(card7y);
        list.add(card12y);
        list.add(card2y);
        player.setPhaseNumber(2);
        assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhase2AndListLengthIsRightButNotAllCardsAreOfTheSameColor_ThenReturnFalse ()
    {
        list.add(card1y);
        list.add(card5y);
        list.add(card10y);
        list.add(card7y);
        list.add(card12y);
        list.add(card2g);
        player.setPhaseNumber(2);
        assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhase2AndListLengthIsWrongEvenIfAllCardsAreOfOneColor_ThenReturnFalse ()
    {
        list.add(card1y);
        list.add(card5y);
        list.add(card10y);
        list.add(card7y);
        list.add(card12y);
        list.add(card2y);
        list.add(card11y);
        player.setPhaseNumber(2);
        assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
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
