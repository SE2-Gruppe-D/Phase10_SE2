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

    private ArrayList<Cards> getRightHandCards (){ //!!!!Statt ID von Player über Color --> ersetzen!!!!
        switch (player.getColor()){
            case RED: return playfield.getPlayerRed().getPlayerHand();
            case BLUE: return playfield.getPlayerBlue().getPlayerHand();
            case YELLOW: return playfield.getPlayerYellow().getPlayerHand();
            case GREEN: return playfield.getPlayerGreen().getPlayerHand();
            default:return null;
        }
    }
    //GREY = nimm 2 Karten vom Aufnahme- und/oder Ablagestapel
    private void greyFieldColor(){
        do{
            if (defaultcard.isSelected() ) {
                playfield.addCardsDiscardpile();
            }else if(deckcard.isSelected())
                playfield.addCard();
        }
        while(getRightHandCards().size()!=12);
    }

    //GREEN = wähle 1 zufällige Karte aus dem gesamten Ablagestapel aus
    private void greenFieldColor() {
        if (defaultcard.isSelected()) {
            playfield.addRandomCardsDiscardpile();
        }
    }

    //ORANGE = nimm 3 Karten vom Aufnahme- und/oder Ablagestapel
    private void orangeFieldColor(){
        do{
            if (defaultcard.isSelected() ) {
                playfield.addCardsDiscardpile();
            }else if(deckcard.isSelected())
                playfield.addCard();
        }
        while(getRightHandCards().size()!=13);
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
        do{
            if (defaultcard.isSelected() ) {
                playfield.addCardsDiscardpile();
            }else if(deckcard.isSelected())
                playfield.addCard();
        }
        while(getRightHandCards().size()!=11);
        //vorrücken kommt noch
    }








}
