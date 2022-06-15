package com.example.phase10_se2;

import static org.mockito.Mockito.mock;

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
    CardsPrimaryPlayer cardsPrimaryPlayer;
    Cards card1;
    Cards card2;
    Cards card3;
    Cards card4;

    private ArrayList<Cards> cardListPrim=new ArrayList<>();
    private ArrayList<Cards> cardList=new ArrayList<>();

    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    LinearLayout linearLayout4;

    Player playerBlue;
    Player playerYellow;
    Player playerGreen;
    Player playerRed;
    Player primary;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    @BeforeEach
    public void init(){
        cardsPrimaryPlayer=mock(CardsPrimaryPlayer.class);
        handCards=new HandCards(cardsPrimaryPlayer);
        cardList= (ArrayList<Cards>) cardDrawer.generateInitialCards();
        for (int i = 0; i<96; i++){
            CardUI cardUI= mock(CardUI.class);
            cardList.get(i).setCardUI(cardUI);
        }

        playerBlue=new Player("Max", PlayerColor.BLUE, 0,0);
        playerYellow=new Player("Lea", PlayerColor.YELLOW, 0,0);
        playerRed= new Player("Lia", PlayerColor.RED, 0,0);
        playerGreen= new Player("Hans", PlayerColor.GREEN, 0,0);

        linearLayout1= mock(LinearLayout.class);
        linearLayout2=mock(LinearLayout.class);
        linearLayout3=mock(LinearLayout.class);
        linearLayout4=mock(LinearLayout.class);


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
        handCards.handCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);

        Assertions.assertEquals(56, cardList.size());
        Assertions.assertEquals(10, primary.getPlayerHand().size());

    }

    @Test
    public void PrimaryPlayerGreen(){
        primary=playerGreen;
        handCards.handCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);

        Assertions.assertEquals(56, cardList.size());
        Assertions.assertEquals(10, primary.getPlayerHand().size());
    }

    @Test
    public void PrimaryPlayerYellow(){
        primary=playerYellow;
        handCards.handCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);

        Assertions.assertEquals(56, cardList.size());
        Assertions.assertEquals(10, primary.getPlayerHand().size());
    }

    @Test
    public void PrimaryPlayerRed(){
        primary=playerRed;
        handCards.handCardsPlayer(linearLayout1, linearLayout2, linearLayout3, linearLayout4, cardList, playerBlue, playerGreen, playerYellow, playerRed, primary);

        Assertions.assertEquals(56, cardList.size());
        Assertions.assertEquals(10, primary.getPlayerHand().size());
    }

    @Test
    public void testVisibilityCards(){
        ArrayList<Cards> cardlist= new ArrayList<>();
        card1 = new Cards("blue", 1, new CardUI(imageView1), 1);
        card2 = new Cards("red", 2, new CardUI(imageView2), 2);
        card3 = new Cards("yellow", 6, new CardUI(imageView3), 3);
        card4 = new Cards("green", 12, new CardUI(imageView4), 4);

        cardlist.add(card1);
        cardlist.add(card2);
        cardlist.add(card3);
        cardlist.add(card4);

        if(primary!=null){
            cardsPrimaryPlayer.showOnlyPrimaryPlayerCards(primary);
            primary.setPlayerHand(cardlist);
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
