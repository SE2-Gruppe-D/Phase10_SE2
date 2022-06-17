package com.example.phase10_se2;

import com.example.phase10_se2.ENUM.PlayerColor;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class WinnerTest
{

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private CardsForTesting cfg = new CardsForTesting();
    private ArrayList<Cards> cards;
    private ArrayList<Player> players;
    private WinnerDecision winnerDecision;
    private ArrayList<Player> winners;

    @BeforeEach
    public void setup()
    {
        player1 = new Player("Lorem", PlayerColor.BLUE, 1);
        player2 = new Player("Ipsum",PlayerColor.GREEN, 3);
        player3 = new Player("Marce", PlayerColor.RED, 3);
        player4 = new Player("Lippi", PlayerColor.YELLOW, 1);
        players = new ArrayList<Player>();
        winners = new ArrayList<Player>();

    }

    @Test
    public void ifTwoPlayersAreOnTheSamePhaseButHaveDifferentMinusPoints_ThenReturnTheRightPlayerAsWinner()
    {
            cards = new ArrayList<Cards>();
            player1.setPhaseNumber(3);
            player2.setPhaseNumber(3);
            cards.add(cfg.card1y);
            cards.add(cfg.card2g);
            player1.updateMinusPoints(cards);
            cards.add(cfg.card11y);
            player2.updateMinusPoints(cards);
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
            cards.add(cfg.card1y);
            cards.add(cfg.card2g);
            player1.updateMinusPoints(cards);
            player2.updateMinusPoints(cards);
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
            cards.add(cfg.card2g);
            cards.add(cfg.card1y);
            player1.updateMinusPoints(cards);
            player2.updateMinusPoints(cards);
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
            cards.add(cfg.card1y);
            cards.add(cfg.card2g);
            player1.updateMinusPoints(cards);
            cards.add(cfg.card11y);
            player2.updateMinusPoints(cards);
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
            cards.add(cfg.card2g);
            cards.add(cfg.card1y);
            player1.updateMinusPoints(cards);
            cards.add(cfg.card11y);
            player2.updateMinusPoints(cards);
            players.add(player1);
            players.add(player2);
            players.add(player3);
            players.add(player4);
            winnerDecision = new WinnerDecision(players);
            winners.add(player3);
            Assertions.assertEquals(winners, winnerDecision.getWinner());
    }

}












