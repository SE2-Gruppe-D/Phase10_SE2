package com.example.phase10_se2;

import android.widget.ImageView;

public class Cards {

    private String color;
    private int value;
    private CardUI cardUI;
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public Cards(String color, int value, CardUI cardUI, int ID) {
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
