package com.example.phase10_se2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
//ToDo: Reihenfolge - Würfel, Aktion(ziehen,ablegen), Karten auslegen/Karten ablegen, Karte ablegen, Karte auslegen/dazu legen/ablegen
//Leon move, actionfield einbinden, nocheinmal würfel,
//jeder Spieler in der Reihe eine Karte
//blue, purple, pink
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


    //GREY = nimm 2 Karten vom Zieh- und/oder Ablagestapel
    private void greyFieldColor(){
        int counter =0;
        do{
            if (defaultcard.isSelected() ) {
                playfield.addCardsDiscardpile();
                counter++;
            }else if(deckcard.isSelected()) {
                playfield.addCard();
                counter++;
            }
        }
        while(counter<2);
    }

    //GREEN = ziehe 1 zufällige Karte aus dem gesamten Ablagestapel aus
    private void greenFieldColor() {
        if (defaultcard.isSelected()) {
            playfield.addRandomCardsDiscardpile();
        }
    }

    //ORANGE = nimm 3 Karten vom Zieh- und/oder Ablagestapel
    private void orangeFieldColor(){
        int counter =0;
        do{
            if (defaultcard.isSelected() ) {
                playfield.addCardsDiscardpile();
                counter++;
            }else if(deckcard.isSelected()) {
                playfield.addCard();
                counter++;
            }
        }
        while(counter<3);
    }

    //BLUE = rücke vor bis zu einem Feld deiner Wahl
    private void blueFieldColor(){
      //ToDO: Leon -> vorrücken - move 1 Feld mit Button??
    }

    //RED = Lege 2 Karten auf den Ablagestapel und nimm 3 vom Ziehstapel
    private void redFieldColor(){
        //ablegen
        int size = playfield.discardpileList.size()+2;//2
        int size2;//0
        do{
            size2 = playfield.discardpileList.size();
        }while(size2!=size);

        //ziehen
        int counter = 0;
        do{
            if (deckcard.isSelected()) {
            playfield.addCard();
            counter++;
        }
        }while(counter<3);
    }

    //PURPLE = alle Spieler nehmen reihum 1 Karte vom Ziehstapel
    private void purpleFieldColor(){
        //Spieleranzahl auslesen, mit counter vergleichen, jeder zieht (aber nicht reihum)
        if (deckcard.isSelected()) {
            playfield.addCard();
            //ToDO reihum
        }
    }

    //PINK = nimm 1 Karte vom Aufnahme- oder Ziehstapel. Mache einen weiteren Zug
    private void pinkFieldColor(){
        if (defaultcard.isSelected() ) {
            playfield.addCardsDiscardpile();
        }else if(deckcard.isSelected()){
            playfield.addCard();
        }

        //ToDO: Leon --> nochmal würfeln
    }








}
