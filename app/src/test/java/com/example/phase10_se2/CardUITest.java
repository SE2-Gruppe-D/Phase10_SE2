package com.example.phase10_se2;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.widget.ImageView;
import org.junit.jupiter.api.*;

public class CardUITest {
    CardUIManager cardUIManager;
    Cards cards1;
    Cards cards3;
    Cards cards;

    ImageView imageView1;

    @BeforeEach
    public void init(){
        cardUIManager=new CardUIManager();
        cards1= new Cards("blue", 1, null, 1);
        cards3= new Cards("blue", 1, null, 3);
        imageView1= mock(ImageView.class);
    }

    @AfterEach
    public void tearDown(){
        cardUIManager=null;
    }


    //Test Blaue Karten
    @Test
    public void testSetImageCardBlue1(){
        cards= new Cards("blue", 1, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau1);
    }
    @Test
    public void testSetImageCardBlue2(){
        cards= new Cards("blue", 2, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau2);
    }
    @Test
    public void testSetImageCardBlue3(){
        cards= new Cards("blue", 3, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau3);
    }
    @Test
    public void testSetImageCardBlue4(){
        cards= new Cards("blue", 4, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau4);
    }
    @Test
    public void testSetImageCardBlue5(){
        cards= new Cards("blue", 5, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau5);
    }@Test
    public void testSetImageCardBlue6(){
        cards= new Cards("blue", 6, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau6);
    }@Test
    public void testSetImageCardBlue7(){
        cards= new Cards("blue", 7, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau7);
    }@Test
    public void testSetImageCardBlue8(){
        cards= new Cards("blue", 8, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau8);
    }@Test
    public void testSetImageCardBlue9(){
        cards= new Cards("blue", 9, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau9);
    }@Test
    public void testSetImageCardBlue10(){
        cards= new Cards("blue", 10, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau10);
    }@Test
    public void testSetImageCardBlue11(){
        cards= new Cards("blue", 11, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau11);
    }@Test
    public void testSetImageCardBlue12(){
        cards= new Cards("blue", 12, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau12);
    }

    //Test Rote Karten
    @Test
    public void testSetImageCardRed1(){
        cards= new Cards("red", 1, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot1);
    }

    @Test
    public void testSetImageCardRed2(){
        cards= new Cards("red", 2, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot2);
    }
    @Test
    public void testSetImageCardRed3(){
        cards= new Cards("red", 3, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot3);
    } @Test
    public void testSetImageCardRed4(){
        cards= new Cards("red", 4, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot4);
    } @Test
    public void testSetImageCardRed5(){
        cards= new Cards("red", 5, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot5);
    }
    @Test
    public void testSetImageCardRed6(){
        cards= new Cards("red", 6, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot6);
    }
    @Test
    public void testSetImageCardRed7(){
        cards= new Cards("red", 7, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot7);
    }
    @Test
    public void testSetImageCardRed8(){
        cards= new Cards("red", 8, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot8);
    }
    @Test
    public void testSetImageCardRed9(){
        cards= new Cards("red", 9, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot9);
    }
    @Test
    public void testSetImageCardRed10(){
        cards= new Cards("red", 10, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot10);
    }
    @Test
    public void testSetImageCardRed11(){
        cards= new Cards("red", 11, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot11);
    }
    @Test
    public void testSetImageCardRed12() {
        cards = new Cards("red", 12, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot12);
    }

    //Test Gelbe Karten
    @Test
    public void testSetImageCardYellow1(){
        cards = new Cards("yellow", 1, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb1);
    }
    @Test
    public void testSetImageCardYellow2(){
        cards = new Cards("yellow", 2, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb2);
    }
    @Test
    public void testSetImageCardYellow3(){
        cards = new Cards("yellow", 3, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb3);
    }@Test
    public void testSetImageCardYellow4(){
        cards = new Cards("yellow", 4, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb4);
    }
    @Test
    public void testSetImageCardYellow5(){
        cards = new Cards("yellow", 5, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb5);
    }
    @Test
    public void testSetImageCardYellow6(){
        cards = new Cards("yellow", 6, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb6);
    }@Test
    public void testSetImageCardYellow7(){
        cards = new Cards("yellow", 7, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb7);
    }
    @Test
    public void testSetImageCardYellow8(){
        cards = new Cards("yellow", 8, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb8);
    }
    @Test
    public void testSetImageCardYellow9(){
        cards = new Cards("yellow", 9, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb9);
    }
    @Test
    public void testSetImageCardYellow10(){
        cards = new Cards("yellow", 10, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb10);
    }
    @Test
    public void testSetImageCardYellow11(){
        cards = new Cards("yellow", 11, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb11);
    }
    @Test
    public void testSetImageCardYellow12(){
        cards = new Cards("yellow", 12, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb12);
    }

    //Test Gruene Karten
    @Test
    public void testSetImageCardGreen1(){
        cards = new Cards("green", 1, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen1);
    }
    @Test
    public void testSetImageCardGreen2(){
        cards = new Cards("green", 2, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen2);
    }
    @Test
    public void testSetImageCardGreen3(){
        cards = new Cards("green", 3, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen3);
    }
    @Test
    public void testSetImageCardGreen4(){
        cards = new Cards("green", 4, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen4);
    }
    @Test
    public void testSetImageCardGreen5(){
        cards = new Cards("green", 5, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen5);
    }
    @Test
    public void testSetImageCardGreen6(){
        cards = new Cards("green", 6, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen6);
    }
    @Test
    public void testSetImageCardGreen7(){
        cards = new Cards("green", 7, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen7);
    }
    @Test
    public void testSetImageCardGreen8(){
        cards = new Cards("green", 8, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen8);
    }
    @Test
    public void testSetImageCardGreen9(){
        cards = new Cards("green", 9, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen9);
    }
    @Test
    public void testSetImageCardGreen10(){
        cards = new Cards("green", 10, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen10);
    }
    @Test
    public void testSetImageCardGreen11(){
        cards = new Cards("green", 11, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen11);
    }
    @Test
    public void testSetImageCardGreen12(){
        cards = new Cards("green", 12, null, 1);
        cardUIManager.setCardImage(cards, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen12);
    }

    //Karten werden zwei mal gesetzt
    @Test
    public void testSetImageTwice(){
        cardUIManager.setCardImage(cards1, imageView1);
        cardUIManager.setCardImage(cards3, imageView1);
        verify(imageView1, times(2)).setImageResource(R.drawable.blau1);
    }
}
