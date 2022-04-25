package com.example.phase10_se2;


import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;

enum PlayerState {
    WAITING, THROWING_DICE, PLAYING
}

enum PlayerColor {
    RED, BLUE, YELLOW, GREEN
}

enum FieldColor {
    BLUE, GREY, GREEN, ORANGE, RED, PURPLE, PINK
}

public class Player {
    final private FieldColor[] colorField = new FieldColor[]{FieldColor.BLUE, FieldColor.GREY, FieldColor.GREEN,
            FieldColor.ORANGE, FieldColor.GREY, FieldColor.RED, FieldColor.GREY, FieldColor.PURPLE, FieldColor.PINK,
            FieldColor.BLUE, FieldColor.GREY, FieldColor.ORANGE, FieldColor.GREEN, FieldColor.GREY, FieldColor.RED,
            FieldColor.PINK};
    private String name;
    private PlayerColor color;
    private String room;
    private int startingOrder;
    private PlayerState state;
    private int currentPosition;
    private int positionX, positionY;
    private ImageView playerview;
    private ArrayList<Cards> playerHand;



    // no-argument constructor
    public Player() {
    }

    public Player(String name, PlayerColor color, String room) {
        this.name = name;
        this.color = color;
        this.room = room;

        positionX = 0;
        positionY = 0;
        currentPosition = 0;
        startingOrder = -1;
        state = PlayerState.WAITING;
        playerHand = new ArrayList<Cards>();
    }

    public Player(String name, PlayerColor color, int positionX, int positionY) {
        this.name = name;
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;

        currentPosition = 0;
        startingOrder = -1;
        state = PlayerState.WAITING;
        playerHand = new ArrayList<Cards>();
    }

    public Player(String name, PlayerColor color) {
        this.name = name;
        this.color = color;

        positionX = 0;
        positionY = 0;
        currentPosition = 0;
        startingOrder = -1;
        state = PlayerState.WAITING;
        playerHand = new ArrayList<Cards>();
    }

    public void move(int diceValue) {
        currentPosition = (currentPosition + diceValue) % 16;

        if (currentPosition <= 3) {
            positionX = currentPosition;
            positionY = 0;
        } else if (currentPosition <= 8) {
            positionX = 3;
            positionY = currentPosition-3;
        } else if (currentPosition <= 11) {
            positionX = 12 - currentPosition;
            positionY = 5;
        } else {
            positionX = 0;
            positionY = 16 - currentPosition;
        }

        updateMapPosition();
    }


    //GETTER AND SETTER
    public int getStartingOrder() {
        return startingOrder;
    }

    public void setStartingOrder(int startingPosition) {
        if (startingPosition == -1) {
            this.startingOrder = startingPosition;
        }
    }
    //bewegen des Players auf dem Spielbrett
    public void updateMapPosition () {
        if(this.color == PlayerColor.BLUE && this.playerview != null) {
            this.playerview.setTranslationX(getPositionX() * (float)47 + (float) 16);
            this.playerview.setTranslationY(getPositionY() * (float)54 + (float) 63);
        }
        else if(this.color == PlayerColor.YELLOW && this.playerview != null) {
            this.playerview.setTranslationX(getPositionX() * (float)47 + (float)35);
            this.playerview.setTranslationY(getPositionY() * (float)54 + (float)63);
        }
        else if(this.color == PlayerColor.RED && this.playerview != null) {
            this.playerview.setTranslationX(getPositionX() * (float)47 + (float)35);
            this.playerview.setTranslationY(getPositionY() * (float)54 + (float)82);
        }
        else if(this.color == PlayerColor.GREEN && this.playerview != null) {
            this.playerview.setTranslationX(getPositionX() * (float)47 + (float)16);
            this.playerview.setTranslationY(getPositionY() * (float)54 + (float)82);
        }
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public PlayerColor getColor() {
        return color;
    }

    public String getRoom() {
        return room;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPlayerview(ImageView playerview) {
        this.playerview = playerview;
    }

    public ImageView getPlayerview() {
        return playerview;
    }

    public ArrayList<Cards> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(ArrayList<Cards> playerHand) {
        this.playerHand = playerHand;
    }
}
