package com.example.phase10_se2;


enum PlayerState {
    WAITING, THROWING_DICE, PLAYING
}

enum PlayerColor {
    RED, BLUE, YELLOW, GREEN
}

public class Player {
    private final String name;
    private final PlayerColor color;
    private int startingOrder;
    private PlayerState state;
    private int currentPosition;

    public Player(String name, PlayerColor color) {
        this.name = name;
        this.color = color;

        currentPosition = 0;
        startingOrder = -1;
        state = PlayerState.WAITING;
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
}
