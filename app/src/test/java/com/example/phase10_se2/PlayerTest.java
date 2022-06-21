package com.example.phase10_se2;

import org.junit.jupiter.api.*;

import com.example.phase10_se2.enums.PlayerColor;
import com.example.phase10_se2.enums.PlayerState;

import java.util.ArrayList;


public class PlayerTest {
    private Player player;
    private Player playerCon1;
    private Player playerCon3;
    private Player playerCon4;
    private CardsForTesting cfg = new CardsForTesting();
    private ArrayList<Cards> cards;

    @BeforeEach
    public void init() {
        player = new Player("Test-Player", null, 0,0);
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
                cards.add(cfg.card1y);
                cards.add(cfg.card2g);
                player.updateMinusPoints(cards);
                Assertions.assertEquals(10, player.getMinusPoints());
    }

    @Test
    public void ifOneTwoDigitAndOneOneDigitCardsAreLeft_ThenMinusPointsShouldBe15(){
            cards.add(cfg.card1y);
            cards.add(cfg.card11y);
            player.updateMinusPoints(cards);
            Assertions.assertEquals(15, player.getMinusPoints());
    }

    @Test
    public void ifPhaseNumberIs1_ThenAssignTheRightText() {
        player.setPhaseNumber(1);
        player.setPhaseText();
        Assertions.assertEquals("4 Zwillinge", player.getPhaseText());

       }

    @Test
    public void ifPhaseNumberIs2_ThenAssignTheRightText() {
        player.setPhaseNumber(2);
        player.setPhaseText();
        Assertions.assertEquals("6 Karten einer Farbe", player.getPhaseText());
    }

    @Test
    public void ifPhaseNumberIs3_ThenAssignTheRightText() {
        player.setPhaseNumber(3);
        player.setPhaseText();
        Assertions.assertEquals("1 Vierling + 1 Viererfolge", player.getPhaseText());
    }

    @Test
    public void ifPhaseNumberIs4_ThenAssignTheRightText() {
        player.setPhaseNumber(4);
        player.setPhaseText();
        Assertions.assertEquals("1 Achterfolge", player.getPhaseText());
    }

    @Test
    public void ifPhaseNumberIs5_ThenAssignTheRightText() {
        player.setPhaseNumber(5);
        player.setPhaseText();
        Assertions.assertEquals("7 Karten einer Farbe", player.getPhaseText());
    }

    @Test
    public void ifPhaseNumberIs6_ThenAssignTheRightText() {
        player.setPhaseNumber(6);
        player.setPhaseText();
        Assertions.assertEquals("1 Neunerfolge", player.getPhaseText());
    }

    @Test
    public void ifPhaseNumberIs7_ThenAssignTheRightText() {
        player.setPhaseNumber(7);
        player.setPhaseText();
        Assertions.assertEquals("2 Vierlinge", player.getPhaseText());
    }

    @Test
    public void ifPhaseNumberIs8_ThenAssignTheRightText() {
        player.setPhaseNumber(8);
        player.setPhaseText();
        Assertions.assertEquals("1 Viererfolge einer Farbe + 1 Drilling", player.getPhaseText());
    }

    @Test
    public void ifPhaseNumberIs9_ThenAssignTheRightText() {
        player.setPhaseNumber(9);
        player.setPhaseText();
        Assertions.assertEquals("1 Fünfling + 1 Drilling", player.getPhaseText());
    }

    @Test
    public void ifPhaseNumberIs10_ThenAssignTheRightText() {
        player.setPhaseNumber(10);
        player.setPhaseText();
        Assertions.assertEquals("1 Fünfling + 1 Dreierfolge einer Farbe", player.getPhaseText());
    }


    @Test
    public void ifGetColorAsStringIsUsedAsRED_ThenReturnColorAsAString () {
        player.setColor(PlayerColor.RED);
        Assertions.assertEquals("RED",player.getColorAsString());
    }

    @Test
    public void ifGetColorAsStringIsUsedAsBLUE_ThenReturnColorAsAString () {
        player.setColor(PlayerColor.BLUE);
        Assertions.assertEquals("BLUE",player.getColorAsString());
    }

    @Test
    public void ifGetColorAsStringIsUsedAsGREEN_ThenReturnColorAsAString () {
        player.setColor(PlayerColor.GREEN);
        Assertions.assertEquals("GREEN",player.getColorAsString());
    }

    @Test
    public void ifGetColorAsStringIsUsedAsYELLOW_ThenReturnColorAsAString () {
        player.setColor(PlayerColor.YELLOW);
        Assertions.assertEquals("YELLOW",player.getColorAsString());
    }


    //Funktionalität von setStartingOrder wirkt komisch...
    @Test
    public void testForPlayerGetterAndSetterStartingOrder () {
        player.setStartingOrder(3);
        Assertions.assertEquals(-1,player.getStartingOrder());
    }

    @Test
    public void testForPlayerGetterAndSetterPlayerState () {
        player.setState(PlayerState.PLAYING);
        Assertions.assertEquals(PlayerState.PLAYING,player.getState());
    }

    @Test
    public void testForPlayerGetterName () {
        Assertions.assertEquals("Test-Player", player.getName());
    }

    @Test
    public void testForPlayerGetterRoom () {
        Assertions.assertEquals(null, playerCon1.getRoom());
    }

    @Test
    public void testForPlayerGetterAndSetterMinusPoints () {
        player.setMinusPoints(5);
        Assertions.assertEquals(5,player.getMinusPoints());
    }

    @Test
    public void testForPlayerGetterAndSetterPlayerView () {
        playerCon1.setPlayerview(null);
        Assertions.assertEquals(null, playerCon1.getPlayerview());
    }

    @Test
    public void testForPlayerGetterAndSetterCardField () {
        playerCon1.setCardField(null);
        Assertions.assertEquals(null, playerCon1.getCardField());
    }

    @Test
    public void testForPlayerAddCardToCardField () {
        if(player!=null && player.getCardField()!=null) {
            player.addCardField(cfg.card2g);
            int lastCard = player.getCardField().size() - 1;
            Assertions.assertEquals(cfg.card2g, player.getCardField().get(lastCard));
        }
    }

    @Test
    public void testForPlayerupdateCardfieldCompletelyEmptyCardsList () {
        player.setCardField(null);
        Assertions.assertEquals(null, player.getCardField());
        }
    }


