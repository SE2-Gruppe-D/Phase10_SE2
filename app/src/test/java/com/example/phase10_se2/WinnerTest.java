package com.example.phase10_se2;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class WinnerTest
{

    Player player1;
    Player player2;
    Player player3;
    Player player4;
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
        player3 = new Player("Marce", PlayerColor.RED, 3);
        player4 = new Player("Lippi", PlayerColor.YELLOW, 1);
        card1 = new Cards("blue",7,null,7);
        card2 = new Cards("yellow",5,null,5+48);
        card3 = new Cards("green",11,null,11+72);
        players = new ArrayList<Player>();
        winners = new ArrayList<Player>();

    }

    @Test
    public void ifTwoPlayersAreOnTheSamePhaseButHaveDifferentMinusPoints_ThenReturnTheRightPlayerAsWinner()
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

    @Test
    public void ifTwoPlayersAreOnTheSamePhaseAndHaveTheSameMinusPoints_ThenReturnBothPlayersAsWinners()
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

    @Test
    public void ifTwoPlayersAreOnDifferentPhasesAndHaveTheSameAmountOfMinusPoints_ThenReturnTheRightWinner()
    {
            cards = new ArrayList<Cards>();
            cards.add(card1);
            cards.add(card2);
            player1.updateMinusPoints(cards);
            Assertions.assertEquals(10, player1.getMinusPoints());
            player2.updateMinusPoints(cards);
            Assertions.assertEquals(10, player2.getMinusPoints());
            player1.setPhaseNumber(4);
            players.add(player1);
            players.add(player2);
            winnerDecision = new WinnerDecision(players);
            winners.add(player1);
            Assertions.assertEquals(winners, winnerDecision.getWinner());
    }

    @Test
    public void ifTwoPlayersAreOnTheSamePhaseAndHaveDifferentMinusPoints_ThenReturnTheRightWinner()
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
            players.add(player2);
            winnerDecision = new WinnerDecision(players);
            winners.add(player1);
            Assertions.assertEquals(winners, winnerDecision.getWinner());
    }
    @Test
    public void ifThreePlayersAreGivenGoThroughThem_ThenReturnTheRightWinner()
    {
            cards = new ArrayList<Cards>();
            cards.add(card1);
            cards.add(card2);
            player1.updateMinusPoints(cards);
            Assertions.assertEquals(10, player1.getMinusPoints());
            cards.add(card3);
            player2.updateMinusPoints(cards);
            Assertions.assertEquals(20, player2.getMinusPoints());
            players.add(player1);
            players.add(player2);
            players.add(player3);
            players.add(player4);
            winnerDecision = new WinnerDecision(players);
            winners.add(player3);
            Assertions.assertEquals(winners, winnerDecision.getWinner());
    }

}












