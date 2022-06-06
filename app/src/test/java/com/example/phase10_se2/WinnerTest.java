package com.example.phase10_se2;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class WinnerTest
{

    Player player1;
    Player player2;
    Cards card1;
    Cards card2;
    Cards card3;
    ArrayList<Cards> cards;
    ArrayList<Player> players;
    WinnerDecision winnerDecision;
    ArrayList<Player> winners;

    @BeforeEach
    public void setup()
    {
        player1 = new Player("Lorem",PlayerColor.BLUE, 1);
        player2 = new Player("Ipsum",PlayerColor.GREEN, 3);
        card1 = new Cards("blue",7,null,7);
        card2 = new Cards("yellow",5,null,5+48);
        card3 = new Cards("green",11,null,11+72);
        players = new ArrayList<Player>();
        winners = new ArrayList<Player>();

    }

    @Test
    public void ifTwoPlayersAreOnTheSamePhaseButHaveDifferentMinusPoints_ThenReturnTheRightPlayerAsWinner()
    {
        if (player1 != null && player2 != null)
        {
            cards = new ArrayList<Cards>();
            player1.setPhaseNumber(3);
            player2.setPhaseNumber(3);
            cards.add(card1);
            cards.add(card2);
            player1.updateMinusPoints(cards);
            Assertions.assertEquals(10, player1.getMinusPoints());
            cards.add(card3);
            player2.updateMinusPoints(cards);
            Assertions.assertEquals(20, player2.getMinusPoints());
            players.add(player1);
            players.add(player2);
            winnerDecision = new WinnerDecision(players);
            winners.add(player1);
            Assertions.assertEquals(winners, winnerDecision.getWinner());

        }
    }

    @Test
    public void ifTwoPlayersAreOnTheSamePhaseAndHaveTheSameMinusPoints_ThenReturnBothPlayersAsWinners()
    {
        if (player1 != null && player2 != null)
        {
            cards = new ArrayList<Cards>();
            player1.setPhaseNumber(3);
            player2.setPhaseNumber(3);
            cards.add(card1);
            cards.add(card2);
            player1.updateMinusPoints(cards);
            Assertions.assertEquals(10, player1.getMinusPoints());
            player2.updateMinusPoints(cards);
            Assertions.assertEquals(10, player2.getMinusPoints());
            players.add(player1);
            players.add(player2);
            winnerDecision = new WinnerDecision(players);
            winners.add(player1);
            winners.add(player2);
            Assertions.assertEquals(winners, winnerDecision.getWinner());
        }
    }

    @Test
    public void ifTwoPlayersAreOnDifferentPhasesAndHaveTheSameAmountOfMinusPoints_ThenReturnTheRightWinner()
    {
        if (player1 != null && player2 != null)
        {
            cards = new ArrayList<Cards>();
            player1.setPhaseNumber(3);
            player2.setPhaseNumber(2);
            cards.add(card1);
            cards.add(card2);
            player1.updateMinusPoints(cards);
            Assertions.assertEquals(10, player1.getMinusPoints());
            player2.updateMinusPoints(cards);
            Assertions.assertEquals(10, player2.getMinusPoints());
            player1.setPhaseNumber(3);
            players.add(player1);
            players.add(player2);
            winnerDecision = new WinnerDecision(players);
            winners.add(player1);
            Assertions.assertEquals(winners, winnerDecision.getWinner());
        }
    }

    @Test
    public void ifTwoPlayersAreOnTheSamePhaseAndHaveDifferentMinusPoints_ThenReturnTheRightWinner()
    {
        if (player1 != null && player2 != null)
        {
            cards = new ArrayList<Cards>();
            cards.add(card1);
            cards.add(card2);
            player1.updateMinusPoints(cards);
            Assertions.assertEquals(10, player1.getMinusPoints());
            cards.add(card3);
            player2.updateMinusPoints(cards);
            Assertions.assertEquals(20, player2.getMinusPoints());
            player1.setPhaseNumber(3);
            players.add(player1);
            winnerDecision = new WinnerDecision(players);
            winners.add(player1);
            Assertions.assertEquals(winners, winnerDecision.getWinner());
        }
    }

}












