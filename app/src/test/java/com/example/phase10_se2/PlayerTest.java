package com.example.phase10_se2;

import org.junit.jupiter.api.*;

import com.example.phase10_se2.ENUM.PlayerColor;
import com.example.phase10_se2.ENUM.PlayerState;

import java.util.ArrayList;


public class PlayerTest {
    Player player;
    Player playerCon1;
    Player playerCon3;
    Player playerCon4;

    Cards card1;
    Cards card2;
    Cards card3;
    Cards cards1;
    ArrayList<Cards> cards;

    @BeforeEach
    public void init() {
        player = new Player("Test-Player", null, 0,0);
        card1 = new Cards("blue",7,null,7);
        card2 = new Cards("yellow",5,null,53);
        card3 = new Cards("green",11,null,83);
        cards1= new Cards("blue", 11, null, 1);
        cards = new ArrayList<Cards>();
        playerCon1 = new Player("TesterCon1",null, null,1,0,null,null);
        playerCon3 = new Player("TesterCon3", null,1);
        playerCon4 = new Player("TesterCon4", null);

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
            player.setCurrentPosition(5);
            player.move(4);
            Assertions.assertEquals(9, player.getCurrentPosition());
            Assertions.assertEquals(2, player.getPositionX());
            Assertions.assertEquals(5, player.getPositionY());
        }
    }

    @Test
    public void testMove_simple3() {
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
        player.setPhaseNumber(1);
        player.setPhaseText();
        Assertions.assertEquals("4 Zwillinge", player.getPhaseText());
        player.setPhaseNumber(2);
        player.setPhaseText();
        Assertions.assertEquals("6 Karten einer Farbe", player.getPhaseText());
        player.setPhaseNumber(3);
        player.setPhaseText();
        Assertions.assertEquals("1 Vierling + 1 Viererfolge", player.getPhaseText());
        player.setPhaseNumber(4);
        player.setPhaseText();
        Assertions.assertEquals("1 Achterfolge", player.getPhaseText());
        player.setPhaseNumber(5);
        player.setPhaseText();
        Assertions.assertEquals("7 Karten einer Farbe", player.getPhaseText());
        player.setPhaseNumber(6);
        player.setPhaseText();
        Assertions.assertEquals("1 Neunerfolge", player.getPhaseText());
        player.setPhaseNumber(7);
        player.setPhaseText();
        Assertions.assertEquals("2 Vierlinge", player.getPhaseText());
        player.setPhaseNumber(8);
        player.setPhaseText();
        Assertions.assertEquals("1 Viererfolge einer Farbe + 1 Drilling", player.getPhaseText());
        player.setPhaseNumber(9);
        player.setPhaseText();
        Assertions.assertEquals("1 Fünfling + 1 Drilling", player.getPhaseText());
        player.setPhaseNumber(10);
        player.setPhaseText();
        Assertions.assertEquals("1 Fünfling + 1 Dreierfolge einer Farbe", player.getPhaseText());
    }

    @Test
    public void ifGetColorAsStringisUsed_ThenReturnColorAsAString () {
        player.setColor(PlayerColor.RED);
        Assertions.assertEquals("RED",player.getColorAsString());
        player.setColor(PlayerColor.BLUE);
        Assertions.assertEquals("BLUE",player.getColorAsString());
        player.setColor(PlayerColor.GREEN);
        Assertions.assertEquals("GREEN",player.getColorAsString());
        player.setColor(PlayerColor.YELLOW);
        Assertions.assertEquals("YELLOW",player.getColorAsString());
    }

    //Funktionalität von setStartingOrder wirkt komisch...
    @Test
    public void testForPlayerGetterAndSetter () {
        Assertions.assertEquals(-1,player.getStartingOrder());
        player.setStartingOrder(3);
        Assertions.assertEquals(-1,player.getStartingOrder());
        Assertions.assertEquals(PlayerState.WAITING,player.getState());
        player.setState(PlayerState.PLAYING);
        Assertions.assertEquals(PlayerState.PLAYING,player.getState());
        Assertions.assertEquals("Test-Player", player.getName());
        Assertions.assertEquals(null, playerCon1.getRoom());
        Assertions.assertEquals(0,player.getMinusPoints());
        player.setMinusPoints(5);
        Assertions.assertEquals(5,player.getMinusPoints());
        playerCon1.setPlayerview(null);
        Assertions.assertEquals(null, playerCon1.getPlayerview());
        playerCon1.setCardField(null);
        Assertions.assertEquals(null, playerCon1.getCardField());
    }



}
