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

    public WinnerDecision(ArrayList<Player> playerList)
    {
        for (Player p : playerList)
        {
            if (p != null)
            {
                if (p.getColor().equals("RED"))
                {
                    playerRed = p;
                    actualPlayers.add(playerRed);
                }
                else if (p.getColor().equals("BLUE"))
                {
                    playerBlue = p;
                    actualPlayers.add(playerBlue);
                }
                else if (p.getColor().equals("YELLOW"))
                {
                    playerYellow = p;
                    actualPlayers.add(playerYellow);
                }
                else if (p.getColor().equals("GREEN"))
                {
                    playerGreen = p;
                    actualPlayers.add(playerGreen);
                }
            }
        }
    }

    public String getWinner()
    {
        StringBuilder sB = new StringBuilder();
        String colorString = "";
        int phaseCheck = 10;
        int minusPointsCheck = -1;
        Player temporaryWinner = null;
        int Winnernumber = 0;
        for (Player p : actualPlayers)
        {
            if(p.getPhasenumber() > phaseCheck && minusPointsCheck == -1)
            {
                minusPointsCheck=p.getMinusPoints();
                temporaryWinner = p;
            }
            else if(p.getPhasenumber() > phaseCheck && p.getMinusPoints()<=minusPointsCheck)
            {
                if (minusPointsCheck == p.getMinusPoints())
                {
                    Winnernumber++;
                }
                minusPointsCheck=p.getMinusPoints();
                temporaryWinner = p;
            }
        }
        if (Winnernumber == 0)
        {
            colorString = temporaryWinner.getColor().toString();
            return colorString;
        }
        else if (Winnernumber != 0)
        {
            for (Player p : actualPlayers)
            {
                if (p.getMinusPoints() == minusPointsCheck)
                {
                    if (sB.toString().isEmpty())
                    {
                        sB.append(p.getColor().toString());
                    }

                    sB.append(" " + p.getColor().toString());
                }
            }
            colorString = sB.toString();
            return colorString;
        }
            return colorString;
    }
}
