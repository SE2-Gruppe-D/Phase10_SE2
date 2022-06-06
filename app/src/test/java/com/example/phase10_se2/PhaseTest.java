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
        card3g = new Cards("GREEN", 3,null, 27);
        card8y = new Cards("YELLOW", 8, null, 8);
        card8b = new Cards("BLUE", 8,null,32);
        card8b2 = new Cards("BLUE", 8,null,64);
        card6g = new Cards("GREEN", 6,null,6);
        list = new ArrayList<>();
    }

//Phase1 Test
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
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
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
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhase1AndListLengthIsWrong_ThenReturnFalse ()
    {
        list.add(card2g);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhaseNumberButItsAWrongNumber_ThenReturnFalse ()
    {
        list.add(card2g);
        player.setPhaseNumber(0);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    //Phase 2 Tests
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
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
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
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
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
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }
//Phase 3 Tests
    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAre4CardsOfTheSameValueAnd4CardsInSuccession_ThenReturnTrue ()
    {
        list.add(card1y);
        list.add(card8r);
        list.add(card2y);
        list.add(card3g);
        list.add(card8y);
        list.add(card4b);
        list.add(card8g);
        list.add(card8b);
        player.setPhaseNumber(3);
        Assertions.assertTrue(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhase3AndListLengthIsWrong_ThenReturnFalse ()
    {
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
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAre4CardsOfTheSameValueButNot4CardsInSuccession_ThenReturnFalse ()
    {
        list.add(card1y);
        list.add(card8r);
        list.add(card2y);
        list.add(card3g);
        list.add(card8y);
        list.add(card5y);
        list.add(card8g);
        list.add(card8b);
        player.setPhaseNumber(3);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    @Test
    public void whenCheckingPhase3AndListLengthIsRightAndThereAreNot4CardsOfTheSameValueBut4CardsInSuccession_ThenReturnFalse ()
    {
        list.add(card1y);
        list.add(card8r);
        list.add(card2y);
        list.add(card3g);
        list.add(card8y);
        list.add(card5y);
        list.add(card8g);
        list.add(card7b);
        player.setPhaseNumber(3);
        Assertions.assertFalse(phaseCheck.getRightPhase(player.getPhaseNumber(),list));
    }

    /*
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
     */


}
