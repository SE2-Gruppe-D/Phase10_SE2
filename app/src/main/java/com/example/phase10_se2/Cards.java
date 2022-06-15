package com.example.phase10_se2;

import android.widget.ImageView;

public class Cards {

    private String color;
    private int value;
    private CardUI cardUI;
    private int CardID;

    public int getID() {
        return CardID;
    }

    public void setID(int CardID) {
        this.CardID = CardID;
    }

    public CardUI getCardUIObject(){
        return cardUI;
    }

    public void setCardUI(CardUI cardUI) {
        this.cardUI = cardUI;
    }

    public ImageView getCardUI() {
        return cardUI.imageView;
    }

    public Cards(String color, int value, CardUI cardUI, int CardID) {
        this.color = color;
        this.value = value;
        this.cardUI = cardUI;
        this.CardID= CardID;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }


}
