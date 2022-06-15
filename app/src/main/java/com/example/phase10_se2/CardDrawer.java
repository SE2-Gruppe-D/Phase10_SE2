package com.example.phase10_se2;

import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDrawer {

    private ArrayList<Cards> initialCardsList=new ArrayList<>();
    private ArrayList<Cards> discardpileList=new ArrayList<>();      //Ablagestapel

    public ArrayList<Cards> getDiscardpileList() {
        return discardpileList;
    }

    public CardDrawer(){
        generateInitialCards();
        shuffleCards(initialCardsList);
    }

    public ArrayList<Cards> getInitialCardsList() {
        return initialCardsList;
    }

    private void initCards(String color, int maxID) {
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards(color, (i%12) + 1, null, i + 1 + maxID-24);
            initialCardsList.add(card);
        }
    }

    public ArrayList<Cards> generateInitialCards(){
        //alle 96 Karten werden in eine ArrayList gespeichert
        //erstelle alle Blauen Karten
        initialCardsList= new ArrayList<>();

        initCards("blue", 24);
        initCards("red", 48);
        initCards("yellow", 72);
        initCards("green", 96);

        return initialCardsList;
    }
    //Karten werden gemischt
    public void shuffleCards(ArrayList<Cards> initialCardsList){
        Collections.shuffle(initialCardsList);
    }

    //Kartenliste leer? Ablagestapel wird gemischt und zu ziehstapel gegeben
    public void isInitialCardsEmpty(){
        if (initialCardsList.isEmpty()){
            initialCardsList=discardpileList;
            shuffleCards(initialCardsList);
        }
    }
}
