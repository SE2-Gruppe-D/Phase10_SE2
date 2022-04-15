package com.example.phase10_se2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Phase {

    ArrayList<Cards> handcards;
    Playfield playfield;
    Player player;
    int phase = 1; //!!!!Statt "aktuelle Phase prüfen" --> danach ersetzen!!!!

    private ArrayList<Cards> getRightHandCards (){ //!!!!Statt ID von Player über Color --> ersetzen!!!!
        switch (player.getColor()){
            case RED: return playfield.getPlayer1Hand();
            case BLUE: return playfield.getPlayer2Hand();
            case YELLOW: return playfield.getPlayer3Hand();
            case GREEN: return playfield.getPlayer4Hand();
            default:return null;
        }
    }

    private List<Cards> layPhase(){
        //!!!!aktuelle Phase prüfen -->  ersetzen!!!!
        switch (phase) {
            case 1:
                return layPhase1();
                /*
            case 2:
                return checkPhase2();
            case 3:
                return checkPhase3();
            case 4:
                return checkPhase4();
            case 5:
                return checkPhase5();
            case 6:
                return checkPhase6();
            case 7:
                return checkPhase7();
            case 8:
                return checkPhase8();
            case 9:
                return checkPhase9();
            case 10:
                return checkPhase10();

                 */
            default:
                return null;
        }
    }

    //check Phase 1 - 10
    //Phase 1: 4 Zwillinge
    public boolean checkPhase1(List<Cards> list1, List<Cards> list2, List<Cards> list3, List<Cards> list4){
        return checkSetOf2(list1) && checkSetOf2(list2) && checkSetOf2(list3) && checkSetOf2(list4);
    }

    //beginnt, sobald man eine Karte rauslegt
    public ArrayList<Cards> layPhase1(){
        handcards = getRightHandCards();
        //Karten rauslegen
        List<Cards> cards1 = new ArrayList<>();
        cards1.add(handcards.get(0)); //Die erste Karte, die man raus legt?????
        cards1.add(handcards.get(1));
        List<Cards> cards2 = new ArrayList<>();
        cards2.add(handcards.get(2));
        cards2.add(handcards.get(3));
        List<Cards> cards3 = new ArrayList<>();
        cards3.add(handcards.get(4));
        cards3.add(handcards.get(5));
        List<Cards> cards4 = new ArrayList<>();
        cards4.add(handcards.get(6));
        cards4.add(handcards.get(7));

        //überprüfen, ob richtig ist
        if(checkPhase1(cards1,cards2,cards3,cards4)){
            ArrayList<Cards> phase = new ArrayList<>();
            phase.addAll(cards1);
            phase.addAll(cards2);
            phase.addAll(cards3);
            phase.addAll(cards4);
            return phase; //Karten bleiben liegen
        }else{
            return null; //Keine Karte bleibt liegen
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
   public boolean checkSetOf2(List<Cards> list1){
       return list1.size() == 2 && checkEqualsValue(list1);
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
