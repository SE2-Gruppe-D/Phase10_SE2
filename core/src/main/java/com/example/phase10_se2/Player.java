package com.example.phase10_se2;


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
    private int minusPoints;
    private ImageView playerview;
    private ArrayList<Cards> playerHand;
    private String phaseText;
    private int phasenumber;
    //für das Auslegen der Karten
    private ArrayList<Cards> cardField;


    // no-argument constructor
    public Player() {
    }

    public Player(String name, PlayerColor color, String room, int phasenumber, int minusPoints, ArrayList<Cards> cards, ArrayList<Cards> cardField) {
        this.name = name;
        this.color = color;
        this.room = room;

        this.phasenumber = phasenumber;
        positionX = 0;
        positionY = 0;
        this.minusPoints = minusPoints;
        currentPosition = 0;
        startingOrder = -1;
        state = PlayerState.WAITING;
        playerHand = new ArrayList<Cards>();
        phaseText = "/";
        this.cardField = cardField;
    }

    public Player(String name, PlayerColor color, int positionX, int positionY) {
        this.name = name;
        this.color = color;
        this.positionX = positionX;
        this.positionY = positionY;

        minusPoints = 0;
        currentPosition = 0;
        startingOrder = -1;
        state = PlayerState.WAITING;
        playerHand = new ArrayList<Cards>();

        phaseText = "/";
    }

    public Player(String name, PlayerColor color) {
        this.name = name;
        this.color = color;

        positionX = 0;
        positionY = 0;
        minusPoints = 0;
        currentPosition = 0;
        startingOrder = -1;
        state = PlayerState.WAITING;
        playerHand = new ArrayList<Cards>();

        phaseText = "/";
    }

    public void move(int diceValue) {
        currentPosition = (currentPosition + diceValue) % 16;

        if (currentPosition <= 3) {
            positionX = currentPosition;
            positionY = 0;
        } else if (currentPosition <= 7) {
            positionX = 3;
            positionY = currentPosition - 3;
        } else if (currentPosition <= 11) {
            positionX = 11 - currentPosition;
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
            this.playerview.setTranslationX(getPositionX() * (float)45 + (float) 15);
            this.playerview.setTranslationY(getPositionY() * (float)49 + (float) 56);
        }
        else if(this.color == PlayerColor.YELLOW && this.playerview != null) {
            this.playerview.setTranslationX(getPositionX() * (float)45 + (float)34);
            this.playerview.setTranslationY(getPositionY() * (float)49 + (float)56);
        }
        else if(this.color == PlayerColor.RED && this.playerview != null) {
            this.playerview.setTranslationX(getPositionX() * (float)45 + (float)15);
            this.playerview.setTranslationY(getPositionY() * (float)49 + (float)75);
        }
        else if(this.color == PlayerColor.GREEN && this.playerview != null) {
            this.playerview.setTranslationX(getPositionX() * (float)45 + (float)34);
            this.playerview.setTranslationY(getPositionY() * (float)49 + (float)75);
        }
        System.out.println((getPositionX() * (float) 47 + (float) 16) + " : " + (getPositionY() * (float) 54 + (float) 63));
    }

    //übergeben der restlichen Handkarten am ende einer Runde - Zusammenfügen der Minuspunkte
    public void updateMinusPoints(ArrayList<Cards> cards) {
        int sumMinusPoints = this.minusPoints;
        int cid;


        for (Cards card : cards) {
            if (!cards.isEmpty() && card != null) {

                cid = card.getValue();

                switch (cid) {

                    case (1):

                    case (2):

                    case (3):

                    case (4):

                    case (5):

                    case (6):

                    case (7):

                    case (8):

                    case (9):
                        sumMinusPoints += 5;
                        break;

                    case (10):

                    case (11):

                    case (12):
                        sumMinusPoints += 10;
                        break;

                }

            }
        }
        this.minusPoints = sumMinusPoints;
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


    public int getMinusPoints() {
        return minusPoints;
    }

    public void setMinusPoints(int minusPoints) {
        this.minusPoints = minusPoints;
    }

    public ImageView getPlayerview() {
        return playerview;
    }

    public void setPlayerview(ImageView playerview) {
        this.playerview = playerview;
    }

    public ArrayList<Cards> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(ArrayList<Cards> playerHand) {
        this.playerHand = playerHand;
    }

    public String getPhaseText() {
        return phaseText;
    }

    public void setPhaseText(int phaseNumber) {

        switch (phaseNumber) {

            case (1):
                phaseText = "4 Zwillinge";
                break;

            case (2):
                phaseText = "6 Karten einer Farbe";
                break;

            case (3):
                phaseText = "1 Vierling + 1 Viererfolge";
                break;

            case (4):
                phaseText = "1 Achterfolge";
                break;

            case (5):
                phaseText = "7 Karten einer Farbe";
                break;

            case (6):
                phaseText = "1 Neunerfolge";
                break;

            case (7):
                phaseText = "2 Vierlinge";
                break;

            case (8):
                phaseText = "1 Viererfolge einer Farbe + 1 Drilling";
                break;

            case (9):
                phaseText = "1 Fünfling + 1 Drilling";
                break;

            case (10):
                phaseText = "1 Fünfling + 1 Dreierfolge einer Farbe";
                break;

        }

    }

    public int getPhasenumber() {
        return phasenumber;
    }

    public void setPhasenumber(int phasenumber) {
        this.phasenumber = phasenumber;
    }

    public ArrayList<Cards> getCardField() {
        return cardField;
    }

    public void setCardField(ArrayList<Cards> cardField) {
        this.cardField = cardField;
    }
}
