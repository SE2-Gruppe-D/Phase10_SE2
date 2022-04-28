package com.example.phase10_se2;

import static android.os.SystemClock.sleep;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Game {
    private final String room;
    private DiceFragment diceFragment;
    private ArrayList<Player> players;
    private int roundsFinished;

    public Game(String room) {
        this.room = room;
        players = new ArrayList<>();
    }


    public void playerJoined(Player player) {
        if (!players.contains(player)) {
            this.players.add(player);
        }
    }

    //Getter and Setters
    public String getRoom() {
        return room;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getRoundsFinished() {
        return roundsFinished;
    }
}
