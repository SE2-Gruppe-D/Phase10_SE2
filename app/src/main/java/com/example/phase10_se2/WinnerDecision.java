package com.example.phase10_se2;

import android.widget.Toast;

import java.util.ArrayList;

public class WinnerDecision
{

    Player playerRed = null;
    Player playerBlue = null;
    Player playerYellow = null;
    Player playerGreen = null;
    ArrayList<Player> actualPlayers;

    //Konstruktor für WinnerDecision, speichern der Spieler in eigene Variablen + einer Sammlung(ArrayList) von Spielern(Spielerliste)
    public WinnerDecision(ArrayList<Player> playerList)
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
            if (phaseCheck <= p.getPhasenumber() && temporaryWinner == null) {
                temporaryWinner = p;
                phaseCheck = temporaryWinner.getPhasenumber();
                minusPointsCheck = temporaryWinner.getMinusPoints();
            }
            if (p.getPhasenumber() > phaseCheck) {
                temporaryWinner = p;
                phaseCheck = temporaryWinner.getPhasenumber();
                minusPointsCheck = temporaryWinner.getMinusPoints();
            }
            if (p.getPhasenumber() == phaseCheck && p.getMinusPoints() < minusPointsCheck) {
                temporaryWinner = p;
                phaseCheck = temporaryWinner.getPhasenumber();
                minusPointsCheck = temporaryWinner.getMinusPoints();
            }
        }
        //Jeden Spieler mit den gleichen Werten in die ArrayList speichern und am ende zurückgeben
        for (Player p : actualPlayers)
        {
            if (p.getPhasenumber() == temporaryWinner.getPhasenumber() &&p.getMinusPoints() == temporaryWinner.getMinusPoints())
            {
                winners.add(p);
            }
        }
     return winners;
    }
}
