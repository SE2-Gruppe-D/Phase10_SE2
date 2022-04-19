package com.example.phase10_se2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playfield extends AppCompatActivity {
    ImageView deckcard;
    LinearLayout layoutPlayer1;
    LinearLayout layoutPlayer2;
    LinearLayout layoutPlayer3;
    LinearLayout layoutPlayer4;


    ArrayList<Cards> cardlist;
    ArrayList<ImageView> Imagelist;
    ArrayList<Cards> drawpileList;      //Ablagestapel

    ArrayList<Cards> player1Hand;
    ArrayList<Cards> player2Hand;
    ArrayList<Cards> player3Hand;
    ArrayList<Cards> player4Hand;

    public ArrayList<Cards> getPlayer1Hand() {
        return player1Hand;
    }

    public ArrayList<Cards> getPlayer2Hand() {
        return player2Hand;
    }

    public ArrayList<Cards> getPlayer3Hand() {
        return player3Hand;
    }

    public ArrayList<Cards> getPlayer4Hand() {
        return player4Hand;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfield);

        //entfernt die label Leiste (Actionbar) auf dem Playfield
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();

        drawpileList= new ArrayList<>();
        cardlist = new ArrayList<>();
        Imagelist = new ArrayList<>();
        player1Hand = new ArrayList<>();
        player2Hand = new ArrayList<>();
        player3Hand = new ArrayList<>();
        player4Hand = new ArrayList<>();

        //alle 96 Karten werden in eine ArrayList gespeichert
        //erstelle alle Blauen Karten
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("blue", (i % 12) + 1, null, i+1);  //%12 weil, es wird bei 0 gestartet und immer +1 gerechnet & somit wird jeder Karte doppelt eingefügt
            cardlist.add(card);
        }
        //erstelle alle Roten Karten
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("red", (i % 12) + 1, null, i+25);
            cardlist.add(card);
        }
        //erstelle alle Gelben Karten
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("yellow", (i % 12) + 1, null, i+49);
            cardlist.add(card);
        }
        //erstelle alle Gruenen Karte
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("green", (i % 12) + 1, null, i+73);
            cardlist.add(card);
        }

        //dynamisches erstellen der Karten ImageViews
        for(int i=0; i<96; i++){
            cardlist.get(i).setCardUI(createCardUI(cardlist.get(i)));
        }

        drawpileList.addAll(cardlist);

        deckcard= findViewById(R.id.deckblatt);
        layoutPlayer1=findViewById(R.id.player1);
        layoutPlayer2=findViewById(R.id.player2);
        layoutPlayer3=findViewById(R.id.player3);
        layoutPlayer4=findViewById(R.id.player4);

        //Karten werden gemischt
        Collections.shuffle(cardlist);

        //Handkarten
        for(int i = 0; i<10;i++){
            updateHand(player1Hand, cardlist.get(0), layoutPlayer1,0);

            updateHand(player2Hand, cardlist.get(0), layoutPlayer2, 0);

            updateHand(player3Hand, cardlist.get(0), layoutPlayer3, 90);

            updateHand(player4Hand, cardlist.get(0), layoutPlayer4, -90);
        }

        //Player Blue, Red, Yellow, Green
        deckcard.setOnClickListener(view -> {
            addCard();
        });

    }
    //Karten werden angeordnet
    private void updateHand(List list, Cards cards, LinearLayout linearLayout, int grad){
        list.add(cards);
        linearLayout.addView(cards.getCardUI());
        //cards.getCardUI().setVisibility(View.VISIBLE);
        drawpileList.add(cardlist.get(0));
        cardlist.remove(0);
        cards.getCardUI().setRotation(grad);
    }

    //Karte ziehen
    private void addCard(){
        updateHand(player1Hand, cardlist.get(0), layoutPlayer1,0);
        updateHand(player2Hand, cardlist.get(0), layoutPlayer2,0);
        updateHand(player3Hand, cardlist.get(0), layoutPlayer3,90);
        updateHand(player4Hand, cardlist.get(0), layoutPlayer4,-90);
    }

    //Stapel leer
    private void resetDrawPile(){
        cardlist.addAll(drawpileList);
        Collections.shuffle(cardlist);
        //drawpileList.removeAll(drawpileList);
    }

    private ImageView createCardUI(Cards cards){
        ImageView imageView= new ImageView(getApplicationContext());
        setCardImage(cards, imageView);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(35, 120, 1);
        imageView.setLayoutParams(params);
        imageView.setTag("c"+ cards.getID());
        //imageView.setVisibility(View.INVISIBLE);
        return imageView;
    }

    private void setCardImage(Cards cards, ImageView image){
        cards.setCardUI(image);         //Imageview in card setzen
        int Id=0;       //grafik von Imageview setzen

        if(cards.getColor().equals("blue")){
            Id=Id+100;

        }else if(cards.getColor().equals("yellow")){
            Id=Id+200;

        }else if(cards.getColor().equals("green")){
            Id=Id+300;

        }else if(cards.getColor().equals("red")){
            Id=Id+400;
        }
        Id=Id+cards.getValue();
        cardID(Id, image);
    }

    //ID zuweisung Karten
    private void cardID(int card, ImageView image) {
        switch (card) {
            case (101):
                image.setImageResource(R.drawable.blau1);
                break;

            case (102):
                image.setImageResource(R.drawable.blau2);
                break;

            case (103):
                image.setImageResource(R.drawable.blau3);
                break;

            case (104):
                image.setImageResource(R.drawable.blau4);
                break;

            case (105):
                image.setImageResource(R.drawable.blau5);
                break;

            case (106):
                image.setImageResource(R.drawable.blau6);
                break;

            case (107):
                image.setImageResource(R.drawable.blau7);
                break;

            case (108):
                image.setImageResource(R.drawable.blau8);
                break;

            case (109):
                image.setImageResource(R.drawable.blau9);
                break;

            case (110):
                image.setImageResource(R.drawable.blau10);
                break;

            case (111):
                image.setImageResource(R.drawable.blau11);
                break;

            case (112):
                image.setImageResource(R.drawable.blau12);
                break;


            case (201):
                image.setImageResource(R.drawable.gelb1);
                break;

            case (202):
                image.setImageResource(R.drawable.gelb2);
                break;

            case (203):
                image.setImageResource(R.drawable.gelb3);
                break;

            case (204):
                image.setImageResource(R.drawable.gelb4);
                break;

            case (205):
                image.setImageResource(R.drawable.gelb5);
                break;

            case (206):
                image.setImageResource(R.drawable.gelb6);
                break;

            case (207):
                image.setImageResource(R.drawable.gelb7);
                break;

            case (208):
                image.setImageResource(R.drawable.gelb8);
                break;

            case (209):
                image.setImageResource(R.drawable.gelb9);
                break;

            case (210):
                image.setImageResource(R.drawable.gelb10);
                break;

            case (211):
                image.setImageResource(R.drawable.gelb11);
                break;

            case (212):
                image.setImageResource(R.drawable.gelb12);
                break;

            case (301):
                image.setImageResource(R.drawable.gruen1);
                break;

            case (302):
                image.setImageResource(R.drawable.gruen2);
                break;

            case (303):
                image.setImageResource(R.drawable.gruen3);
                break;

            case (304):
                image.setImageResource(R.drawable.gruen4);
                break;

            case (305):
                image.setImageResource(R.drawable.gruen5);
                break;

            case (306):
                image.setImageResource(R.drawable.gruen6);
                break;

            case (307):
                image.setImageResource(R.drawable.gruen7);
                break;

            case (308):
                image.setImageResource(R.drawable.gruen8);
                break;

            case (309):
                image.setImageResource(R.drawable.gruen9);
                break;

            case (310):
                image.setImageResource(R.drawable.gruen10);
                break;

            case (311):
                image.setImageResource(R.drawable.gruen11);
                break;

            case (312):
                image.setImageResource(R.drawable.gruen12);
                break;

            case (401):
                image.setImageResource(R.drawable.rot1);
                break;

            case (402):
                image.setImageResource(R.drawable.rot2);
                break;

            case (403):
                image.setImageResource(R.drawable.rot3);
                break;

            case (404):
                image.setImageResource(R.drawable.rot4);
                break;

            case (405):
                image.setImageResource(R.drawable.rot5);
                break;

            case (406):
                image.setImageResource(R.drawable.rot6);
                break;

            case (407):
                image.setImageResource(R.drawable.rot7);
                break;

            case (408):
                image.setImageResource(R.drawable.rot8);
                break;

            case (409):
                image.setImageResource(R.drawable.rot9);
                break;

            case (410):
                image.setImageResource(R.drawable.rot10);
                break;

            case (411):
                image.setImageResource(R.drawable.rot11);
                break;

            case (412):
                image.setImageResource(R.drawable.rot12);
                break;

        }
    }
}