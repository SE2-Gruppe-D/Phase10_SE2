package com.example.phase10_se2;

import com.example.phase10_se2.ENUM.PlayerColor;

import java.util.ArrayList;

public class WinnerDecision
{

    Player playerRed = null;
    Player playerBlue = null;
    Player playerYellow = null;
    Player playerGreen = null;
    ArrayList<Player> actualPlayers = new ArrayList<>();

    //Konstruktor für WinnerDecision, speichern der Spieler in eigene Variablen + einer Sammlung(ArrayList) von Spielern(Spielerliste)
    public WinnerDecision(ArrayList<Player> playerList)
    {
        if (playerList!=null)
        {
            for (Player p : playerList)
            {
                if (p.getColor() != null)
                {
                    if (p.getColor().equals(PlayerColor.RED))
                    {
                        playerRed = p;
                        actualPlayers.add(playerRed);
                    }
                    else if (p.getColor().equals(PlayerColor.BLUE))
                    {
                        playerBlue = p;
                        actualPlayers.add(playerBlue);
                    }
                    else if (p.getColor().equals(PlayerColor.YELLOW))
                    {
                        playerYellow = p;
                        actualPlayers.add(playerYellow);
                    }
                    else if (p.getColor().equals(PlayerColor.GREEN))
                    {
                        playerGreen = p;
                        actualPlayers.add(playerGreen);
                    }
                }
            }
        }
    }

    //gibt eine ArrayList mit den Gewinnern aus
    public ArrayList getWinner()
    {
        ArrayList<Player> winners = new ArrayList<>();
        int phaseCheck = 0;
        int minusPointsCheck = -1;
        Player temporaryWinner = null;

        //den Spieler mit den besten Werten für Phasenumber und minusPoints in temporaryWinner speichern
        for (Player p : actualPlayers)
        {
            if (phaseCheck <= p.getPhaseNumber() && temporaryWinner == null) {
                temporaryWinner = p;
                phaseCheck = temporaryWinner.getPhaseNumber();
                minusPointsCheck = temporaryWinner.getMinusPoints();
            }
            if (p.getPhaseNumber() > phaseCheck) {
                temporaryWinner = p;
                phaseCheck = temporaryWinner.getPhaseNumber();
                minusPointsCheck = temporaryWinner.getMinusPoints();
            }
            if (p.getPhaseNumber() == phaseCheck && p.getMinusPoints() < minusPointsCheck) {
                temporaryWinner = p;
                phaseCheck = temporaryWinner.getPhaseNumber();
                minusPointsCheck = temporaryWinner.getMinusPoints();
            }
        }
        //Jeden Spieler mit den gleichen Werten in die ArrayList speichern und am ende zurückgeben
        for (Player p : actualPlayers)
        {
            if (p.getPhaseNumber() == temporaryWinner.getPhaseNumber() &&p.getMinusPoints() == temporaryWinner.getMinusPoints())
            {
                winners.add(p);
            }
        }
     return winners;
    }
}
