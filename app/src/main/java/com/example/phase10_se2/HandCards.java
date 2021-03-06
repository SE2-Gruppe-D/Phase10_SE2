package com.example.phase10_se2;

import android.view.ViewGroup;
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
    public void handCardsPlayer(LinearLayout[] layoutPlayers, List<Cards> cardlist, Player playerblue, Player playergreen, Player playeryellow, Player playerred, Player primaryplayer) {
        primaryPlayer = primaryplayer;
        for (int i = 0; i < 10; i++) {
            if (playerblue != null) {
                playerBlue = playerblue;
                if (playerblue.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayers[0], cardlist, true);  //Primary player bekommt immer Layout1
                } else {
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayers[1], cardlist, false);
                }
            }
            if (playerred != null) {
                playerRed = playerred;

                if (playerred.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayers[0], cardlist, true);
                } else {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayers[2], cardlist, false);
                }
            }
            if (playeryellow != null) {
                playerYellow = playeryellow;

                if (playeryellow.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayers[0], cardlist, true);

                } else {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayers[3], cardlist, false);
                }
            }
            if (playergreen != null) {
                playerGreen = playergreen;
                if (playergreen.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayers[0], cardlist, true);
                } else {
                    if (playerblue != null && playerblue.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayers[1], cardlist, true);

                    } else if (playerred != null && playerred.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayers[2], cardlist, true);

                    } else if (playeryellow != null && playeryellow.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayers[3], cardlist, true);
                    }
                }
            }
        }
    }

    //Karten werden den Spieler angepasst/ Handkarten-Layout
    public void updateHand(List<Cards> list, Cards cards, LinearLayout linearLayout, List<Cards> cardlist, boolean isPrimary) {
        list.add(cards);

        if (isPrimary) { //only add cards to the view, if its the primary player
            if (cards.getCardUI().getParent() != null) {
                ((ViewGroup) cards.getCardUI().getParent()).removeView(cards.getCardUI());
            }
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
        cards.getCardUIObject().setRotation(0); //set rotation to 0
    }


    public void updateHandCompletely(List<Cards> list, List<Cards> cards, LinearLayout linearLayout) {
        linearLayout.removeAllViews();

        for (Cards card : cards) {
            if(card != null && card.getCardUI() != null && card.getCardUI().getParent() != null) {
                ((ViewGroup)card.getCardUI().getParent()).removeView(card.getCardUI());
            }
        }

        list.addAll(cards);

        for (Cards card : cards) {
            if(card.getCardUI().getParent()==null){
                linearLayout.addView(card.getCardUI());
            }
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

