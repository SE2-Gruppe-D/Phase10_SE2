package com.example.phase10_se2;

import static org.mockito.Mockito.mock;
import android.widget.ImageView;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestCardsVisibility {
    CardsPrimaryPlayer cardsPrimaryPlayer;
    Cards card1;
    Cards card2;
    Cards card3;
    Cards card4;

    Player primary;
    private ArrayList<Cards> cardList=new ArrayList<>();

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    @BeforeEach
    public void init(){
        cardsPrimaryPlayer=new CardsPrimaryPlayer();
        primary=new Player();
        imageView1= mock(ImageView.class);
        imageView2= mock(ImageView.class);
        imageView3= mock(ImageView.class);
        imageView4= mock(ImageView.class);
    }

    @AfterEach
    public void tearDown(){
        cardsPrimaryPlayer=null;
    }

    @Test
    public void testVisibilityCards(){
        card1 = new Cards("blue", 1, new CardUI(imageView1), 1);
        card2 = new Cards("red", 2, new CardUI(imageView2), 2);
        card3 = new Cards("yellow", 6, new CardUI(imageView3), 3);
        card4 = new Cards("green", 12, new CardUI(imageView4), 4);

        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);

        if(primary!=null){
            primary.setPlayerHand(cardList);
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(primary);
            for (Cards card : primary.getPlayerHand()) {
                Assertions.assertEquals(0, card.getCardUI().getVisibility());
            }
        }
    }

    @Test
    public void PrimaryPlayerNull(){
        Assertions.assertNotNull(primary);
    }

}
