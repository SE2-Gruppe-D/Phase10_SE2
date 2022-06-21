package com.example.phase10_se2;

import com.example.phase10_se2.enums.PlayerColor;

import org.junit.jupiter.api.*;
import java.util.ArrayList;

public class PhaseTest {
    private Player player;
    private Phase phaseCheck;
    private ArrayList<Cards> list;
    private CardsForTesting cft = new CardsForTesting();

    @BeforeEach
    public void setup() {
        player = new Player("player1", PlayerColor.BLUE, 1);
        phaseCheck = new Phase();
        list = new ArrayList<>();
    }

    //Phase1 Test
    @Test
    public void whenCheckingPhase1AndListLengthIsRightAndCardsAreRightAndSorted_ThenReturnTrue() {
        System.out.println(cft.card2g.getValue());
        list.add(cft.card2g);
        list.add(cft.card2y);
        list.add(cft.card4b);
        list.add(cft.card4r);
        list.add(cft.card7y);
        list.add(cft.card7b);
        list.add(cft.card8g);
        list.add(cft.card8r);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsRightAndCardsAreRightWithSimilarNumbers_ThenReturnTrue() {
        System.out.println(cft.card2g.getValue());
        list.add(cft.card8g2);
        list.add(cft.card2y);
        list.add(cft.card2g);
        list.add(cft.card8y);
        list.add(cft.card8g);
        list.add(cft.card8r2);
        list.add(cft.card8b);
        list.add(cft.card8r);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsRightAndCardsAreRightButUnsorted_ThenReturnTrue() {
        list.add(cft.card2g);
        list.add(cft.card8g);
        list.add(cft.card8r);
        list.add(cft.card7y);
        list.add(cft.card4b);
        list.add(cft.card4r);
        list.add(cft.card2y);
        list.add(cft.card7b);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsRightAndCardsAreFalse_ThenReturnFalse() {
        list.add(cft.card2g);
        list.add(cft.card2y);
        list.add(cft.card4b);
        list.add(cft.card4r);
        list.add(cft.card7y);
        list.add(cft.card7b);
        list.add(cft.card9r);
        list.add(cft.card8r);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsWrong_ThenReturnFalse() {
        list.add(cft.card2g);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhaseNumberButItsAWrongNumber_ThenReturnFalse() {
        list.add(cft.card2g);
        player.setPhaseNumber(0);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase 2 Tests
    @Test
    public void whenCheckingPhase2AndListLengthIsRightAndAllCardsAreOfOneColor_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card10y);
        list.add(cft.card7y);
        list.add(cft.card12y);
        list.add(cft.card2y);
        player.setPhaseNumber(2);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase2AndListLengthIsRightButNotAllCardsAreOfTheSameColor_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card10y);
        list.add(cft.card7y);
        list.add(cft.card12y);
        list.add(cft.card2g);
        player.setPhaseNumber(2);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase2AndListLengthIsWrongEvenIfAllCardsAreOfOneColor_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card10y);
        list.add(cft.card7y);
        list.add(cft.card12y);
        list.add(cft.card2y);
        list.add(cft.card11y);
        player.setPhaseNumber(2);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase 3 Tests
    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAre4CardsOfTheSameValueAnd4CardsInSuccession_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card8r);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card8y);
        list.add(cft.card4b);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase3AndListLengthIsWrong_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card8r);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card8y);
        list.add(cft.card4b);
        list.add(cft.card8g);
        list.add(cft.card8b);
        list.add(cft.card8b2);
        player.setPhaseNumber(3);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAre4CardsOfTheSameValueButNot4CardsInSuccession_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card8r);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card8y);
        list.add(cft.card5y);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(3);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAreNot4CardsOfTheSameValueBut4CardsInSuccession_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card8r);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card8y);
        list.add(cft.card5y);
        list.add(cft.card8g);
        list.add(cft.card7b);
        player.setPhaseNumber(3);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }


    //Test um zu überprüfen, ob die Zahl der Vierlinge in der Viererfolge vorkommen darf
    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAre4CardsOfTheSameValueAnd4CardsInARow_ThenReturnTrue ()
    {
        list.add(cft.card5y);
        list.add(cft.card8r);
        list.add(cft.card7b);
        list.add(cft.card6g);
        list.add(cft.card8y);
        list.add(cft.card8b2);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }
   
