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

    public ArrayList<Cards> generateInitialCards(){
        //alle 96 Karten werden in eine ArrayList gespeichert
        //erstelle alle Blauen Karten
        initialCardsList= new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("blue", (i % 12) + 1, null, i+1);  //%12 weil, es wird bei 0 gestartet und immer +1 gerechnet & somit wird jeder Karte doppelt eingefügt
            initialCardsList.add(card);
        }
        //erstelle alle Roten Karten
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("red", (i % 12) + 1, null, i+25);
            initialCardsList.add(card);
        }
        //erstelle alle Gelben Karten
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("yellow", (i % 12) + 1, null, i+49);
            initialCardsList.add(card);
        }
        //erstelle alle Gruenen Karte
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("green", (i % 12) + 1, null, i+73);
            initialCardsList.add(card);
        }
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
