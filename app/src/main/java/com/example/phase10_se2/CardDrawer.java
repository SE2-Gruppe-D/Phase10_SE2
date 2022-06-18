package com.example.phase10_se2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDrawer {

    private ArrayList<Cards> initialCardsList = new ArrayList<>();
    private final ArrayList<Cards> discardpileList = new ArrayList<>();      //Ablagestapel

    public CardDrawer() {
        generateInitialCards();
        shuffleCards(initialCardsList);
    }

    public List<Cards> getDiscardpileList() {
        return discardpileList;
    }

    public List<Cards> getInitialCardsList() {
        return initialCardsList;
    }

    private void initCards(String color, int maxID) {
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards(color, (i % 12) + 1, null, i + 1 + maxID - 24);
            initialCardsList.add(card);
        }
    }

    public List<Cards> generateInitialCards() {
        //alle 96 Karten werden in eine ArrayList gespeichert
        //erstelle alle Blauen Karten
        initialCardsList = new ArrayList<>();

        initCards("blue", 24);
        initCards("red", 48);
        initCards("yellow", 72);
        initCards("green", 96);

        return initialCardsList;
    }

    //Karten werden gemischt
    public void shuffleCards(List<Cards> initialCardsList) {
        Collections.shuffle(initialCardsList);
    }

    //Kartenliste leer? Ablagestapel wird gemischt und zu ziehstapel gegeben
    public void isInitialCardsEmpty() {
        if (initialCardsList.isEmpty()) {
            initialCardsList = discardpileList;
            shuffleCards(initialCardsList);
        }
    }
}
