package com.example.phase10_se2;


import com.example.phase10_se2.ENUM.FieldColor;

public class Actionfield {

    DiceFragment diceFragment = new DiceFragment();
     int cardToPullBoth=0;
     int cardToPullCardlist = 0;
     int cardToPullDiscardpileList = 0;


    public FieldColor getRightFieldColor(int currentPosition){
        switch (currentPosition){
            case 0: return FieldColor.BLUE;
            case 1: return FieldColor.GREY;
            case 2: return FieldColor.GREEN;
            case 3: return FieldColor.ORANGE;
            case 4: return FieldColor.GREY;
            case 5: return FieldColor.RED;
            case 6: return FieldColor.GREY;
            case 7: return FieldColor.PURPLE;
            case 8: return FieldColor.PINK;
            case 9: return FieldColor.BLUE;
            case 10: return FieldColor.GREY;
            case 11: return FieldColor.ORANGE;
            case 12: return FieldColor.GREEN;
            case 13: return FieldColor.GREY;
            case 14: return FieldColor.RED;
            case 15: return FieldColor.PINK;
            default: return null;
        }
    }

    //GREY = nimm 2 Karten vom Zieh- und/oder Ablagestapel
    public void greyFieldColor() {
        cardToPullBoth = 2;
        cardToPullCardlist = 0;
        cardToPullDiscardpileList = 0;
    }


    //GREEN = ziehe 1 zufällige Karte aus dem gesamten Ablagestapel aus
    public void greenFieldColor() {
        cardToPullBoth =0;
        cardToPullCardlist=0;
        cardToPullDiscardpileList=1;
    }

    //ORANGE = nimm 3 Karten vom Zieh- und/oder Ablagestapel
    public void orangeFieldColor() {
        cardToPullBoth = 3;
        cardToPullCardlist = 0;
        cardToPullDiscardpileList = 0;
    }

    //BLUE = nimm eine Karte von Ziehstapel
    public void blueFieldColor() {
        cardToPullBoth = 0;
        cardToPullCardlist = 1;
        cardToPullDiscardpileList = 0;
    }

    //RED =nimm 3 Karten vom Ziehstapel
    public void redFieldColor() {
        cardToPullBoth =0;
        cardToPullCardlist=3;
        cardToPullDiscardpileList=0;
    }

    //PURPLE = der Spieler darf keine Karte ziehen
    public void purpleFieldColor() {
        cardToPullBoth =0;
        cardToPullCardlist=0;
        cardToPullDiscardpileList=0;
    }

    //noch nicht testen, weil noch nicht sicher ob das Würfeln so funktioniert
    //PINK = nimm 1 Karte vom Aufnahme- oder Ziehstapel. Mache einen weiteren Zug
    public void pinkFieldColor() {
        cardToPullBoth =1;
        cardToPullCardlist=0;
        cardToPullDiscardpileList=0;
        diceFragment.setMoved(false);
    }
}
