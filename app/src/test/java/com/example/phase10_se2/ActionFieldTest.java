package com.example.phase10_se2;

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
        Assertions.assertEquals(null, actionfield.getRightFieldColor(16));
    }

    @Test
    public void testGreyFieldColor (){
        actionfield.greyFieldColor();
        Assertions.assertEquals(2,actionfield.cardToPullBoth);
        Assertions.assertEquals(0,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testGreenFieldColor (){
        actionfield.greenFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullBoth);
        Assertions.assertEquals(0,actionfield.cardToPullCardlist);
        Assertions.assertEquals(1,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testOrangeFieldColor (){
        actionfield.orangeFieldColor();
        Assertions.assertEquals(3,actionfield.cardToPullBoth);
        Assertions.assertEquals(0,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testBlueFieldColor (){
        actionfield.blueFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullBoth);
        Assertions.assertEquals(1,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testRedFieldColor (){
        actionfield.redFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullBoth);
        Assertions.assertEquals(3,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }

    @Test
    public void testPurpleFieldColor (){
        actionfield.purpleFieldColor();
        Assertions.assertEquals(0,actionfield.cardToPullBoth);
        Assertions.assertEquals(0,actionfield.cardToPullCardlist);
        Assertions.assertEquals(0,actionfield.cardToPullDiscardpileList);
    }


}
