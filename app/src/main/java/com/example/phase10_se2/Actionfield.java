package com.example.phase10_se2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Actionfield extends AppCompatActivity {

    Playfield playfield;
    ImageView deckcard;
    ImageView defaultcard;
    DiceFragment diceFragment;

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

    //BLUE = nimm eine Karte von Ziehstapel
    private void blueFieldColor(){
        if(deckcard.isSelected()){
            playfield.addCard();
        }
    }

    //RED =nimm 3 Karten vom Ziehstapel
    private void redFieldColor(){
        //ziehen
        int counter = 0;
        do{
            if (deckcard.isSelected()) {
            playfield.addCard();
            counter++;
        }
        }while(counter<3);
    }

    //PURPLE = der Spieler darf keine Karte ziehen
    private void purpleFieldColor(){
        //nothing
    }

    //PINK = nimm 1 Karte vom Aufnahme- oder Ziehstapel. Mache einen weiteren Zug
    private void pinkFieldColor(){
        if (defaultcard.isSelected() ) {
            playfield.addCardsDiscardpile();
            diceFragment.setMoved(false);
        }else if(deckcard.isSelected()){
            playfield.addCard();
            diceFragment.setMoved(false);
        }
    }




}
