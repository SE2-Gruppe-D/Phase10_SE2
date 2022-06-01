package com.example.phase10_se2;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.widget.ImageView;
import org.junit.jupiter.api.*;

public class CardUITest {
    CardUIManager cardUIManager;
    Cards cards1;
    Cards cards2;
    Cards cards3;
    Cards cards4;
    Cards cards5;

    ImageView imageView1;

    @BeforeEach
    public void init(){
        cardUIManager=new CardUIManager();
        cards1= new Cards("blue", 1, null, 1);
        cards2= new Cards("red", 10, null, 2);
        cards3= new Cards("blue", 1, null, 3);
        cards4= new Cards("yellow", 7, null, 4);
        cards5= new Cards("green", 12, null, 5);

        imageView1= mock(ImageView.class);
    }

    @AfterEach
    public void tearDown(){
        cardUIManager=null;
    }

    @Test
    public void testSetImageCardBlue1(){
        cardUIManager.setCardImage(cards1, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.blau1);
    }
    @Test
    public void testSetImageCardRed10(){
        cardUIManager.setCardImage(cards2, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.rot10);
    }

    @Test
    public void testSetImageCardYellow7(){
        cardUIManager.setCardImage(cards4, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gelb7);
    }
    @Test
    public void testSetImageCardGreen12(){
        cardUIManager.setCardImage(cards5, imageView1);
        verify(imageView1, times(1)).setImageResource(R.drawable.gruen12);
    }

    @Test
    public void testSetImageTwice(){
        cardUIManager.setCardImage(cards1, imageView1);
        cardUIManager.setCardImage(cards3, imageView1);
        verify(imageView1, times(2)).setImageResource(R.drawable.blau1);
    }
}
