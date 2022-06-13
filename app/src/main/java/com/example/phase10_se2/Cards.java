package com.example.phase10_se2;

import android.widget.ImageView;

public class Cards {

    private final String color;
    private final int value;
    private CardUI cardUI;
    private int ID;

    public Cards(String color, int value, CardUI cardUI, int ID) {
        this.color = color;
        this.value = value;
        this.cardUI = cardUI;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public CardUI getCardUIObject() {
        return cardUI;
    }

    public ImageView getCardUI() {
        return cardUI.imageView;
    }

    public void setCardUI(CardUI cardUI) {
        this.cardUI = cardUI;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }


}
