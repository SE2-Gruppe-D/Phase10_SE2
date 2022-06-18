package com.example.phase10_se2;

import android.widget.LinearLayout;

import java.util.List;

public class HandCards {

    Player playerGreen;
    Player playerRed;
    Player playerYellow;
    Player playerBlue;
    Player primaryPlayer;


    CardsPrimaryPlayer cardsPrimaryPlayer;

    public HandCards() {
        cardsPrimaryPlayer = new CardsPrimaryPlayer();
    }

    public HandCards(CardsPrimaryPlayer cardsPrimaryPlayer) {
        this.cardsPrimaryPlayer = cardsPrimaryPlayer;
    }

    //Handkarten werden ausgeteilt
    public void handCardsPlayer(LinearLayout layoutPlayer1, LinearLayout layoutPlayer2, LinearLayout layoutPlayer3, LinearLayout layoutPlayer4, List<Cards> cardlist, Player playerblue, Player playergreen, Player playeryellow, Player playerred, Player primaryplayer) {
        primaryPlayer = primaryplayer;
        for (int i = 0; i < 10; i++) {
            if (playerblue != null) {
                playerBlue = playerblue;
                if (playerblue.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist, true);  //Primary player bekommt immer Layout1
                } else {
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0, cardlist, false);
                }
            }
            if (playerred != null) {
                playerRed = playerred;

                if (playerred.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist, true);
                } else {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90, cardlist, false);
                }
            }
            if (playeryellow != null) {
                playerYellow = playeryellow;

                if (playeryellow.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist, true);

                } else {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90, cardlist, false);
                }
            }
            if (playergreen != null) {
                playerGreen = playergreen;
                if (playergreen.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist, true);
                } else {
                    if (playerblue != null && playerblue.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0, cardlist, true);

                    } else if (playerred != null && playerred.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90, cardlist, true);

                    } else if (playeryellow != null && playeryellow.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90, cardlist, true);
                    }
                }
            }
        }
    }

    //Karten werden den Spieler angepasst/ Handkarten-Layout
    public void updateHand(List<Cards> list, Cards cards, LinearLayout linearLayout, int grad, List<Cards> cardlist, boolean isPrimary) {
        list.add(cards);

        if (isPrimary) { //only add cards to the view, if its the primary player
            linearLayout.addView(cards.getCardUI());
        }

        //Karten nur fuer primary player sichtbar

        if (playerYellow != null && playerYellow.getColor().equals(primaryPlayer.getColor())) {
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerYellow);
        }
        if (playerBlue != null && playerBlue.getColor().equals(primaryPlayer.getColor())) {
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerBlue);
        }
        if (playerRed != null && playerRed.getColor().equals(primaryPlayer.getColor())) {
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerRed);
        }
        if (playerGreen != null && playerGreen.getColor().equals(primaryPlayer.getColor())) {
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerGreen);
        }

        cardlist.remove(0);
        cards.getCardUIObject().setRotation(grad);
    }


    public void updateHandCompletely(List<Cards> list, List<Cards> cards, LinearLayout linearLayout) {
        linearLayout.removeAllViews();
        list.addAll(cards);

        for (Cards card : cards) {
            linearLayout.addView(card.getCardUI());
        }

        //Karten nur fuer primary player sichtbar
        if (playerYellow != null && playerYellow.getColor().equals(primaryPlayer.getColor())) {
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerYellow);
        }
        if (playerBlue != null && playerBlue.getColor().equals(primaryPlayer.getColor())) {
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerBlue);
        }
        if (playerRed != null && playerRed.getColor().equals(primaryPlayer.getColor())) {
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerRed);
        }
        if (playerGreen != null && playerGreen.getColor().equals(primaryPlayer.getColor())) {
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerGreen);
        }
    }
}

