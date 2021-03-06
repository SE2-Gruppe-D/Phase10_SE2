package com.example.phase10_se2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Phase {

    //zur Überprüfung, ob der currentplayer die Phase richtig abgelegt hat
    public boolean getRightPhase(int phasenumber, List<Cards> cardfieldCardlist) {
        switch (phasenumber) {
            case 1:
                return checkPhase1(cardfieldCardlist);
            case 2:
                return checkPhase2(cardfieldCardlist);
            case 3:
                return checkPhase3(cardfieldCardlist);
            case 4:
                return checkPhase4(cardfieldCardlist);
            case 5:
                return checkPhase5(cardfieldCardlist);
            case 6:
                return checkPhase6(cardfieldCardlist);
            case 7:
                return checkPhase7(cardfieldCardlist);
            case 8:
                return checkPhase8(cardfieldCardlist);
            case 9:
                return checkPhase9(cardfieldCardlist);
            case 10:
                return checkPhase10(cardfieldCardlist);
            default:
                return false;

        }
    }

    //check Phase 1 - 10
    //Phase 1: 4 Zwillinge
    public boolean checkPhase1(List<Cards> list) {
        if (list.size() == 8) {
            return checkEqualValue2(list);
        } else {
            return false;
        }
    }


    //Phase 2: 6 Karten einer Farbe
    public boolean checkPhase2(List<Cards> list) {
        if (list.size() == 6) {
            return checkEqualColor(list);
        } else {
            return false;
        }
    }

    //Phase 3: 1 Vierling + 1 Viererfolge
    public boolean checkPhase3(List<Cards> list) {
        if (list.size() == 8) {
            return checkEqualValue4AndRunOf4(list);
        } else {
            return false;
        }
    }

    //Phase 4: 1 Achterfolge
    private boolean checkPhase4(List<Cards> list) {
        if (list.size() == 8) {
            return checkRunOfX(list);
        } else {
            return false;
        }
    }


    //Phase 5: 7 Karten einer Farbe
    private boolean checkPhase5(List<Cards> list) {
        if (list.size() == 7) {
            return checkEqualColor(list);
        } else {
            return false;
        }
    }

    //Phase 6: 1 Neunerfolge
    private boolean checkPhase6(List<Cards> list) {
        if (list.size() == 9) {
            return checkRunOfX(list);
        } else {
            return false;
        }
    }


    //Phase 7: 2 Vierlinge
    private boolean checkPhase7(List<Cards> list) {
        if (list.size() == 8) {
            return checkEqualValue4(list);
        } else {
            return false;
        }
    }


    //Phase 8: 1 Viererfolge einer Farbe + 1 Drilling
    private boolean checkPhase8(List<Cards> list) {
        if (list.size() == 7) {
            return checkRunOfXEqualColorAnd3(list);
        } else {
            return false;
        }
    }

    //Phase 9: 1 Fünfling + 1 Drilling
    private boolean checkPhase9(List<Cards> list) {
        if (list.size() == 8) {
            return checkEqualValue3And5(list);
        } else {
            return false;
        }
    }

    //Phase 10: 1 Fünfling + 1 Dreierfolge einer Farbe
    private boolean checkPhase10(List<Cards> list) {
        if (list.size() == 8) {
            return checkRunOfXEqualColorAnd5(list);
        } else {
            return false;
        }
    }


    //Methoden zur Überprüfung
    //checkEqual value Zwilling, Phase 1
    public boolean checkEqualValue2(List<Cards> list) {
        list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren

        for (int i = 0; i < list.size(); i = i + 2) {
            if (list.get(i).getValue() != list.get(i + 1).getValue()) {
                return false;
            }
        }

        return true;
    }


    //für Phase 7
    public boolean checkEqualValue4(List<Cards> list) {
        list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren
        List<Cards> helplist = new ArrayList<>(list);
        int count = 0;
        Cards helpCard1 = list.get(0);
        Cards helpCard2 = list.get(4);
        for (int i = 0; i < 4; i++) {
            if (helpCard1.getValue() == helplist.get(1).getValue() && count <= 2) {
                helplist.remove(1);
                count++;
            }
        }
        helplist.remove(helpCard1);
        for (int i = 0; i < 3; i++) {
            if (helpCard2.getValue() == helplist.get(1).getValue()) {
                helplist.remove(1);
            }
        }
        helplist.remove(helpCard2);
        return helplist.isEmpty();
    }

    //für Phase 9
    public boolean checkEqualValue3And5(List<Cards> list) {
        list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren
        List<Cards> helplist = new ArrayList<>(list);
        Cards helpCard1 = list.get(0);
        Cards helpCard2 = list.get(7);
        helplist.remove(helpCard1);
        helplist.remove(helpCard2);
        //Die Fünflinge oder Drillinge aus helplist löschen
        for (int i = 0; i < list.size(); i++) {
            if (helpCard1.getValue() == list.get(i).getValue()) {
                helplist.remove(list.get(i));
            }
        }
        for (int i = 0; i < list.size() - 1; i++) {
            if (helpCard2.getValue() == list.get(i).getValue()) {
                helplist.remove(list.get(i));
            }
        }
        return helplist.isEmpty();
    }


    //check Phase 3
    //Sehr große Methode - eventuell aufteilen
    public boolean checkEqualValue4AndRunOf4(List<Cards> list) {
        list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren
        List<Cards> helplist = new ArrayList<>(list);
        List<Cards> helplist2 = new ArrayList<>();
        Cards helpCard;

        //zuerst Folge
        int counter = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            helpCard = list.get(i);
            if ((helpCard.getValue()) == (list.get(i + 1).getValue())) {
                counter = 0;
            } else if ((helpCard.getValue() + 1) == (list.get(i + 1).getValue())) {
                helplist2.add(helpCard);
                counter++;
                if (counter == 3) {
                    helplist2.add(list.get(i + 1));
                    helplist.removeAll(helplist2);
                }
            }
        }

        if (helplist.size() == 4) {
            helpCard = helplist.get(0);
            for (int i = 1; i < 4; i++) {
                if (helpCard.getValue() == helplist.get(1).getValue()) {
                    helplist.remove(1);
                }
            }
            helplist.remove(helpCard);
        }
        return helplist.isEmpty();

    }


    //check equal color, Phase 2 & 5
    private boolean checkEqualColor(List<Cards> list) {
        List<Cards> helplist = new ArrayList<>(list);
        Cards helpCard = list.get(0);
        helplist.remove(helpCard);
        for (int i = 1; i < list.size(); i++) {
            if (helpCard.getColor().equals(helplist.get(0).getColor())) {
                helplist.remove(0);
            }
        }
        return helplist.isEmpty();
    }


    //check run of X, Phase 4 & 6
    private boolean checkRunOfX(List<Cards> list) {
        list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren
        List<Cards> helplist = new ArrayList<>(list);
        Cards helpCard;
        for (int i = 0; i < list.size(); i++) {
            helpCard = helplist.get(0);
            if (helplist.size() == 1) {
                helplist.remove(0);
            } else if ((helpCard.getValue() + 1) == (helplist.get(1).getValue())) {
                helplist.remove(helpCard);
            }
        }
        return helplist.isEmpty();
    }

    //check Phase 8
    //sehr große Methode - eventuell aufteilen
    private boolean checkRunOfXEqualColorAnd3(List<Cards> list) {
        list.sort(Comparator.comparing(Cards::getValue));//2. nach Wert sortieren
        list.sort(Comparator.comparing(Cards::getColor));//1. nach Farbe sortieren

        List<Cards> helplist = new ArrayList<>(list);
        List<Cards> helplist2 = new ArrayList<>();
        Cards helpCard;

        //zuerst Folge
        int counter = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            helpCard = list.get(i);
            if ((helpCard.getValue() + 1) == (list.get(i + 1).getValue()) && helpCard.getColor().equals(list.get(i + 1).getColor())) {
                helplist2.add(helpCard);
                counter++;
                if (counter == 3) {
                    helplist2.add(list.get(i + 1));
                    helplist.removeAll(helplist2);
                }
            } else if (!((helpCard.getValue()) == (list.get(i + 1).getValue()) && helpCard.getColor().equals(list.get(i + 1).getColor()))){
                counter = 0;
            }
        }

        if (helplist.size() == 3) {
            helpCard = helplist.get(0);
            for (int i = 1; i < 3; i++) {
                if (helpCard.getValue() == helplist.get(1).getValue()) {
                    helplist.remove(1);
                }
            }
            helplist.remove(helpCard);
        }
        return helplist.isEmpty();
    }


    //check Phase 10
    //sehr große Methode - eventuell aufteilen
    private boolean checkRunOfXEqualColorAnd5(List<Cards> list) {
        list.sort(Comparator.comparing(Cards::getValue));//2. nach Wert sortieren
        list.sort(Comparator.comparing(Cards::getColor));//1. nach Farbe sortieren

        List<Cards> helplist = new ArrayList<>(list);
        List<Cards> helplist2 = new ArrayList<>();
        Cards helpCard;

        //zuerst Folge
        int counter = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            helpCard = list.get(i);
            if ((helpCard.getValue() + 1) == (list.get(i + 1).getValue()) && helpCard.getColor().equals(list.get(i + 1).getColor())) {
                helplist2.add(helpCard);
                counter++;
                if (counter == 2) {
                    helplist2.add(list.get(i + 1));
                    helplist.removeAll(helplist2);
                }
            } else if (!((helpCard.getValue()) == (list.get(i + 1).getValue()) && helpCard.getColor().equals(list.get(i + 1).getColor()))) {
                counter = 0;
            }
        }

        if (helplist.size() == 5) {
            helpCard = helplist.get(0);
            for (int i = 1; i < 5; i++) {
                if (helpCard.getValue() == helplist.get(1).getValue()) {
                    helplist.remove(1);
                }
            }
            helplist.remove(helpCard);
        }
        return helplist.isEmpty();
    }

    //zur Überprüfung, ob der currentplayer bei einem Mitspieler eine richtige Karte dazu gelegt hat
    public boolean getRightPhaseOtherPlayer(int phasenumber, Cards cards, List<Cards> list){
        switch (phasenumber){
            case 1:
            case 7:
            case 9:
                return checkEqualValueOneCard(cards,list);
            case 2:
            case 5:
                return checkEqualColorOneCard(cards,list);
            case 4:
            case 6:
                return checkFirstOrLastValue(cards,list);
            case 3: return checkPhase3FromOtherPlayer(cards,list);
            case 8: return checkPhase8FromOtherPlayer(cards,list);
            case 10: return checkPhase10FromOtherPlayer(cards,list);
            default: return false;
        }
    }


    //Phase 1: 4 Zwillinge
    //Phase 7: 2 Vierlinge
    //Phase 9: 1 Fünfling + 1 Drilling
    //Überprüfen, ob ich meine Karte zum Mitspieler dazu legen kann (Zwilling, Drilling, Vierling, Fünfling)
    public boolean checkEqualValueOneCard(Cards cards, List<Cards> list) {
        for (int i = 0; i < list.size(); i++) {
            if (cards.getValue() == list.get(i).getValue()) {
                return true;
            }
        }
        return false;
    }

    //Phase 2: 6 Karten einer Farbe
    //Phase 5: 7 Karten einer Farbe
    private boolean checkEqualColorOneCard(Cards cards, List<Cards> list){
        for (int i = 0; i < list.size(); i++) {
            if (cards.getColor().equals(list.get(i).getColor())) {
                return true;
            }
        }
        return false;
    }

    //Phase 4: 1 Achterfolge
    //Phase 6: 1 Neunerfolge
    private boolean checkFirstOrLastValue(Cards cards, List<Cards> list){
        list.sort(Comparator.comparing(Cards::getValue));//2. nach Wert sortieren
        return cards.getValue() + 1 == list.get(0).getValue() || cards.getValue() - 1 == list.get(list.size() - 1).getValue();
    }

    //Phase 3: 1 Vierling + 1 Viererfolge
    private boolean checkPhase3FromOtherPlayer(Cards cards, List<Cards> list){
        list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren
        List<Cards> helplist = new ArrayList<>(list); //Vierling
        List<Cards> helplist2 = new ArrayList<>(); //Folge
        Cards helpCard;
        //zwei Arrays befüllen
        int counter = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            helpCard = list.get(i);
            if ((helpCard.getValue() + 1) == (list.get(i + 1).getValue())) {
                helplist2.add(helpCard);
                counter++;
                if (counter == 3) {
                    helplist2.add(list.get(i + 1));
                    helplist.removeAll(helplist2);
                }
            } else if (!((helpCard.getValue()) == (list.get(i + 1).getValue()) && helpCard.getColor().equals(list.get(i + 1).getColor()))){
                counter = 0;
            }
        }
        return checkFirstOrLastValue(cards, helplist2) || checkEqualValueOneCard(cards, helplist);
    }

    //Phase 8: 1 Viererfolge einer Farbe + 1 Drilling
    private boolean checkPhase8FromOtherPlayer(Cards cards, List<Cards> list){
        list.sort(Comparator.comparing(Cards::getValue));//2. nach Wert sortieren
        list.sort(Comparator.comparing(Cards::getColor));//1. nach Farbe sortieren
        List<Cards> helplist = new ArrayList<>(list); //Drilling
        List<Cards> helplist2 = new ArrayList<>(); //Folge
        Cards helpCard;
        int counter = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            helpCard = list.get(i);
            if ((helpCard.getValue() + 1) == (list.get(i + 1).getValue()) && helpCard.getColor().equals(list.get(i + 1).getColor())) {
                helplist2.add(helpCard);
                counter++;
                if (counter == 3) {
                    helplist2.add(list.get(i + 1));
                    helplist.removeAll(helplist2);
                }
            } else if (!((helpCard.getValue()) == (list.get(i + 1).getValue()) && helpCard.getColor().equals(list.get(i + 1).getColor()))){
                counter = 0;
            }
        }
        return checkFirstOrLastValue(cards, helplist2) || checkEqualValueOneCard(cards, helplist);
    }

    //Phase 10: 1 Fünfling + 1 Dreierfolge einer Farbe
    private boolean checkPhase10FromOtherPlayer(Cards cards, List<Cards> list){
        list.sort(Comparator.comparing(Cards::getValue));//2. nach Wert sortieren
        list.sort(Comparator.comparing(Cards::getColor));//1. nach Farbe sortieren
        List<Cards> helplist = new ArrayList<>(list); //Fünfling
        List<Cards> helplist2 = new ArrayList<>(); //Folge
        Cards helpCard;
        int counter = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            helpCard = list.get(i);
            if ((helpCard.getValue() + 1) == (list.get(i + 1).getValue()) && helpCard.getColor().equals(list.get(i + 1).getColor())) {
                helplist2.add(helpCard);
                counter++;
                if (counter == 2) {
                    helplist2.add(list.get(i + 1));
                    helplist.removeAll(helplist2);
                }
            } else if (!((helpCard.getValue()) == (list.get(i + 1).getValue()) && helpCard.getColor().equals(list.get(i + 1).getColor()))) {
                counter = 0;
            }
        }
        return checkFirstOrLastValue(cards, helplist2) || checkEqualValueOneCard(cards, helplist);
    }
}












