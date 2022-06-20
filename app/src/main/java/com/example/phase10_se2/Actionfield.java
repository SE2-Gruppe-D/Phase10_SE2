package com.example.phase10_se2;

import com.example.phase10_se2.enums.FieldColor;

public class Actionfield {

    DiceFragment diceFragment = new DiceFragment();
     int cardToPullBoth=0;
     int cardToPullCardlist = 0;
     int cardToPullDiscardpileList = 0;
     boolean randomCard = false;


    public FieldColor getRightFieldColor(int currentPosition){
        switch (currentPosition){
            case 0:
            case 9:
                return FieldColor.PURPLE;
            case 1:
            case 4:
            case 6:
            case 10:
            case 13:
                return FieldColor.PURPLE;
            case 2:
            case 12:
                return FieldColor.PURPLE;
            case 3:
            case 11:
                return FieldColor.PURPLE;
            case 5:
            case 14:
                return FieldColor.PURPLE;
            case 7: return FieldColor.PURPLE;
            case 8:
            case 15:
                return FieldColor.PURPLE;
            default: return null;
        }
    }

    //GREY = nimm 2 Karten vom Zieh- und/oder Ablagestapel
    public void greyFieldColor() {
        randomCard = false;
        cardToPullBoth = 2;
        cardToPullCardlist = 0;
        cardToPullDiscardpileList = 0;
    }


    //GREEN = ziehe 1 zufällige Karte aus dem gesamten Ablagestapel aus
    public void greenFieldColor() {
        randomCard=true;
        cardToPullBoth =0;
        cardToPullCardlist=0;
        cardToPullDiscardpileList=1;
    }

    //ORANGE = nimm 3 Karten vom Zieh- und/oder Ablagestapel
    public void orangeFieldColor() {
        randomCard = false;
        cardToPullBoth = 3;
        cardToPullCardlist = 0;
        cardToPullDiscardpileList = 0;
    }

    //BLUE = nimm eine Karte von Ziehstapel
    public void blueFieldColor() {
        randomCard = false;
        cardToPullBoth = 0;
        cardToPullCardlist = 1;
        cardToPullDiscardpileList = 0;
    }

    //RED =nimm 3 Karten vom Ziehstapel
    public void redFieldColor() {
        randomCard = false;
        cardToPullBoth = 0;
        cardToPullCardlist = 3;
        cardToPullDiscardpileList = 0;
    }

    //PURPLE = der Spieler darf keine Karte ziehen
    public void purpleFieldColor() {
        randomCard = false;
        cardToPullBoth = 0;
        cardToPullCardlist = 0;
        cardToPullDiscardpileList = 0;
    }

    //noch nicht testen, weil noch nicht sicher ob das Würfeln so funktioniert
    //PINK = nimm 1 Karte vom Aufnahme- oder Ziehstapel. Mache einen weiteren Zug
    public void pinkFieldColor() {
        randomCard = false;
        cardToPullBoth = 1;
        cardToPullCardlist = 0;
        cardToPullDiscardpileList = 0;
//        diceFragment.setMoved(false); //TODO: FIX
    }
}
