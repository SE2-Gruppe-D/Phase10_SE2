package com.example.phase10_se2;

import android.widget.ImageView;

public class Cards {

    private final String color;
    private final int value;
    private CardUI cardUI;
    private int cardID;

    public Cards(String color, int value, CardUI cardUI, int cardID) {
        this.color = color;
        this.value = value;
        this.cardUI = cardUI;
        this.cardID = cardID;
    }

    public int getID() {
        return cardID;
    }

    public void setID(int cardID) {
        this.cardID = cardID;
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
