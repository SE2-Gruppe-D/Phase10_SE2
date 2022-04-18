package com.example.phase10_se2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class Playfield extends AppCompatActivity {
    ImageView card1, card2,card3, card4, card5, card6, card7, card8 ,card9 ,card10 ,card11, card12,card13, card14, card15, card16, card17, card18, card19, card20, card21,card22, card23, card24, card25, card26, card27, card28, card29, card30, card31, card32, card33, card34,card35,card36, card37, card38, card39, card40;

    ArrayList<cards> cardlist;
    ArrayList<ImageView> Imagelist;

    ArrayList<cards> player1Hand;
    ArrayList<cards> player2Hand;
    ArrayList<cards> player3Hand;
    ArrayList<cards> player4Hand;

    public ArrayList<cards> getPlayer1Hand() {
        return player1Hand;
    }

    public ArrayList<cards> getPlayer2Hand() {
        return player2Hand;
    }

    public ArrayList<cards> getPlayer3Hand() {
        return player3Hand;
    }

    public ArrayList<cards> getPlayer4Hand() {
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

        cardlist = new ArrayList<>();
        Imagelist = new ArrayList<>();
        player1Hand = new ArrayList<>();
        player2Hand = new ArrayList<>();
        player3Hand = new ArrayList<>();
        player4Hand = new ArrayList<>();

        //alle 96 Karten werden in eine ArrayList gespeichert
        //erstelle alle Blauen Karten
        for (int i = 0; i < 24; i++) {
            cards card = new cards("blue", (i % 12) + 1, null);  //%12 weil, es wird bei 0 gestartet und immer +1 gerechnet & somit wird jeder Karte doppelt eingefügt
            cardlist.add(card);
        }
        //erstelle alle Roten Karten
        for (int i = 0; i < 24; i++) {
            cards card = new cards("red", (i % 12) + 1, null);
            cardlist.add(card);
        }
        //erstelle alle Gelben Karten
        for (int i = 0; i < 24; i++) {
            cards card = new cards("yellow", (i % 12) + 1, null);
            cardlist.add(card);
        }
        //erstelle alle Gruenen Karte
        for (int i = 0; i < 24; i++) {
            cards card = new cards("green", (i % 12) + 1, null);
            cardlist.add(card);
        }

        Imagelist.add(card1 = findViewById(R.id.card1));
        Imagelist.add(card2 = findViewById(R.id.card2));
        Imagelist.add(card3 = findViewById(R.id.card3));
        Imagelist.add(card4 = findViewById(R.id.card4));
        Imagelist.add(card5 = findViewById(R.id.card5));
        Imagelist.add(card6 = findViewById(R.id.card6));
        Imagelist.add(card7 = findViewById(R.id.card7));
        Imagelist.add(card8 = findViewById(R.id.card8));
        Imagelist.add(card9 = findViewById(R.id.card9));
        Imagelist.add(card10 = findViewById(R.id.card10));
        Imagelist.add(card11 = findViewById(R.id.card11));
        Imagelist.add(card12 = findViewById(R.id.card12));
        Imagelist.add(card13 = findViewById(R.id.card13));
        Imagelist.add(card14 = findViewById(R.id.card14));
        Imagelist.add(card15 = findViewById(R.id.card15));
        Imagelist.add(card16 = findViewById(R.id.card16));
        Imagelist.add(card17 = findViewById(R.id.card17));
        Imagelist.add(card18 = findViewById(R.id.card18));
        Imagelist.add(card19 = findViewById(R.id.card19));
        Imagelist.add(card20 = findViewById(R.id.card20));
        Imagelist.add(card21 = findViewById(R.id.card21));
        Imagelist.add(card22 = findViewById(R.id.card22));
        Imagelist.add(card23 = findViewById(R.id.card23));
        Imagelist.add(card24 = findViewById(R.id.card24));
        Imagelist.add(card25 = findViewById(R.id.card25));
        Imagelist.add(card26 = findViewById(R.id.card26));
        Imagelist.add(card27 = findViewById(R.id.card27));
        Imagelist.add(card28 = findViewById(R.id.card28));
        Imagelist.add(card29 = findViewById(R.id.card29));
        Imagelist.add(card30 = findViewById(R.id.card30));
        Imagelist.add(card31 = findViewById(R.id.card31));
        Imagelist.add(card32 = findViewById(R.id.card32));
        Imagelist.add(card33 = findViewById(R.id.card33));
        Imagelist.add(card34 = findViewById(R.id.card34));
        Imagelist.add(card35 = findViewById(R.id.card35));
        Imagelist.add(card36 = findViewById(R.id.card36));
        Imagelist.add(card37 = findViewById(R.id.card37));
        Imagelist.add(card38 = findViewById(R.id.card38));
        Imagelist.add(card39 = findViewById(R.id.card39));
        Imagelist.add(card40 = findViewById(R.id.card40));

        //Karten werden gemischt
        Collections.shuffle(cardlist);

        //Handkarten
        for (int i = 0; i < 10; i++) {
            player1Hand.add(cardlist.get(i));       //Handkarten austeilen
            setCardImage(cardlist.get(i), Imagelist.get(i));        //Image setzen

            player2Hand.add(cardlist.get(i + 10));
            setCardImage(cardlist.get(i + 10), Imagelist.get(i + 10));

            player3Hand.add(cardlist.get(i + 20));
            setCardImage(cardlist.get(i + 20), Imagelist.get(i + 20));

            player4Hand.add(cardlist.get(i + 30));
            setCardImage(cardlist.get(i + 30), Imagelist.get(i + 30));
        }
    }

    private void setCardImage(cards cards, ImageView image){        //Kartenobjekt + Imageview
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