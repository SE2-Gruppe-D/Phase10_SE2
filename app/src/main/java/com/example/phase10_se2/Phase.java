package com.example.phase10_se2;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Phase extends AppCompatActivity {

    Player player;
/*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
    }

 */
    //zur Überprüfung, ob der currentplayer die Phase richtig abgelegt hat
    public boolean getRightPhase(Integer phasenumber, ArrayList<Cards> cardfieldCardlist){
        switch (phasenumber){
            case 1: return checkPhase1(cardfieldCardlist);
            case 2: return checkPhase2(cardfieldCardlist);
            default: return false;
        }
    }

    //zur Überprüfung, ob der currentplayer bei einem Mitspieler eine richtige Karte dazu gelegt hat
    public boolean getRightPhaseOtherPlayer(int phasenumber, Cards cards, Player player){
        switch (phasenumber){
            case 1: return checkPhase1FromOtherPlayer(cards,player);
            case 2: return checkPhase2FromOtherPlayer(cards,player);
            default: return false;
        }
    }

    //check Phase 1 - 10 && check eine Handkarte zur Phase dazulegen
    //Phase 1: 4 Zwillinge
    public boolean checkPhase1(List<Cards> list){
        if (list.size() == 8) {
            return checkEqualValue2(list);
        }else{
            return false;
        }
    }

    public boolean checkPhase1FromOtherPlayer(Cards cards, Player player){
        return checkEqualValueOneCard(cards, player.getCardField());
    }


    //Phase 2: 6 Karten einer Farbe
    public boolean checkPhase2(List<Cards> list) {
        if (list.size() == 6) {
            return checkEqualColor(list);
        } else {
            return false;
        }
    }

    public boolean checkPhase2FromOtherPlayer(Cards cards, Player player){
        return checkEqualColorOneCard(cards, player.getCardField());
    }




    //Phase 3: 1 Vierling + 1 Viererfolge
    public boolean checkPhase3(List<Cards> list) {
        if (list.size() == 8) {

            return checkEqualValue4(list);
        } else {
            return false;
        }
    }

    //Phase 4: 1 Achterfolge
    private boolean checkPhase4(List<Cards> list1){
        return list1.size()==8 && checkRunOfX(list1);
    }


    //Phase 5: 7 Karten einer Farbe
    private boolean checkPhase5(List<Cards> list1){
        return list1.size()==7 && checkEqualColor(list1);
    }

    //Phase 6: 1 Neunerfolge
    private boolean checkPhase6(List<Cards> list1){

        return list1.size()==9 && checkRunOfX(list1);
    }


    //Phase 7: 2 Vierlinge
    private boolean checkPhase7(List<Cards> list1, List<Cards> list2){
        return checkSetOf4(list1) && checkSetOf4(list2);
    }


    //Phase 8: 1 Viererfolge einer Farbe + 1 Drilling
    private boolean checkPhase8(List<Cards> list1, List<Cards> list2){
        return ((list1.size()==4 && checkRunOfX(list1) && checkEqualColor(list1)) && checkSetOf3(list2));
    }

    //Phase 9: 1 Fünfling + 1 Drilling
    private boolean checkPhase9(List<Cards> list1, List<Cards> list2){
        return checkSetOf5(list1) && checkSetOf3(list2);
    }

    //Phase 10: 1 Fünfling + 1 Dreierfolge einer Farbe
    //Problem! zuerst Folge überprüfen?
    private boolean checkPhase10(List<Cards> list1, List<Cards> list2){
        return (checkSetOf5(list1) && (list2.size()==3 && checkRunOfX(list2) && checkEqualColor(list2)));
    }




    //Methoden zur Überprüfung
    //gleicher Zahlenwert
   //checkEqual value Zwilling
   public boolean checkEqualValue2(List<Cards> list){
       list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren
       List<Cards> helplist = new ArrayList<>(list);
       Cards helpCard;
           for (int i = 0; i < list.size(); i=i+2) {
               helpCard = list.get(i);
               if (helplist.size() != 0) {
                   if (helpCard.getValue() == helplist.get(1).getValue()) {
                       helplist.remove(1);
                       helplist.remove(helpCard);
                   }
               }
           }
       return helplist.isEmpty();
   }

    //checkEqual value Vierling
    public boolean checkEqualValue4(List<Cards> list){
        list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren
        List<Cards> helplist = new ArrayList<>(list);
        Cards helpCard;
        for (int i = 0; i < list.size(); i=i+4) {
            helpCard = list.get(i);
            if (helplist.size() != 0) {
                for (int j = 0; j < 3; j++) {
                if (helpCard.getValue() == helplist.get(1).getValue()) {
                    helplist.remove(1);
                }
            }
            }
            helplist.remove(helpCard);
        }
        return helplist.isEmpty();
    }

    public List<Cards> getEqualValue4(List<Cards> list){
        list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren
        List<Cards> helplist = new ArrayList<>(list);
        Cards helpCard;
        for (int i = 0; i < list.size(); i=i+4) {
            helpCard = list.get(i);
            if (helplist.size() != 0) {
                for (int j = 0; j < 3; j++) {
                    if (helpCard.getValue() == helplist.get(1).getValue()) {
                        helplist.remove(1);
                    }
                }
            }
            helplist.remove(helpCard);
        }
        return helplist;
    }

   //Überprüfen, ob ich meine Karte zum Mitspieler dazu legen kann (Zwilling, Drilling, Vierling, Fünfling)
    public boolean checkEqualValueOneCard(Cards cards, List<Cards> list){
        for (int i = 0; i < list.size(); i++) {
            if(cards.getValue()==list.get(i).getValue()){
                return true;
            }
        }
        return false;
    }



   //check equal color
    private boolean checkEqualColor(List<Cards> list){
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

    private boolean checkEqualColorOneCard(Cards cards, List<Cards> list){
        for (int i = 0; i < list.size(); i++) {
            if(cards.getColor().equals(list.get(i).getColor())){
                return true;
            }
        }
        return false;
    }

    //check run of X
    private boolean checkRunOfX(List<Cards> list) {
        list.sort(Comparator.comparing(Cards::getValue));//nach Wert sortieren
        List<Cards> helplist = new ArrayList<>(list);
        Cards helpCard;
        for (int i = 0; i < list.size(); i++) {
            helpCard = list.get(i);
            if (helplist.size() != 0) {
                if ((helpCard.getValue() + 1) == (helplist.get(1).getValue())) {
                    helplist.remove(helpCard);
                    if(helplist.size()==1){
                        helplist.remove(0);
                    }
                }
                }
            }
        return helplist.isEmpty();
        }




    private boolean checkSetOf3(List<Cards> list1){
        return list1.size() == 3 && checkEqualValue2(list1);
    }

    private boolean checkSetOf4(List<Cards> list1){
        return list1.size() == 4 && checkEqualValue2(list1);
    }

    private boolean checkSetOf5(List<Cards> list1){
        return list1.size() == 5 && checkEqualValue2(list1);
    }

    //check sets of 2,3,4,5
    public boolean checkSetOf2(List<Cards> list) {
        if (list.size() == 8) {
            return checkEqualValue2(list);
        }else{
            return false;
        }
    }








}
