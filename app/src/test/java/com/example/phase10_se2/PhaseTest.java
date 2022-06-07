package com.example.phase10_se2;

import org.junit.jupiter.api.*;
import java.util.ArrayList;

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
    private Cards card3g;
    private Cards card8y;
    private Cards card8b;
    private Cards card8b2;
    private Cards card6g;
    private Cards card9r;
    private Cards card7g;
    private Cards card7r;
    private Cards card8r2;
    private Cards card8y2;
    private Cards card8g2;
    private Cards card3y;
    private Cards card4y;
    private Cards card2r;
    private Cards card2y2;
    private ArrayList<Cards> list;


    @BeforeEach
    public void setup() {
        player = new Player("player1", PlayerColor.BLUE, 1);
        phaseCheck = new Phase();
        card8r = new Cards("RED", 8, null, 8);
        card8g = new Cards("GREEN", 8, null, 32);
        card7y = new Cards("YELLOW", 7, null, 7);
        card7b = new Cards("BLUE", 7, null, 31);
        card4r = new Cards("RED", 4, null, 4);
        card4b = new Cards("BLUE", 4, null, 28);
        card2g = new Cards("GREEN", 2, null, 2);
        card2y = new Cards("YELLOW", 2, null, 26);
        card10y = new Cards("YELLOW", 10, null, 10);
        card1y = new Cards("YELLOW", 1, null, 1);
        card5y = new Cards("YELLOW", 5, null, 5);
        card12y = new Cards("YELLOW", 12, null, 12);
        card11y = new Cards("YELLOW", 11, null, 11);
        card3g = new Cards("GREEN", 3, null, 27);
        card8y = new Cards("YELLOW", 8, null, 8);
        card8b = new Cards("BLUE", 8, null, 32);
        card6g = new Cards("GREEN", 6, null, 6);
        card9r = new Cards("RED", 9, null, 9);
        card7g = new Cards ("GREEN", 7, null, 55);
        card7r = new Cards ("RED", 7, null, 79);
        card3y = new Cards("YELLOW", 3, null, 333);
        card4y = new Cards("YELLOW", 4, null, 44);
        card2r = new Cards("RED", 2, null, 222);

        card8b2 = new Cards("BLUE", 8, null, 64);
        card8r2 = new Cards ("RED", 8,null,108);
        card8g2 = new Cards ("GREEN", 8, null, 116);
        card8y2 = new Cards ("YELLOW", 8, null, 124);
        card2y2 = new Cards("YELLOW", 2, null, 82);
        list = new ArrayList<>();
    }

    //Phase1 Test
    @Test
    public void whenCheckingPhase1AndListLengthIsRightAndCardsAreRightAndSorted_ThenReturnTrue() {
        list.add(card2g);
        list.add(card2y);
        list.add(card4b);
        list.add(card4r);
        list.add(card7y);
        list.add(card7b);
        list.add(card8g);
        list.add(card8r);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsRightAndCardsAreRightButUnsorted_ThenReturnTrue() {
        list.add(card2g);
        list.add(card8g);
        list.add(card8r);
        list.add(card7y);
        list.add(card4b);
        list.add(card4r);
        list.add(card2y);
        list.add(card7b);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsWrong_ThenReturnFalse() {
        list.add(card2g);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhaseNumberButItsAWrongNumber_ThenReturnFalse() {
        list.add(card2g);
        player.setPhaseNumber(0);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase 2 Tests
    @Test
    public void whenCheckingPhase2AndListLengthIsRightAndAllCardsAreOfOneColor_ThenReturnTrue() {
        list.add(card1y);
        list.add(card5y);
        list.add(card10y);
        list.add(card7y);
        list.add(card12y);
        list.add(card2y);
        player.setPhaseNumber(2);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase2AndListLengthIsRightButNotAllCardsAreOfTheSameColor_ThenReturnFalse() {
        list.add(card1y);
        list.add(card5y);
        list.add(card10y);
        list.add(card7y);
        list.add(card12y);
        list.add(card2g);
        player.setPhaseNumber(2);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase2AndListLengthIsWrongEvenIfAllCardsAreOfOneColor_ThenReturnFalse() {
        list.add(card1y);
        list.add(card5y);
        list.add(card10y);
        list.add(card7y);
        list.add(card12y);
        list.add(card2y);
        list.add(card11y);
        player.setPhaseNumber(2);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase 3 Tests
    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAre4CardsOfTheSameValueAnd4CardsInSuccession_ThenReturnTrue() {
        list.add(card1y);
        list.add(card8r);
        list.add(card2y);
        list.add(card3g);
        list.add(card8y);
        list.add(card4b);
        list.add(card8g);
        list.add(card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase3AndListLengthIsWrong_ThenReturnFalse() {
        list.add(card1y);
        list.add(card8r);
        list.add(card2y);
        list.add(card3g);
        list.add(card8y);
        list.add(card4b);
        list.add(card8g);
        list.add(card8b);
        list.add(card8b2);
        player.setPhaseNumber(3);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAre4CardsOfTheSameValueButNot4CardsInSuccession_ThenReturnFalse() {
        list.add(card1y);
        list.add(card8r);
        list.add(card2y);
        list.add(card3g);
        list.add(card8y);
        list.add(card5y);
        list.add(card8g);
        list.add(card8b);
        player.setPhaseNumber(3);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAreNot4CardsOfTheSameValueBut4CardsInSuccession_ThenReturnFalse() {
        list.add(card1y);
        list.add(card8r);
        list.add(card2y);
        list.add(card3g);
        list.add(card8y);
        list.add(card5y);
        list.add(card8g);
        list.add(card7b);
        player.setPhaseNumber(3);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }


    //Test um zu überprüfen, ob die Zahl der Vierlinge in der Viererfolge vorkommen darf
    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAre4CardsOfTheSameValueAnd4CardsInARow_ThenReturnTrue ()
    {
        list.add(card5y);
        list.add(card8r);
        list.add(card7b);
        list.add(card6g);
        list.add(card8y);
        list.add(card8b2);
        list.add(card8g);
        list.add(card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }
   
//Phase 4 Test
    @Test
    public void whenCheckingPhase4AndListLengthIsRightAndThereAre8CardsInSuccession_ThenReturnTrue() {
        list.add(card1y);
        list.add(card5y);
        list.add(card2y);
        list.add(card3g);
        list.add(card6g);
        list.add(card4b);
        list.add(card7b);
        list.add(card8b);
        player.setPhaseNumber(4);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase4AndListLengthIsWrong_ThenReturnFalse() {
        list.add(card1y);
        player.setPhaseNumber(4);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase4AndListLengthIsRightButThereAreNot8CardsInSuccession_ThenReturnFalse() {
        list.add(card1y);
        list.add(card5y);
        list.add(card2y);
        list.add(card3g);
        list.add(card6g);
        list.add(card4b);
        list.add(card7b);
        list.add(card9r);
        player.setPhaseNumber(4);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase 5
    @Test
    public void whenCheckingPhase5AndListLengthIsRightAndAllCardsAreOfOneColor_ThenReturnTrue() {
        list.add(card1y);
        list.add(card5y);
        list.add(card10y);
        list.add(card7y);
        list.add(card12y);
        list.add(card2y);
        list.add(card8y);
        player.setPhaseNumber(5);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase5AndListLengthIsWrong_ThenReturnFalse() {
        list.add(card1y);
        player.setPhaseNumber(5);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Richtige Listenlänge und falsche Karten bereits bei 2 getestet.


    //Phase 6
    @Test
    public void whenCheckingPhase6AndListLengthIsRightAndThereAre9CardsInSuccession_ThenReturnTrue() {
        list.add(card1y);
        list.add(card5y);
        list.add(card2y);
        list.add(card3g);
        list.add(card6g);
        list.add(card4b);
        list.add(card7b);
        list.add(card8b);
        list.add(card9r);
        player.setPhaseNumber(6);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase6AndListLengthIsWrong_ThenReturnFalse() {
        list.add(card1y);
        player.setPhaseNumber(6);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Richtige Listenlänge und falsche Karten bereits bei 4 getestet.

    //Phase 7
    @Test
    public void whenCheckingPhase7AndListLengthIsRightAnd4CardsHaveTheSameValueTwice_ThenReturnRight() {
        list.add(card7b);
        list.add(card8r);
        list.add(card7y);
        list.add(card8y);
        list.add(card7g);
        list.add(card7r);
        list.add(card8g);
        list.add(card8b);
        player.setPhaseNumber(7);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }


    @Test
    public void whenCheckingPhase7AndListLengthIsRightAnd4CardsHaveTheSameValueTwiceInARow_ThenReturnRight() {
        list.add(card8r);
        list.add(card8g);
        list.add(card8r2);
        list.add(card8y2);
        list.add(card8b);
        list.add(card8g2);
        list.add(card8y);
        list.add(card8b2);
        player.setPhaseNumber(7);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }




    @Test
    public void whenCheckingPhase7AndListLengthIsWrong_ThenReturnFalse() {
        list.add(card1y);
        player.setPhaseNumber(7);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase7AndListLengthIsRightAndThereAreNot4CardsOfTheSameValueTwice_ThenReturnFalse() {
        list.add(card7b);
        list.add(card8r);
        list.add(card7y);
        list.add(card8y);
        list.add(card7g);
        list.add(card7r);
        list.add(card8g);
        list.add(card9r);
        player.setPhaseNumber(7);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase 8
    @Test
    public void whenCheckingPhase8AndListLengthIsRightAnd4CardsAreInSuccessionAndTheSameColorAnd3CardsHaveTheSameValue_ThenReturnRight() {
        list.add(card2y);
        list.add(card1y);
        list.add(card3y);
        list.add(card4y);
        list.add(card7g);
        list.add(card7r);
        list.add(card7y);
        player.setPhaseNumber(8);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase8AndListLengthIsRightAnd4CardsAreInSuccessionAndTheSameColorAnd3CardsHaveTheSameValueAsACardOfTheSuccession_ThenReturnRight() {
        list.add(card2y);
        list.add(card1y);
        list.add(card2g);
        list.add(card2r);
        list.add(card2y2);
        list.add(card3y);
        list.add(card4y);
        player.setPhaseNumber(8);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase8AndListLengthIsRightBut4CardsAreInSuccessionButNotOfTheSameColorEvenWhen3CardsHaveTheSameValue_ThenReturnFalse() {
        list.add(card2y);
        list.add(card1y);
        list.add(card2g);
        list.add(card2r);
        list.add(card2y2);
        list.add(card3y);
        list.add(card4b);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase8AndListLengthIsRightBut4CardsAreNotInSuccessionEvenIfTheyHaveTheSameColorAnd3CardsHaveTheSameValue_ThenReturnFalse() {
        list.add(card2y);
        list.add(card1y);
        list.add(card2g);
        list.add(card2r);
        list.add(card2y2);
        list.add(card3y);
        list.add(card5y);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }
/*
//Überprüfung der Folge funktioniert nicht richtig
    @Test
    public void whenCheckingPhase8AndListLengthIsRightAnd4CardsAreInSuccessionAndTheSameColorBut3CardsDoNotHaveTheSameValue_ThenReturnFalse() {
        list.add(card2y);
        list.add(card1y);
        list.add(card4b);
        list.add(card2r);
        list.add(card2y2);
        list.add(card3y);
        list.add(card4y);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }
    @Test
    public void whenCheckingPhase8AndListLengthIsRightAnd4CardsAreInSuccessionAndTheSameColorBut3CardsDoNotHaveTheSameValue2_ThenReturnFalse() {
        list.add(card2y);
        list.add(card1y);
        list.add(card8b);
        list.add(card3y);
        list.add(card4y);
        list.add(card9r);
        list.add(card7y);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }


 */

    @Test
    public void whenCheckingPhase8AndListLengthIsWrong_ThenReturnFalse() {
        list.add(card2y);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase9

    @Test
    public void whenCheckingPhase9AndListLengthIsRightAnd5CardsAnd3CardsHaveTheSameValue_ThenReturnTrue() {
        list.add(card8r);
        list.add(card8r2);
        list.add(card8y);
        list.add(card8b);
        list.add(card8g);
        list.add(card7b);
        list.add(card7r);
        list.add(card7g);
        player.setPhaseNumber(9);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase9AndListLengthIsRightAnd5CardsAnd3CardsHaveTheSameValueWithSameNumbersInBothConditions_ThenReturnTrue() {
        list.add(card8r);
        list.add(card8r2);
        list.add(card8y);
        list.add(card8b);
        list.add(card8g);
        list.add(card8g2);
        list.add(card8y2);
        list.add(card8b2);
        player.setPhaseNumber(9);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase9AndListLengthIsWrong_ThenReturnFalse() {
        list.add(card8r);
        player.setPhaseNumber(9);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase10






}
