package com.example.phase10_se2;

import android.widget.ImageView;

public class cards {

    private String color;
    private int value;
    private ImageView cardUI;
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public void setCardUI(ImageView cardUI) {
        this.cardUI = cardUI;
    }

    public ImageView getCardUI() {
        return cardUI;
    }

    public cards(String color, int value, ImageView cardUI, int ID) {
        this.color = color;
        this.value = value;
        this.cardUI = cardUI;
        this.ID= ID;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }


}
