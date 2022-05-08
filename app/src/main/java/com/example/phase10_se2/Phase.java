package com.example.phase10_se2;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Phase extends AppCompatActivity {

    Player player;
    Playfield playfield;
    ArrayList<Cards> handcards;
    LinearLayout ablageLayoutPlayer1;
    Cards hilfskarte;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
    }
/*
        ablageLayoutPlayer1= findViewById(R.id.player1PhaseAblegen);
        handcards = getRightHandCards();
        List<Cards> zwillinge = new ArrayList<>();
        final int[] id = new int[1];
        handcards.get(0).getCardUI().setOnClickListener(view -> {
            id[0] = view.getId();
        });

        for(int i = 0; i < handcards.size(); i++){
            if(handcards.get(i).getCardUI().getId() == id[0]){
                zwillinge.add(handcards.get(i));
                handcards.remove(i);
                ablageLayoutPlayer1.addView(zwillinge.get(0).getCardUI());
                zwillinge.get(0).getCardUI().setVisibility(View.VISIBLE);
            }
        }



        handcards.get(0).getCardUI().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id[0] = view.getId();
            }
        });
        for(int i = 0; i < handcards.size(); i++){
            if(handcards.get(i).getCardUI().getId() == id[0]){
                zwillinge.add(handcards.get(i));
                handcards.remove(i);
                ablageLayoutPlayer1.addView(zwillinge.get(0).getCardUI());
                zwillinge.get(0).getCardUI().setVisibility(View.VISIBLE);
            }
        }
    }

 */
    /*
    private ArrayList<Cards> getRightHandCards (){ //!!!!Statt ID von Player über Color --> ersetzen!!!!
        switch (player.getColor()){

            case RED:  ablageLayoutPlayer1.findViewById(R.id.player1PhaseAblegen);
                return playfield.getPlayerRed().getPlayerHand();
            case BLUE: ablageLayoutPlayer1.findViewById(R.id.player1PhaseAblegen);
                return playfield.getPlayerBlue().getPlayerHand();
            case YELLOW: ablageLayoutPlayer1.findViewById(R.id.player1PhaseAblegen);
                return playfield.getPlayerYellow().getPlayerHand();
            case GREEN: ablageLayoutPlayer1.findViewById(R.id.player1PhaseAblegen);
                return playfield.getPlayerGreen().getPlayerHand();
            default:return null;
        }
    }

     */

    public List<Cards> getRightPhase(ArrayList<Cards> cardfieldCardlist){
        int phase = 1;
        List<Cards> list1 = new ArrayList<>();
        List<Cards> list2 = new ArrayList<>();
        List<Cards> list3 = new ArrayList<>();
        List<Cards> list4 = new ArrayList<>();

        switch (phase){
            case 1: list1.add(cardfieldCardlist.get(0));
                list1.add(cardfieldCardlist.get(1));
                cardfieldCardlist.removeAll(list1);
                list2.add(cardfieldCardlist.get(2));
                list2.add(cardfieldCardlist.get(3));
                cardfieldCardlist.removeAll(list2);
                list3.add(cardfieldCardlist.get(4));
                list3.add(cardfieldCardlist.get(5));
                cardfieldCardlist.removeAll(list3);
                list4.add(cardfieldCardlist.get(6));
                list4.add(cardfieldCardlist.get(7));
                cardfieldCardlist.removeAll(list4);
               return list1; list2;list3,list4;


        }
    }

    //check Phase 1 - 10
    //Phase 1: 4 Zwillinge
    public boolean checkPhase1(List<Cards> list1, List<Cards> list2, List<Cards> list3, List<Cards> list4){
        return checkSetOf2(list1) && checkSetOf2(list2) && checkSetOf2(list3) && checkSetOf2(list4);
    }

    public void layPhase1(){

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
        //mit Button
        if(!checkPhase1(cards1,cards2,cards3,cards4)){
            //Karten zurück zu den Handkarten
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
