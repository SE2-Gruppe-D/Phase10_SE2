package com.example.phase10_se2;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class HandCards {

    Player playerGreen;
    Player playerRed;
    Player playerYellow;
    Player playerBlue;
    Player primaryPlayer;


    CardsPrimaryPlayer cardsPrimaryPlayer;

    public HandCards() {
        cardsPrimaryPlayer=new CardsPrimaryPlayer();
    }
    public HandCards(CardsPrimaryPlayer cardsPrimaryPlayer) {
        this.cardsPrimaryPlayer = cardsPrimaryPlayer;
    }

    //Handkarten werden ausgeteilt
    public void handCardsPlayer(LinearLayout layoutPlayer1, LinearLayout layoutPlayer2, LinearLayout layoutPlayer3, LinearLayout layoutPlayer4,ArrayList<Cards> cardlist, Player playerblue,  Player playergreen,  Player playeryellow,  Player playerred, Player primaryplayer) {
        primaryPlayer= primaryplayer;
        for (int i = 0; i < 10; i++) {
            if (playerblue != null) {
                playerBlue= playerblue;
                if (playerblue.getColor().equals(primaryplayer.getColor())){
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);  //Primary player bekommt immer Layout1
                } else {
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0, cardlist);
                }
            }
            if (playerred != null) {
                playerRed= playerred;

                if (playerred.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
                } else {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90, cardlist);
                }
            }
            if (playeryellow != null) {
                playerYellow= playeryellow;

                if (playeryellow.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);

                } else {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90, cardlist);
                }
            }
            if (playergreen != null) {
                playerGreen=playergreen;
                if (playergreen.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
                } else {
                    if (playerblue!=null&&playerblue.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0, cardlist);

                    } else if (playerred!=null&&playerred.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90, cardlist);

                    } else if(playeryellow!=null&&playeryellow.getColor().equals(primaryplayer.getColor())){
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90, cardlist);
                    }
                }
            }
        }
    }

    //Karten werden den Spieler angepasst/ Handkarten-Layout
    public void updateHand(List<Cards> list, Cards cards, LinearLayout linearLayout, int grad, ArrayList<Cards> cardlist) {
        list.add(cards);
        linearLayout.addView(cards.getCardUI());

        //Karten nur fuer primary player sichtbar

        if(playerYellow!=null&&playerYellow.getColor().equals(primaryPlayer.getColor())){
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerYellow);
        }
        if(playerBlue!=null&&playerBlue.getColor().equals(primaryPlayer.getColor())){
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerBlue);
        }
        if (playerRed!=null&&playerRed.getColor().equals(primaryPlayer.getColor())){
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerRed);
        }
        if(playerGreen!=null&&playerGreen.getColor().equals(primaryPlayer.getColor())){
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(playerGreen);
        }

        cardlist.remove(0);
        cards.getCardUIObject().setRotation(0); //set rotation to 0
    }
}

