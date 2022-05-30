package com.example.phase10_se2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Actionfield extends AppCompatActivity {

    Player player;
    Playfield playfield;
    ImageView deckcard;
    ImageView defaultcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfield);
        deckcard = findViewById(R.id.deckblatt);
        defaultcard = findViewById(R.id.defaultcard);
    }

    private void getActionfield(FieldColor fieldColor){
        switch (fieldColor){
            case GREY:  greyFieldColor();
            case GREEN:  greenFieldColor();
            case ORANGE:  orangeFieldColor();
            case BLUE:  blueFieldColor();
            case RED:  redFieldColor();
            case PURPLE:  purpleFieldColor();
            case PINK:  pinkFieldColor();
        }
    }


    //GREY = nimm 2 Karten vom Aufnahme- und/oder Ablagestapel
    private void greyFieldColor(){
        int counter =0;
        do{
            if (defaultcard.isSelected() ) {
                playfield.addCardsDiscardpile();
                counter++;
            }else if(deckcard.isSelected())
                playfield.addCard();
            counter++;
        }
        while(counter<2);
    }

    //GREEN = ziehe 1 zufällige Karte aus dem gesamten Ablagestapel aus
    private void greenFieldColor() {
        if (defaultcard.isSelected()) {
            playfield.addRandomCardsDiscardpile();
        }
    }

    //ORANGE = nimm 3 Karten vom Aufnahme- und/oder Ablagestapel
    private void orangeFieldColor(){
        int counter =0;
        do{
            if (defaultcard.isSelected() ) {
                playfield.addCardsDiscardpile();
                counter++;
            }else if(deckcard.isSelected())
                playfield.addCard();
            counter++;
        }
        while(counter<3);
    }

    //BLUE = rücke vor bis zu einem Feld deiner Wahl
    private void blueFieldColor(){
      //vorrücken kommt noch
    }

    //RED = Lege 2 Karten auf den Ablagestapel und nimm 3 vom Aufnahmestapel
    private void redFieldColor(){
        //legen kommt nocht
        if (deckcard.isSelected()) {
            playfield.addCard();
        }
    }

    //PURPLE = alle Spieler nehmen reihum 1 Karte vom Aufnahmestapel
    private void purpleFieldColor(){
        if (deckcard.isSelected()) {
            playfield.addCard();
        }
    }

    //PINK = nimm 1 Karte vom Aufnahme- oder Ablagestapel. Mache einen weiteren Zug
    private void pinkFieldColor(){
        int counter =0;
        do{
            if (defaultcard.isSelected() ) {
                playfield.addCardsDiscardpile();
                counter++;
            }else if(deckcard.isSelected())
                playfield.addCard();
            counter++;
        }
        while(counter<1);
        //vorrücken kommt noch
    }








}
