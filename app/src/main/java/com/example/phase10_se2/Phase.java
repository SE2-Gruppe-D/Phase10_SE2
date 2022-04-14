package com.example.phase10_se2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Phase {

    Card []array  = new Card[2];

    /*
    Phase 1: 4 Zwillinge
    Phase 2: 6 Karten einer Farbe
    Phase 3: 1 Vierling + 1 Viererfolge
    Phase 4: 1 Achterfolge
    Phase 5: 7 Karten einer Farbe
    Phase 6: 1 Neunerfolge
    Phase 7: 2 Vierlinge
    Phase 8: 1 Viererfolge einer Farbe + 1 Drilling
    Phase 9: 1 Fünfling + 1 Drilling
    Phase 10: 1 Fünfling + 1 Dreierfolge einer Farbe
     */

    //check Phase 1 - 10
    private boolean checkPhase1(Card[] array1, List<Card> list2, List<Card> list3, List<Card> list4){
       return false;
    }

   // List <Card> list1 = new ArrayList<Card>();
    //check sets of 2,3,4,5
   public boolean checkSetOf2(List<Card> list1){
       if(list1.size() != 2) {
           return false;
       }else{
           for (int i = 0; i < list1.size() - 1; i++) {
               if (list1.get(i).getValue() != list1.get(i+1).getValue()) {
                   return false;
               }
           }
       }
       return true;
   }



}
