package com.example.phase10_se2;

import android.graphics.Color;
import android.widget.ImageView;

import com.google.firebase.database.collection.LLRBNode;

public class CardUIManager {

    void setCardImage(Cards cards, ImageView image) {
        int id = cards.getValue();
        String color = cards.getColor();

        cardID(id, image, color);
    }

    //ID zuweisung Karten
    public void cardID(int card, ImageView image, String color) {

        switch (color) {
            case ("blue"):
                switch (card) {
                    case (1):
                        image.setImageResource(R.drawable.blau1);
                        break;
                    case (2):
                        image.setImageResource(R.drawable.blau2);
                        break;
                    case (3):
                        image.setImageResource(R.drawable.blau3);
                        break;
                    case (4):
                        image.setImageResource(R.drawable.blau4);
                        break;
                    case (5):
                        image.setImageResource(R.drawable.blau5);
                        break;
                    case (6):
                        image.setImageResource(R.drawable.blau6);
                        break;
                    case (7):
                        image.setImageResource(R.drawable.blau7);
                        break;
                    case (8):
                        image.setImageResource(R.drawable.blau8);
                        break;
                    case (9):
                        image.setImageResource(R.drawable.blau9);
                        break;
                    case (10):
                        image.setImageResource(R.drawable.blau10);
                        break;
                    case (11):
                        image.setImageResource(R.drawable.blau11);
                        break;
                    case (12):
                        image.setImageResource(R.drawable.blau12);
                        break;
                }
                break;
            case ("yellow"):
                switch (card) {
                    case (1):
                        image.setImageResource(R.drawable.gelb1);
                        break;

                    case (2):
                        image.setImageResource(R.drawable.gelb2);
                        break;

                    case (3):
                        image.setImageResource(R.drawable.gelb3);
                        break;

                    case (4):
                        image.setImageResource(R.drawable.gelb4);
                        break;

                    case (5):
                        image.setImageResource(R.drawable.gelb5);
                        break;

                    case (6):
                        image.setImageResource(R.drawable.gelb6);
                        break;

                    case (7):
                        image.setImageResource(R.drawable.gelb7);
                        break;

                    case (8):
                        image.setImageResource(R.drawable.gelb8);
                        break;

                    case (9):
                        image.setImageResource(R.drawable.gelb9);
                        break;

                    case (10):
                        image.setImageResource(R.drawable.gelb10);
                        break;

                    case (11):
                        image.setImageResource(R.drawable.gelb11);
                        break;

                    case (12):
                        image.setImageResource(R.drawable.gelb12);
                        break;
                }
                break;
            case ("green"):
                switch (card) {
                    case (1):
                        image.setImageResource(R.drawable.gruen1);
                        break;

                    case (2):
                        image.setImageResource(R.drawable.gruen2);
                        break;

                    case (3):
                        image.setImageResource(R.drawable.gruen3);
                        break;

                    case (4):
                        image.setImageResource(R.drawable.gruen4);
                        break;

                    case (5):
                        image.setImageResource(R.drawable.gruen5);
                        break;

                    case (6):
                        image.setImageResource(R.drawable.gruen6);
                        break;

                    case (7):
                        image.setImageResource(R.drawable.gruen7);
                        break;

                    case (8):
                        image.setImageResource(R.drawable.gruen8);
                        break;

                    case (9):
                        image.setImageResource(R.drawable.gruen9);
                        break;

                    case (10):
                        image.setImageResource(R.drawable.gruen10);
                        break;

                    case (11):
                        image.setImageResource(R.drawable.gruen11);
                        break;

                    case (12):
                        image.setImageResource(R.drawable.gruen12);
                        break;
                }
                break;

            case ("red"):
                switch (card) {
                    case (1):
                        image.setImageResource(R.drawable.rot1);
                        break;

                    case (2):
                        image.setImageResource(R.drawable.rot2);
                        break;

                    case (3):
                        image.setImageResource(R.drawable.rot3);
                        break;

                    case (4):
                        image.setImageResource(R.drawable.rot4);
                        break;

                    case (5):
                        image.setImageResource(R.drawable.rot5);
                        break;

                    case (6):
                        image.setImageResource(R.drawable.rot6);
                        break;

                    case (7):
                        image.setImageResource(R.drawable.rot7);
                        break;

                    case (8):
                        image.setImageResource(R.drawable.rot8);
                        break;

                    case (9):
                        image.setImageResource(R.drawable.rot9);
                        break;

                    case (10):
                        image.setImageResource(R.drawable.rot10);
                        break;

                    case (11):
                        image.setImageResource(R.drawable.rot11);
                        break;

                    case (12):
                        image.setImageResource(R.drawable.rot12);
                        break;
                }
                break;
        }
    }
}
