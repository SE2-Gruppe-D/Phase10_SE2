package com.example.phase10_se2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Phase {

    /*
    Phase 2: 6 Karten einer Farbe

    //Phase 4: 1 Achterfolge
    //Phase 5: 7 Karten einer Farbe
    //Phase 6: 1 Neunerfolge
    //Phase 7: 2 Vierlinge
    //Phase 8: 1 Viererfolge einer Farbe + 1 Drilling
    //Phase 9: 1 Fünfling + 1 Drilling
    //Phase 10: 1 Fünfling + 1 Dreierfolge einer Farbe
     */

    //check Phase 1 - 10
    //Phase 1: 4 Zwillinge
    private boolean checkPhase1(List<Card> list1, List<Card> list2, List<Card> list3, List<Card> list4){
        return checkSetOf2(list1) && checkSetOf2(list2) && checkSetOf2(list3) && checkSetOf2(list4);
    }

    //Phase 2: 6 Karten einer Farbe


    //Phase 3: 1 Vierling + 1 Viererfolge


    //Phase 4: 1 Achterfolge


    //Phase 5: 7 Karten einer Farbe


    //Phase 6: 1 Neunerfolge


    //Phase 7: 2 Vierlinge
    private boolean checkPhase7(List<Card> list1, List<Card> list2){
        return checkSetOf4(list1) && checkSetOf4(list2);
    }


    //Phase 8: 1 Viererfolge einer Farbe + 1 Drilling


    //Phase 9: 1 Fünfling + 1 Drilling
    private boolean checkPhase9(List<Card> list1, List<Card> list2){
        return checkSetOf5(list1) && checkSetOf3(list2);
    }

    //Phase 10: 1 Fünfling + 1 Dreierfolge einer Farbe


    //check sets of 2,3,4,5
   private boolean checkSetOf2(List<Card> list1){
       return list1.size() == 2 && checkEqualsValue(list1);
   }

    private boolean checkSetOf3(List<Card> list1){
        return list1.size() == 3 && checkEqualsValue(list1);
    }

    private boolean checkSetOf4(List<Card> list1){
        return list1.size() == 4 && checkEqualsValue(list1);
    }

    private boolean checkSetOf5(List<Card> list1){
        return list1.size() == 5 && checkEqualsValue(list1);
    }

   private boolean checkEqualsValue(List<Card> list1){
       for (int i = 0; i < list1.size() - 1; i++) {
           if (list1.get(i).getValue() != list1.get(i+1).getValue()) {
               return false;
           }
       }
       return true;
   }









}
