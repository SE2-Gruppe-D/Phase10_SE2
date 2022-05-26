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
    public void HandCardsPlayer(LinearLayout layoutPlayer1, LinearLayout layoutPlayer2, LinearLayout layoutPlayer3, LinearLayout layoutPlayer4,ArrayList<Cards> cardlist, Player playerblue,  Player playergreen,  Player playeryellow,  Player playerred, Player primaryplayer) {
        playerGreen=playergreen;
        playerBlue= playerblue;
        playerYellow= playeryellow;
        playerRed= playerred;
        primaryPlayer= primaryplayer;
        for (int i = 0; i < 10; i++) {
            if (playerblue != null) {
                if (playerblue.getColor().equals(primaryplayer.getColor())){
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);  //Primary player bekommt immer Layout1
                } else {
                    updateHand(playerblue.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0, cardlist);
                }
            }
            if (playerred != null) {
                if (playerred.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
                } else {
                    updateHand(playerred.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90, cardlist);
                }
            }
            if (playeryellow != null) {
                if (playeryellow.getColor().equals(primaryplayer.getColor())) {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);

                } else {
                    updateHand(playeryellow.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90, cardlist);
                }
            }
            if (playergreen != null) {
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


    public void getCardsLayOut(LinearLayout layoutPlayer1CardField,LinearLayout layoutPlayer2CardField, LinearLayout layoutPlayer3CardField, LinearLayout layoutPlayer4CardField,Player playerblue,  Player playergreen,  Player playeryellow,  Player playerred, Player primaryplayer){
        playerGreen=playergreen;
        playerBlue= playerblue;
        playerYellow= playeryellow;
        playerRed= playerred;
        primaryPlayer= primaryplayer;
        if (playerblue != null) {
            if (playerblue.getColor().equals(primaryplayer.getColor())){
                layoutPlayer1CardField.setVisibility(View.VISIBLE); //Auslegefeld für Spieler sichbar machen
                playerblue.setLinearLayout(layoutPlayer1CardField);
            } else {
                layoutPlayer2CardField.setVisibility(View.VISIBLE);
                playerblue.setLinearLayout(layoutPlayer2CardField);
            }
        }
        if (playerred != null) {
            if (playerred.getColor().equals(primaryplayer.getColor())) {
                layoutPlayer1CardField.setVisibility(View.VISIBLE);
                playerred.setLinearLayout(layoutPlayer1CardField);
            } else if (layoutPlayer2CardField.getVisibility()!=View.VISIBLE){
                layoutPlayer2CardField.setVisibility(View.VISIBLE);
                playerred.setLinearLayout(layoutPlayer2CardField);
            }else{
                layoutPlayer3CardField.setVisibility(View.VISIBLE);
                playerred.setLinearLayout(layoutPlayer3CardField);
            }
        }
        if (playeryellow != null) {
            if (playeryellow.getColor().equals(primaryplayer.getColor())) {
                layoutPlayer1CardField.setVisibility(View.VISIBLE);
                playeryellow.setLinearLayout(layoutPlayer1CardField);

            }else if(layoutPlayer2CardField.getVisibility()!=View.VISIBLE){
                layoutPlayer2CardField.setVisibility(View.VISIBLE);
                playeryellow.setLinearLayout(layoutPlayer2CardField);

            }else if(layoutPlayer3CardField.getVisibility()!=View.VISIBLE){
                layoutPlayer3CardField.setVisibility(View.VISIBLE);
                playeryellow.setLinearLayout(layoutPlayer3CardField);

            }else {
                layoutPlayer4CardField.setVisibility(View.VISIBLE);
                playeryellow.setLinearLayout(layoutPlayer4CardField);

            }
        }
        if (playergreen != null) {
            if (playergreen.getColor().equals(primaryplayer.getColor())) {
                layoutPlayer1CardField.setVisibility(View.VISIBLE);
                playergreen.setLinearLayout(layoutPlayer1CardField);

            }
            else if(layoutPlayer2CardField.getVisibility()!=View.VISIBLE){
            layoutPlayer2CardField.setVisibility(View.VISIBLE);
                playergreen.setLinearLayout(layoutPlayer2CardField);

            }
            else if(layoutPlayer3CardField.getVisibility()!=View.VISIBLE){
            layoutPlayer3CardField.setVisibility(View.VISIBLE);
                playergreen.setLinearLayout(layoutPlayer3CardField);

            }
            else {
            layoutPlayer4CardField.setVisibility(View.VISIBLE);
                playergreen.setLinearLayout(layoutPlayer4CardField);

            }

                /*
            } else {
                if (playerblue!=null&&playerblue.getColor().equals(primaryplayer.getColor()) && layoutPlayer2CardField.getVisibility()!=View.VISIBLE) {
                    layoutPlayer2CardField.setVisibility(View.VISIBLE);

                } else if (playerred!=null&&playerred.getColor().equals(primaryplayer.getColor()) && layoutPlayer3CardField.getVisibility()!=View.VISIBLE) {
                    layoutPlayer3CardField.setVisibility(View.VISIBLE);

                } else if(playeryellow!=null&&playeryellow.getColor().equals(primaryplayer.getColor()) && layoutPlayer4CardField.getVisibility()!=View.VISIBLE){
                    layoutPlayer4CardField.setVisibility(View.VISIBLE);
                }

                 */
            }
        }
    }

