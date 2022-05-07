package com.example.phase10_se2;

import static org.junit.Assert.assertEquals;

import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class PlayerTest {
    Player player;
    Cards card1;
    Cards card2;
    Cards card3;
    ArrayList<Cards> cards;
    ImageView a;

    @Before
    public void init() {
        player = new Player("Test-Player", PlayerColor.GREEN, 0,0);
        Cards card1 = new Cards("blue",7,a,7);
        Cards card2 = new Cards("yellow",5,a,5+48);
        Cards card3 = new Cards("green",11,a,11+72);

    }

    @Test
    public void testMove_simple1() {
        if(player != null) {
            player.move(5);
            assertEquals(5, player.getCurrentPosition());
            assertEquals(3, player.getPositionX());
            assertEquals(2, player.getPositionY());
        }
    }

    @Test
    public void testMove_simple2() {
        if(player != null) {
            player.move(13);
            assertEquals(13, player.getCurrentPosition());
            assertEquals(0, player.getPositionX());
            assertEquals(3, player.getPositionY());
        }
    }

    @Test
    public void testMove_roundFinished() {
        if(player != null) {
            player.move(18);
            assertEquals(2, player.getCurrentPosition());
            assertEquals(2, player.getPositionX());
            assertEquals(0, player.getPositionY());
        }
    }

    @Test
    public void ifTwoOneDigitCardsAreLeft_ThenMinusPointsShouldBe10(){
            if (player != null) {
                cards = new ArrayList<Cards>();
                cards.add(card1);
                cards.add(card2);
                player.updateMinusPoints(cards);
                assertEquals(10, player.getMinusPoints());
            }
    }

    @Test
    public void ifOneTwoDigitAndOneOneDigitCardsAreLeft_ThenMinusPointsShouldBe10(){
        if (player != null) {
            cards = new ArrayList<Cards>();
            cards.add(card1);
            cards.add(card3);
            player.updateMinusPoints(cards);
            assertEquals(15, player.getMinusPoints());
        }
    }


}