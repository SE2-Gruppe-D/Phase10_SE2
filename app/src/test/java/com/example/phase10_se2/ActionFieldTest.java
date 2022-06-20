package com.example.phase10_se2;

import com.example.phase10_se2.enums.FieldColor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionFieldTest {

    private Actionfield actionfield;


    @BeforeEach
    public void setup () {
        actionfield = new Actionfield();
    }

    @Test
    public void testGetRightFieldColor (){
        Assertions.assertEquals(FieldColor.BLUE, actionfield.getRightFieldColor(0));
        Assertions.assertEquals(FieldColor.GREY, actionfield.getRightFieldColor(1));
        Assertions.assertEquals(FieldColor.GREEN, actionfield.getRightFieldColor(2));
        Assertions.assertEquals(FieldColor.ORANGE, actionfield.getRightFieldColor(3));
        Assertions.assertEquals(FieldColor.GREY, actionfield.getRightFieldColor(4));
        Assertions.assertEquals(FieldColor.RED, actionfield.getRightFieldColor(5));
        Assertions.assertEquals(FieldColor.GREY, actionfield.getRightFieldColor(6));
        Assertions.assertEquals(FieldColor.PURPLE, actionfield.getRightFieldColor(7));
        Assertions.assertEquals(FieldColor.PINK, actionfield.getRightFieldColor(8));
        Assertions.assertEquals(FieldColor.BLUE, actionfield.getRightFieldColor(9));
        Assertions.assertEquals(FieldColor.GREY, actionfield.getRightFieldColor(10));
        Assertions.assertEquals(FieldColor.ORANGE, actionfield.getRightFieldColor(11));
        Assertions.assertEquals(FieldColor.GREEN, actionfield.getRightFieldColor(12));
        Assertions.assertEquals(FieldColor.GREY, actionfield.getRightFieldColor(13));
        Assertions.assertEquals(FieldColor.RED, actionfield.getRightFieldColor(14));
        Assertions.assertEquals(FieldColor.PINK, actionfield.getRightFieldColor(15));
        Assertions.assertNull(actionfield.getRightFieldColor(16));
    }

    @Test
    public void testGreyFieldColor (){
        actionfield.greyFieldColor();
        Assertions.assertEquals(2,actionfield.cardToPullBoth);
        Assertions.assertEquals(0,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testGreyFieldColorRandomCard (){
        actionfield.greyFieldColor();
        Assertions.assertFalse(actionfield.randomCard);
    }

    @Test
    public void testGreenFieldColor (){
        actionfield.greenFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullBoth);
        Assertions.assertEquals(0,actionfield.cardToPullCardlist);
        Assertions.assertEquals(1,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testGreenFieldColorRandomCard (){
        actionfield.greenFieldColor();
        Assertions.assertTrue(actionfield.randomCard);
    }

    @Test
    public void testOrangeFieldColor (){
        actionfield.orangeFieldColor();
        Assertions.assertEquals(3,actionfield.cardToPullBoth);
        Assertions.assertEquals(0,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testOrangeFieldColorRandomCard (){
        actionfield.orangeFieldColor();
        Assertions.assertFalse(actionfield.randomCard);
    }

    @Test
    public void testBlueFieldColor (){
        actionfield.blueFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullBoth);
        Assertions.assertEquals(1,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testBlueFieldColorRandomCard (){
        actionfield.blueFieldColor();
        Assertions.assertFalse(actionfield.randomCard);
    }

    @Test
    public void testRedFieldColor (){
        actionfield.redFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullBoth);
        Assertions.assertEquals(3,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testRedFieldColorRandomCard (){
        actionfield.redFieldColor();
        Assertions.assertFalse(actionfield.randomCard);
    }

    @Test
    public void testPurpleFieldColor (){
        actionfield.purpleFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullBoth);
        Assertions.assertEquals(0,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testPurpleFieldColorRandomCard (){
        actionfield.purpleFieldColor();
        Assertions.assertFalse(actionfield.randomCard);
    }

    @Test
    public void testPinkFieldColorCardToPullBoth (){
        actionfield.pinkFieldColor();
        Assertions.assertEquals(1,actionfield.cardToPullBoth);
    }

    @Test
    public void testPinkFieldColorCardToPullCardList (){
        actionfield.pinkFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullCardlist);
    }

    @Test
    public void testPinkFieldColorCardToPullDiscardpileList (){
        actionfield.pinkFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testPinkFieldColorRandomCard (){
        actionfield.pinkFieldColor();
        Assertions.assertFalse(actionfield.randomCard);
    }





}
