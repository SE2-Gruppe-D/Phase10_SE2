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

    public Phase() {
    }
/*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
    }

 */

    public boolean getRightPhase(ArrayList<Cards> cardfieldCardlist){
        int phase = 1;
        switch (phase){
            case 1: return checkPhase1(cardfieldCardlist);
            default: return false;
        }
    }

    //check Phase 1 - 10
    //Phase 1: 4 Zwillinge
    public boolean checkPhase1(List<Cards> list){
        if (list.size() == 8) {
            return checkSameValue(list);
        }else{
            return false;
        }
    }

    //Phase 2: 6 Karten einer Farbe
    private boolean checkPhase2(List<Cards> list1){
        return list1.size()==6 && checkEqualsColor(list1);
    }


    //Phase 3: 1 Vierling + 1 Viererfolge
    private boolean checkPhase3(List<Cards> list1, List<Cards> list2){
        return checkSetOf4(list1) && (list2.size()==4 && checkRunOfX(list2));
    }

    //Phase 4: 1 Achterfolge
    private boolean checkPhase4(List<Cards> list1){
        return list1.size()==8 && checkRunOfX(list1);
    }


    //Phase 5: 7 Karten einer Farbe
    private boolean checkPhase5(List<Cards> list1){
        return list1.size()==7 && checkEqualsColor(list1);
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
        return ((list1.size()==4 && checkRunOfX(list1) && checkEqualsColor(list1)) && checkSetOf3(list2));
    }

    //Phase 9: 1 Fünfling + 1 Drilling
    private boolean checkPhase9(List<Cards> list1, List<Cards> list2){
        return checkSetOf5(list1) && checkSetOf3(list2);
    }

    //Phase 10: 1 Fünfling + 1 Dreierfolge einer Farbe
    private boolean checkPhase10(List<Cards> list1, List<Cards> list2){
        return (checkSetOf5(list1) && (list2.size()==3 && checkRunOfX(list2) && checkEqualsColor(list2)));
    }


    //check sets of 2,3,4,5
   public boolean checkSetOf2(List<Cards> list) {
       if (list.size() == 8) {
           return checkSameValue(list);
       }else{
           return false;
       }
   }

   public boolean checkSameValue(List<Cards> list){
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



    private boolean checkSetOf3(List<Cards> list1){
        return list1.size() == 3 && checkEqualsValue(list1);
    }

    private boolean checkSetOf4(List<Cards> list1){
        return list1.size() == 4 && checkEqualsValue(list1);
    }

    private boolean checkSetOf5(List<Cards> list1){
        return list1.size() == 5 && checkEqualsValue(list1);
    }

   public boolean checkEqualsValue(List<Cards> list1){
       for (int i = 0; i < list1.size() - 1; i++) {
           if (list1.get(i).getValue() != list1.get(i+1).getValue()) {
               return false;
           }
       }
       return true;
   }

   //check equals color
    private boolean checkEqualsColor(List<Cards> list1){
        for (int i = 0; i < list1.size() - 1; i++) {
            if (!list1.get(i).getColor().equals(list1.get(i + 1).getColor())) {
                return false;
            }
        }
        return true;
    }

    //check run of X
    private boolean checkRunOfX(List<Cards> list1){
        for (int i = 0; i < list1.size() - 1; i++) {
            if ((list1.get(i).getValue())+1 != list1.get(i+1).getValue()) {
                return false;
            }
        }
        return true;
    }








}
