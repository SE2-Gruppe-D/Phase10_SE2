package com.example.phase10_se2;


enum PlayerState {
    WAITING, THROWING_DICE, PLAYING
}

enum PlayerColor {
    RED, BLUE, YELLOW, GREEN
}

public class Player {
    private  String name;
    private  PlayerColor color;
    private  String room;
    private int startingOrder;
    private PlayerState state;
    private int currentPosition;

    // no-argument constructor
    public Player(){
    }

    public Player(String name, PlayerColor color, String room) {
        this.name = name;
        this.color = color;
        this.room = room;

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

    public String getRoom() {
        return room;
    }
}
