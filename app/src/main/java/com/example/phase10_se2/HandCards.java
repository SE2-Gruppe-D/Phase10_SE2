package com.example.phase10_se2;

import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class HandCards {

    Player playerGreen;
    Player playerRed;
    Player playerYellow;
    Player playerBlue;
    Player primaryPlayer;

    CardsPrimaryPlayer cardsPrimaryPlayer= new CardsPrimaryPlayer();

    //Handkarten werden ausgeteilt
    public void HandCardsPlayer(LinearLayout layoutPlayer1, LinearLayout layoutPlayer2, LinearLayout layoutPlayer3, LinearLayout layoutPlayer4, LinearLayout layoutPlayer1CardField,LinearLayout layoutPlayer2CardField, LinearLayout layoutPlayer3CardField, LinearLayout layoutPlayer4CardField,ArrayList<Cards> cardlist, Player playerblue,  Player playergreen,  Player playeryellow,  Player playerred, Player primaryplayer) {
        playerGreen=playergreen;
        playerBlue= playerblue;
        playerYellow= playeryellow;
        playerRed= playerred;
        primaryPlayer= primaryplayer;
        for (int i = 0; i < 10; i++) {
            if (playerblue != null) {
                if (playerblue.getColor().equals(primaryplayer.getColor())){
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);  //Primary player bekommt immer Layout1
                    layoutPlayer1CardField.setVisibility(View.VISIBLE); //Auslegefeld für Spieler sichbar machen
                } else {
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0, cardlist);
                    layoutPlayer2CardField.setVisibility(View.VISIBLE);
                }
            }
            if (playerred != null) {
                if (playerred.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
                    layoutPlayer1CardField.setVisibility(View.VISIBLE);

                } else {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90, cardlist);
                    layoutPlayer3CardField.setVisibility(View.VISIBLE);
                }
            }
            if (playeryellow != null) {
                if (playeryellow.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
                    layoutPlayer1CardField.setVisibility(View.VISIBLE);

                } else {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90, cardlist);
                    layoutPlayer4CardField.setVisibility(View.VISIBLE);
                }
            }
            if (playergreen != null) {
                if (playergreen.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
                    layoutPlayer1CardField.setVisibility(View.VISIBLE);
                } else {
                    if (playerblue!=null&&playerblue.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0, cardlist);
                        layoutPlayer2CardField.setVisibility(View.VISIBLE);

                    } else if (playerred!=null&&playerred.getColor().equals(primaryplayer.getColor())) {
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90, cardlist);
                        layoutPlayer3CardField.setVisibility(View.VISIBLE);

                    } else if(playeryellow!=null&&playeryellow.getColor().equals(primaryplayer.getColor())){
                        updateHand(playergreen.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90, cardlist);
                        layoutPlayer4CardField.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
    //Karten werden den Spieler angepasst/ Handkarten-Layout
    public void updateHand(List<Cards> list, Cards cards, LinearLayout linearLayout, int grad, ArrayList<Cards> cardlist) {
        list.add(cards);
        linearLayout.addView(cards.getCardUI());

        //cards.getCardUI().setVisibility(View.VISIBLE);
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
        cards.getCardUI().setRotation(grad);

    }
}