//Phase 4 Test
    @Test
    public void whenCheckingPhase4AndListLengthIsRightAndThereAre8CardsInSuccession_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card6g);
        list.add(cft.card4b);
        list.add(cft.card7b);
        list.add(cft.card8b);
        player.setPhaseNumber(4);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase4AndListLengthIsWrong_ThenReturnFalse() {
        list.add(cft.card1y);
        player.setPhaseNumber(4);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase4AndListLengthIsRightButThereAreNot8CardsInSuccession_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card6g);
        list.add(cft.card4b);
        list.add(cft.card7b);
        list.add(cft.card9r);
        player.setPhaseNumber(4);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase 5
    @Test
    public void whenCheckingPhase5AndListLengthIsRightAndAllCardsAreOfOneColor_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card10y);
        list.add(cft.card7y);
        list.add(cft.card12y);
        list.add(cft.card2y);
        list.add(cft.card8y);
        player.setPhaseNumber(5);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase5AndListLengthIsWrong_ThenReturnFalse() {
        list.add(cft.card1y);
        player.setPhaseNumber(5);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Richtige Listenlänge und falsche Karten bereits bei 2 getestet.


    //Phase 6
    @Test
    public void whenCheckingPhase6AndListLengthIsRightAndThereAre9CardsInSuccession_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card6g);
        list.add(cft.card4b);
        list.add(cft.card7b);
        list.add(cft.card8b);
        list.add(cft.card9r);
        player.setPhaseNumber(6);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase6AndListLengthIsWrong_ThenReturnFalse() {
        list.add(cft.card1y);
        player.setPhaseNumber(6);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Richtige Listenlänge und falsche Karten bereits bei 4 getestet.

    //Phase 7
    @Test
    public void whenCheckingPhase7AndListLengthIsRightAnd4CardsHaveTheSameValueTwice_ThenReturnRight() {
        list.add(cft.card7b);
        list.add(cft.card8r);
        list.add(cft.card7y);
        list.add(cft.card8y);
        list.add(cft.card7g);
        list.add(cft.card7r);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(7);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }


    @Test
    public void whenCheckingPhase7AndListLengthIsRightAnd4CardsHaveTheSameValueTwiceInARow_ThenReturnRight() {
        list.add(cft.card8r);
        list.add(cft.card8g);
        list.add(cft.card8r2);
        list.add(cft.card8y2);
        list.add(cft.card8b);
        list.add(cft.card8g2);
        list.add(cft.card8y);
        list.add(cft.card8b2);
        player.setPhaseNumber(7);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }


    @Test
    public void whenCheckingPhase7AndListLengthIsWrong_ThenReturnFalse() {
        list.add(cft.card1y);
        player.setPhaseNumber(7);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase7AndListLengthIsRightAndThereAreNot4CardsOfTheSameValueTwice_ThenReturnFalse() {
        list.add(cft.card7b);
        list.add(cft.card8r);
        list.add(cft.card7y);
        list.add(cft.card8y);
        list.add(cft.card7g);
        list.add(cft.card7r);
        list.add(cft.card8g);
        list.add(cft.card9r);
        player.setPhaseNumber(7);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase 8
    @Test
    public void whenCheckingPhase8AndListLengthIsRightAnd4CardsAreInSuccessionAndTheSameColorAnd3CardsHaveTheSameValue_ThenReturnRight() {
        list.add(cft.card2y);
        list.add(cft.card1y);
        list.add(cft.card3y);
        list.add(cft.card4y);
        list.add(cft.card7g);
        list.add(cft.card7r);
        list.add(cft.card7y);
        player.setPhaseNumber(8);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase8AndListLengthIsRightAnd4CardsAreInSuccessionAndTheSameColorAnd3CardsHaveTheSameValueAsACardOfTheSuccession_ThenReturnRight() {
        list.add(cft.card2y);
        list.add(cft.card1y);
        list.add(cft.card2g);
        list.add(cft.card2r);
        list.add(cft.card2y2);
        list.add(cft.card3y);
        list.add(cft.card4y);
        player.setPhaseNumber(8);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase8AndListLengthIsRightBut4CardsAreInSuccessionButNotOfTheSameColorEvenWhen3CardsHaveTheSameValue_ThenReturnFalse() {
        list.add(cft.card2y);
        list.add(cft.card1y);
        list.add(cft.card2g);
        list.add(cft.card2r);
        list.add(cft.card2y2);
        list.add(cft.card3y);
        list.add(cft.card4b);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase8AndListLengthIsRightBut4CardsAreNotInSuccessionEvenIfTheyHaveTheSameColorAnd3CardsHaveTheSameValue_ThenReturnFalse() {
        list.add(cft.card2y);
        list.add(cft.card1y);
        list.add(cft.card2g);
        list.add(cft.card2r);
        list.add(cft.card2y2);
        list.add(cft.card3y);
        list.add(cft.card5y);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase8AndListLengthIsRightAnd4CardsAreInSuccessionAndTheSameColorBut3CardsDoNotHaveTheSameValue_ThenReturnFalse() {
        list.add(cft.card2y);
        list.add(cft.card1y);
        list.add(cft.card4b);
        list.add(cft.card2r);
        list.add(cft.card2y2);
        list.add(cft.card3y);
        list.add(cft.card4y);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }
    @Test
    public void whenCheckingPhase8AndListLengthIsRightAnd4CardsAreInSuccessionAndTheSameColorBut3CardsDoNotHaveTheSameValue2_ThenReturnFalse() {
        list.add(cft.card2y);
        list.add(cft.card1y);
        list.add(cft.card8b);
        list.add(cft.card3y);
        list.add(cft.card4y);
        list.add(cft.card9r);
        list.add(cft.card7y);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase8AndListLengthIsWrong_ThenReturnFalse() {
        list.add(cft.card2y);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }


    //Phase9
    @Test
    public void whenCheckingPhase9AndListLengthIsRightAnd5CardsAnd3CardsHaveTheSameValue_ThenReturnTrue() {
        list.add(cft.card8r);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card7b);
        list.add(cft.card7r);
        list.add(cft.card7g);
        player.setPhaseNumber(9);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase9AndListLengthIsRightAnd5CardsAnd3CardsHaveTheSameValueWithSameNumbersInBothConditions_ThenReturnTrue() {
        list.add(cft.card8r);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card8g2);
        list.add(cft.card8y2);
        list.add(cft.card8b2);
        player.setPhaseNumber(9);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase9AndListLengthIsWrong_ThenReturnFalse() {
        list.add(cft.card8r);
        player.setPhaseNumber(9);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    //Phase10
    @Test
    public void whenCheckingPhase10AndListLengthIsRightAndThereAre5CardsOfTheSameValueAnd3CardsOf1ColorInSuccession_ThenReturnTrue(){
        list.add(cft.card8r);
        list.add(cft.card3y);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card5y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card4y);
        player.setPhaseNumber(10);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase10AndListLengthIsRightAndThereAre5CardsOfTheSameValueAnd3CardsOf1ColorInSuccessionButTheNumbersInterfere_ThenReturnTrue(){
        list.add(cft.card8r);
        list.add(cft.card6y);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card7y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card8y2);
        player.setPhaseNumber(10);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase10AndListLengthIsRightAndThereAre5CardsOfTheSameValueAnd3CardsOf1ColorInSuccessionAsACardOfTheSuccession_ThenReturnTrue(){
        list.add(cft.card8r);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card7y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card8y2);
        list.add(cft.card9y);
        player.setPhaseNumber(10);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }

    @Test
    public void whenCheckingPhase10AndListLengthIsWrong_ThenReturnFalse(){
        list.add(cft.card8r);
        player.setPhaseNumber(10);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }


    @Test
    public void whenCheckingPhase10AndListLengthIsRightButThereAreNot5CardsOfTheSameValueAnd3CardsOf1ColorInSuccession_ThenReturnFalse(){
        list.add(cft.card8r);
        list.add(cft.card3y);
        list.add(cft.card7g);
        list.add(cft.card8y);
        list.add(cft.card5y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card4y);
        player.setPhaseNumber(10);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }



    @Test
    public void whenCheckingPhase10AndListLengthIsRightAndThereAre5CardsOfTheSameValueButNot3CardsOf1ColorInSuccession_ThenReturnFalse(){
            list.add(cft.card8r);
            list.add(cft.card3y);
            list.add(cft.card8g2);
            list.add(cft.card8y);
            list.add(cft.card7y);
            list.add(cft.card8b);
            list.add(cft.card8g);
            list.add(cft.card4y);
            player.setPhaseNumber(10);
            Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }



    @Test
    public void whenCheckingPhase10AndListLengthIsRightAnd3CardsAreInSuccessionAndTheSameColorBut5CardsDoNotHaveTheSameValue2_ThenReturnFalse() {
        list.add(cft.card2y);
        list.add(cft.card1y);
        list.add(cft.card8b);
        list.add(cft.card3y);
        list.add(cft.card4y);
        list.add(cft.card9r);
        list.add(cft.card7y);
        list.add(cft.card6g);
        player.setPhaseNumber(10);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(), list));
    }



    //Tests für 1 Karte zur abgelegten Phase dazulegen

    //Zwilling, Drilling, Vierling dazulegen -> Phase 1,7,9
    @Test
    public void afterCheckingPhase1AndAddOneCard_ThenReturnTrue() {
        System.out.println(cft.card2g.getValue());
        list.add(cft.card2g);
        list.add(cft.card2y);
        list.add(cft.card4b);
        list.add(cft.card4r);
        list.add(cft.card7y);
        list.add(cft.card7b);
        list.add(cft.card8g);
        list.add(cft.card8r);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card4y, list));
    }

    @Test
    public void afterCheckingPhase1AndAddOneCard_ThenReturnFalse() {
        System.out.println(cft.card2g.getValue());
        list.add(cft.card2g);
        list.add(cft.card2y);
        list.add(cft.card4b);
        list.add(cft.card4r);
        list.add(cft.card7y);
        list.add(cft.card7b);
        list.add(cft.card8g);
        list.add(cft.card8r);
        Assertions.assertFalse(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card12y, list));
    }

    @Test
    public void afterCheckingPhase7AndAddOneCard_ThenReturnTrue() {
        list.add(cft.card7b);
        list.add(cft.card8r);
        list.add(cft.card7y);
        list.add(cft.card8y);
        list.add(cft.card7g);
        list.add(cft.card7r);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(7);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card7b, list));
    }

    @Test
    public void afterCheckingPhase9AndAddOneCard_ThenReturnTrue() {
        list.add(cft.card8r);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card7b);
        list.add(cft.card7r);
        list.add(cft.card7g);
        player.setPhaseNumber(9);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card7b, list));
    }

    //Gleiche Farbe dazulegen Phase 2,5
    @Test
    public void afterCheckingPhase2AndAddOneCard_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card10y);
        list.add(cft.card7y);
        list.add(cft.card12y);
        list.add(cft.card2y);
        player.setPhaseNumber(2);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card4y, list));
    }

    @Test
    public void afterCheckingPhase5AndAddOneCard_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card10y);
        list.add(cft.card7y);
        list.add(cft.card12y);
        list.add(cft.card2y);
        list.add(cft.card8y);
        player.setPhaseNumber(5);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card4y, list));
    }


    @Test
    public void afterCheckingPhase2AndAddOneCard_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card10y);
        list.add(cft.card7y);
        list.add(cft.card12y);
        list.add(cft.card2y);
        player.setPhaseNumber(2);
        Assertions.assertFalse(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card2g, list));
    }

    //Bei der Folge dazulegen, Phase 4,6
    @Test
    public void afterCheckingPhase4AndAddOneHigherCard_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card6g);
        list.add(cft.card4b);
        list.add(cft.card7b);
        list.add(cft.card8b);
        player.setPhaseNumber(4);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card9r, list));
    }

    @Test
    public void afterCheckingPhase4AndAddOneLowerCard_ThenReturnTrue() {
        list.add(cft.card9r);
        list.add(cft.card5y);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card6g);
        list.add(cft.card4b);
        list.add(cft.card7b);
        list.add(cft.card8b);
        player.setPhaseNumber(4);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card1y, list));
    }

    @Test
    public void afterCheckingPhase4AndAddOneCard_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card6g);
        list.add(cft.card4b);
        list.add(cft.card7b);
        list.add(cft.card8b);
        player.setPhaseNumber(4);
        Assertions.assertFalse(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card12y, list));
    }

    @Test
    public void afterCheckingPhase4AndAddOneCardSameValue_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card6g);
        list.add(cft.card4b);
        list.add(cft.card7b);
        list.add(cft.card8b);
        player.setPhaseNumber(4);
        Assertions.assertFalse(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card4y, list));
    }

    @Test
    public void afterCheckingPhase6AndAddOneHigherCard_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card5y);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card6g);
        list.add(cft.card4b);
        list.add(cft.card7b);
        list.add(cft.card8b);
        list.add(cft.card9r);
        player.setPhaseNumber(6);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card10y, list));
    }

    //Phase 3
    @Test
    public void afterCheckingPhase3AndAddOneEqualCard_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card8r);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card8y);
        list.add(cft.card4b);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8b2, list));
    }

    @Test
    public void afterCheckingPhase3AndAddOneCardForRowLastCard_ThenReturnTrue() {
        list.add(cft.card1y);
        list.add(cft.card8r);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card8y);
        list.add(cft.card4b);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card5y, list));
    }

    @Test
    public void afterCheckingPhase3AndAddOneCardForRowFirstCard_ThenReturnTrue() {
        list.add(cft.card5y);
        list.add(cft.card8r);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card8y);
        list.add(cft.card4b);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card1y, list));
    }

    @Test
    public void afterCheckingPhase3AndAddOneCardForRowAndSameValue_ThenReturnTrue() {
        list.add(cft.card5y);
        list.add(cft.card8r);
        list.add(cft.card7b);
        list.add(cft.card6g);
        list.add(cft.card8y);
        list.add(cft.card8g2);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8b2, list));
    }

    @Test
    public void afterCheckingPhase3AndAddOneCardForRowAndSameValue2_ThenReturnTrue() {
        list.add(cft.card5y);
        list.add(cft.card4b);
        list.add(cft.card7b);
        list.add(cft.card6g);
        list.add(cft.card8y);
        list.add(cft.card8g2);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8b2, list));
    }

    @Test
    public void afterCheckingPhase3AndAddOneCardForRow_ThenReturnFalse() {
        list.add(cft.card1y);
        list.add(cft.card8r);
        list.add(cft.card2y);
        list.add(cft.card3g);
        list.add(cft.card8y);
        list.add(cft.card4b);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(3);
        Assertions.assertFalse(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card6g, list));
    }


    //Phase 8
    @Test
    public void afterCheckingPhase8AndAddOneEqualCard_ThenReturnTrue() {
        list.add(cft.card2y);
        list.add(cft.card1y);
        list.add(cft.card3y);
        list.add(cft.card4y);
        list.add(cft.card7g);
        list.add(cft.card7r);
        list.add(cft.card7y);
        player.setPhaseNumber(8);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card7b, list));
    }

    @Test
    public void afterCheckingPhase8AndAddOneCardForRowLastCard_ThenReturnTrue() {
        list.add(cft.card2y);
        list.add(cft.card1y);
        list.add(cft.card3y);
        list.add(cft.card4y);
        list.add(cft.card7g);
        list.add(cft.card7r);
        list.add(cft.card7y);
        player.setPhaseNumber(8);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card5y, list));
    }

    @Test
    public void afterCheckingPhase8AndAddOneCardForRowFirstCard_ThenReturnTrue() {
        list.add(cft.card2y);
        list.add(cft.card5y);
        list.add(cft.card3y);
        list.add(cft.card4y);
        list.add(cft.card7g);
        list.add(cft.card7r);
        list.add(cft.card7y);
        player.setPhaseNumber(8);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card1y, list));
    }

    @Test
    public void afterCheckingPhase8AndAddOneCardForRowAndSameValue_ThenReturnTrue() {
        list.add(cft.card8r);
        list.add(cft.card7y);
        list.add(cft.card6y);
        list.add(cft.card8y);
        list.add(cft.card8g2);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(8);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8y2, list));
    }

    @Test
    public void afterCheckingPhase8AndAddOneCardForRowAndSameValue2_ThenReturnTrue() {
        list.add(cft.card5y);
        list.add(cft.card7y);
        list.add(cft.card6y);
        list.add(cft.card8y);
        list.add(cft.card8g2);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(8);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8b2, list));
    }

    @Test
    public void afterCheckingPhase8AndAddOneCardForRow_ThenReturnFalse() {
        list.add(cft.card2y);
        list.add(cft.card5y);
        list.add(cft.card3y);
        list.add(cft.card4y);
        list.add(cft.card7g);
        list.add(cft.card7r);
        list.add(cft.card7y);
        player.setPhaseNumber(8);
        Assertions.assertFalse(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8g, list));
    }

    //Phase 10
    @Test
    public void afterCheckingPhase10AndAddOneEqualCard_ThenReturnTrue() {
        list.add(cft.card8r);
        list.add(cft.card3y);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card5y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card4y);
        player.setPhaseNumber(10);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8b2, list));
    }

    @Test
    public void afterCheckingPhase10AndAddOneCardForRowLastCard_ThenReturnTrue() {
        list.add(cft.card8r);
        list.add(cft.card3y);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card5y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card4y);
        player.setPhaseNumber(10);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card6y, list));
    }

    @Test
    public void afterCheckingPhase10AndAddOneCardForRowFirstCard_ThenReturnTrue() {
        list.add(cft.card8r);
        list.add(cft.card3y);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card5y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card4y);
        player.setPhaseNumber(10);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card2y, list));
    }

    @Test
    public void afterCheckingPhase10AndAddOneCardForRowAndSameValue_ThenReturnTrue() {
        list.add(cft.card8r);
        list.add(cft.card6y);
        list.add(cft.card8r2);
        list.add(cft.card8y);
        list.add(cft.card7y);
        list.add(cft.card8b);
        list.add(cft.card8g);
        list.add(cft.card8y2);
        player.setPhaseNumber(10);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8b2, list));
    }

    @Test
    public void afterCheckingPhase10AndAddOneCardForRowAndSameValue2_ThenReturnTrue() {
        list.add(cft.card5y);
        list.add(cft.card7y);
        list.add(cft.card6y);
        list.add(cft.card8y);
        list.add(cft.card8g2);
        list.add(cft.card8g);
        list.add(cft.card8b);
        player.setPhaseNumber(10);
        Assertions.assertTrue(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8b2, list));
    }

    @Test
    public void afterCheckingPhase10AndAddOneCardForRow_ThenReturnFalse() {
        list.add(cft.card2y);
        list.add(cft.card5y);
        list.add(cft.card3y);
        list.add(cft.card4y);
        list.add(cft.card7g);
        list.add(cft.card7r);
        list.add(cft.card7y);
        player.setPhaseNumber(10);
        Assertions.assertFalse(phaseCheck.getRightPhaseOtherPlayer(player.getPhaseNumber(), cft.card8g, list));
    }



















}
