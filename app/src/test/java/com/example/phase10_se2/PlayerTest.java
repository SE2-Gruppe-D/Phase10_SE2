package com.example.phase10_se2;

import org.junit.jupiter.api.*;
import android.widget.ImageView;
import java.util.ArrayList;


public class PlayerTest {
    Player player;
    Cards card1;
    Cards card2;
    Cards card3;
    Cards cards1;
    ArrayList<Cards> cards;

    @BeforeEach
    public void init() {
        player = new Player("Test-Player", PlayerColor.GREEN, 0,0);
        card1 = new Cards("blue",7,null,7);
        card2 = new Cards("yellow",5,null,53);
        card3 = new Cards("green",11,null,83);
        cards1= new Cards("blue", 11, null, 1);
        cards = new ArrayList<Cards>();

    }

    @Test
    public void testMove_simple1() {
        if(player != null) {
            player.move(5);
            Assertions.assertEquals(5, player.getCurrentPosition());
            Assertions.assertEquals(3, player.getPositionX());
            Assertions.assertEquals(2, player.getPositionY());
        }
    }

    @Test
    public void testMove_simple2() {
        if(player != null) {
            player.move(13);
            Assertions.assertEquals(13, player.getCurrentPosition());
            Assertions.assertEquals(0, player.getPositionX());
            Assertions.assertEquals(3, player.getPositionY());
        }
    }

    @Test
    public void testMove_roundFinished() {
        if(player != null) {
            player.move(18);
            Assertions.assertEquals(2, player.getCurrentPosition());
            Assertions.assertEquals(2, player.getPositionX());
            Assertions.assertEquals(0, player.getPositionY());
        }
    }


    @Test
    public void ifTwoOneDigitCardsAreLeft_ThenMinusPointsShouldBe10(){
                cards.add(card1);
                cards.add(card2);
                player.updateMinusPoints(cards);
                Assertions.assertEquals(10, player.getMinusPoints());
    }

    @Test
    public void ifOneTwoDigitAndOneOneDigitCardsAreLeft_ThenMinusPointsShouldBe15(){
            cards.add(card1);
            cards.add(card3);
            player.updateMinusPoints(cards);
            Assertions.assertEquals(15, player.getMinusPoints());
    }

    @Test
    public void ifPhaseNumberIs10_ThenAssignTheRightText() {
        player.setPhaseNumber(10);
        player.setPhaseText();
        Assertions.assertEquals("1 Fünfling + 1 Dreierfolge einer Farbe", player.getPhaseText());
    }
}
