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
    private final ArrayList<Player> players;
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

    public void throwingDice(Player player) {
        int diceValue = -1;

        diceFragment.register();

        while (diceFragment.getAcceleration() < 1) { //wait for sensor to be activated
            sleep(100);
        }
        while (diceFragment.getAcceleration() > 1) {
            diceValue = diceFragment.getLastDiceValue();
        }

        diceFragment.unregister();

        player.move(diceValue);
    }

    public SortedMap<Integer, Player> decideStartingOrder() {
        diceFragment = new DiceFragment();
        SortedMap<Integer, Player> sm = new TreeMap<>();

        for (Player p : players) {
            int diceValue = -1;

            diceFragment.register();
            Toast.makeText(diceFragment.getActivity().getApplicationContext(), "Spieler " + p.getName() + " am Zug", Toast.LENGTH_SHORT).show();

            while (diceFragment.getAcceleration() < 1) { //wait for sensor to be activated
                sleep(100);
            }
            while (diceFragment.getAcceleration() > 1) {
                diceValue = diceFragment.getLastDiceValue();
            }

            diceFragment.unregister();

            sm.put(diceValue, p);
        }

        //set starting order in player class
        Set<Map.Entry<Integer, Player>> s = sm.entrySet();
        Iterator<Map.Entry<Integer, Player>> i = s.iterator();
        StringBuilder startingOrderToastText = new StringBuilder();
        int j = 1;
        while (i.hasNext()) {
            Map.Entry<Integer, Player> m = i.next();

            Player p = m.getValue();
            p.setStartingOrder(j);
            startingOrderToastText.append(j).append(": ").append((m.getValue()).getName());
            j++;
        }

        Toast.makeText(diceFragment.getActivity().getApplicationContext(), startingOrderToastText.toString(), Toast.LENGTH_LONG).show();

        return sm; //return sorted map with players and starting diceValues
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
