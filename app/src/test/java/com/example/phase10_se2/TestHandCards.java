package com.example.phase10_se2;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstructionWithAnswer;

import android.widget.ImageView;
import android.widget.LinearLayout;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestHandCards {
    HandCards handCards;

    CardDrawer cardDrawer= new CardDrawer();
    CardsPrimaryPlayer cardsPrimaryPlayer = new CardsPrimaryPlayer();
    private ArrayList<Cards> cardListPrim=new ArrayList<>();
    private ArrayList<Cards> cardList=new ArrayList<>();
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    LinearLayout linearLayout4;
    ImageView imageView;

    Player playerBlue;
    Player playerYellow;
    Player playerGreen;
    Player playerRed;
    Player primary;

    Cards card1;
    Cards card2;
    Cards card3;
    Cards card4;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    @BeforeEach
    public void init(){
        handCards=new HandCards();
        cardList=cardDrawer.generateInitialCards();
        playerBlue=new Player("Blau", PlayerColor.BLUE, 0,0);
        playerYellow=new Player("Yellow", PlayerColor.YELLOW, 0,0);
        playerRed= new Player("Red", PlayerColor.RED, 0,0);
        playerGreen= new Player("Green", PlayerColor.GREEN, 0,0);

        linearLayout1= mock(LinearLayout.class);
        linearLayout2=mock(LinearLayout.class);
        linearLayout3=mock(LinearLayout.class);
        linearLayout4=mock(LinearLayout.class);

        cardsPrimaryPlayer=new CardsPrimaryPlayer();
        primary=new Player();
        imageView1= mock(ImageView.class);
        imageView2= mock(ImageView.class);
        imageView3= mock(ImageView.class);
        imageView4= mock(ImageView.class);
    }

    @AfterEach
    public void tearDown(){
        handCards=null;
    }

    @Test
    public void PrimaryPlayerBlue(){
        primary=playerBlue;
        handCards.HandCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);

        Assertions.assertEquals(56, cardList.size());
        Assertions.assertEquals(10, primary.getPlayerHand().size());

    }

    @Test
    public void PrimaryPlayerGreen(){
        playerGreen= primary;
        handCards.HandCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);

        Assertions.assertEquals(56, cardList.size());
        Assertions.assertEquals(10, primary.getPlayerHand().size());
    }

    @Test
    public void PrimaryPlayerYellow(){
        playerYellow= primary;
        handCards.HandCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);

        Assertions.assertEquals(56, cardList.size());
        Assertions.assertEquals(10, primary.getPlayerHand().size());
    }

    @Test
    public void PrimaryPlayerRed(){
        playerRed= primary;
        handCards.HandCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);

        Assertions.assertEquals(56, cardList.size());
        Assertions.assertEquals(10, primary.getPlayerHand().size());
    }

    @Test
    public void PlayerNull(){
        playerRed= primary;
        playerBlue=new Player();
        handCards.HandCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);

        Assertions.assertEquals(66, cardList.size());
        Assertions.assertEquals(10, primary.getPlayerHand().size());
    }

    @Test
    public void testVisibilityCards(){
        card1 = new Cards("blue", 1, imageView1, 1);
        card2 = new Cards("red", 2, imageView2, 2);
        card3 = new Cards("yellow", 6, imageView3, 3);
        card4 = new Cards("green", 12, imageView4, 4);

        cardListPrim.add(card1);
        cardListPrim.add(card2);
        cardListPrim.add(card3);
        cardListPrim.add(card4);


        if(primary!=null){
            primary.setPlayerHand(cardListPrim);
            handCards.HandCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);
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
