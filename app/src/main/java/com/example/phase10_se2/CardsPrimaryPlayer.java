package com.example.phase10_se2;

import android.view.View;

public class CardsPrimaryPlayer {
    //primaryPlayer soll nur seine Karten sehen
    public void showOnlyPrimaryPlayerCards(Player primaryPlayer) {

        for (Cards card : primaryPlayer.getPlayerHand()) {
            card.getCardUI().setVisibility(View.VISIBLE);
        }

    }
}
