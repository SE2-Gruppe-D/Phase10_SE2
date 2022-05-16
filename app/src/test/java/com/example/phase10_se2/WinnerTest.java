package com.example.phase10_se2;

import android.widget.ImageView;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class WinnerTest
{
    Player player1;
    Player player2;
    Cards card1;
    Cards card2;
    Cards card3;
    ArrayList<Cards> cards;
    ImageView a;

    @BeforeEach
    public void init() {
        player1 = new Player("Lorem",PlayerColor.BLUE, 1);
        player2 = new Player("Ipsum",PlayerColor.GREEN, 3);
        Cards card1 = new Cards("blue",7,a,7);
        Cards card2 = new Cards("yellow",5,a,5+48);
        Cards card3 = new Cards("green",11,a,11+72);

    }













}
