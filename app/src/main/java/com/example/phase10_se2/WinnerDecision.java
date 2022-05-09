package com.example.phase10_se2;

import java.util.ArrayList;

public class WinnerDecision {
    Player playerRed = null;
    Player playerBlue = null;
    Player playerYellow = null;
    Player playerGreen = null;

    public WinnerDecision(ArrayList<Player> playerList)
    {
        for (Player p : playerList)
        {
            if (p.getColor().equals("RED"))
            {
                playerRed = p;
            }
            else if (p.getColor().equals("BLUE"))
            {
                playerBlue = p;
            }
            else if (p.getColor().equals("YELLOW"))
            {
                playerYellow = p;
            }
            else if (p.getColor().equals("GREEN"))
            {
                playerGreen = p;
            }
        }
    }
    
}
