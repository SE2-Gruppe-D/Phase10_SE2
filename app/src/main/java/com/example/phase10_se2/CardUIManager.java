package com.example.phase10_se2;

import android.content.res.Resources;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CardUIManager {
    private HashMap<List<String>, Integer> cardIds = new HashMap<>();

    public CardUIManager() {
        initHashMap();
    }

    void setCardImage(Cards cards, ImageView image) {
        image.setImageResource(cardID(cards));
    }

    private void initHashMap() {
        cardIds.put(Arrays.asList("1", "blue"), R.drawable.blau1);
        cardIds.put(Arrays.asList("2", "blue"), R.drawable.blau2);
        cardIds.put(Arrays.asList("3", "blue"), R.drawable.blau3);
        cardIds.put(Arrays.asList("4", "blue"), R.drawable.blau4);
        cardIds.put(Arrays.asList("5", "blue"), R.drawable.blau5);
        cardIds.put(Arrays.asList("6", "blue"), R.drawable.blau6);
        cardIds.put(Arrays.asList("7", "blue"), R.drawable.blau7);
        cardIds.put(Arrays.asList("8", "blue"), R.drawable.blau8);
        cardIds.put(Arrays.asList("9", "blue"), R.drawable.blau9);
        cardIds.put(Arrays.asList("10", "blue"), R.drawable.blau10);
        cardIds.put(Arrays.asList("11", "blue"), R.drawable.blau11);
        cardIds.put(Arrays.asList("12", "blue"), R.drawable.blau12);

        cardIds.put(Arrays.asList("1", "red"), R.drawable.rot1);
        cardIds.put(Arrays.asList("2", "red"), R.drawable.rot2);
        cardIds.put(Arrays.asList("3", "red"), R.drawable.rot3);
        cardIds.put(Arrays.asList("4", "red"), R.drawable.rot4);
        cardIds.put(Arrays.asList("5", "red"), R.drawable.rot5);
        cardIds.put(Arrays.asList("6", "red"), R.drawable.rot6);
        cardIds.put(Arrays.asList("7", "red"), R.drawable.rot7);
        cardIds.put(Arrays.asList("8", "red"), R.drawable.rot8);
        cardIds.put(Arrays.asList("9", "red"), R.drawable.rot9);
        cardIds.put(Arrays.asList("10", "red"), R.drawable.rot10);
        cardIds.put(Arrays.asList("11", "red"), R.drawable.rot11);
        cardIds.put(Arrays.asList("12", "red"), R.drawable.rot12);

        cardIds.put(Arrays.asList("1", "yellow"), R.drawable.gelb1);
        cardIds.put(Arrays.asList("2", "yellow"), R.drawable.gelb2);
        cardIds.put(Arrays.asList("3", "yellow"), R.drawable.gelb3);
        cardIds.put(Arrays.asList("4", "yellow"), R.drawable.gelb4);
        cardIds.put(Arrays.asList("5", "yellow"), R.drawable.gelb5);
        cardIds.put(Arrays.asList("6", "yellow"), R.drawable.gelb6);
        cardIds.put(Arrays.asList("7", "yellow"), R.drawable.gelb7);
        cardIds.put(Arrays.asList("8", "yellow"), R.drawable.gelb8);
        cardIds.put(Arrays.asList("9", "yellow"), R.drawable.gelb9);
        cardIds.put(Arrays.asList("10", "yellow"), R.drawable.gelb10);
        cardIds.put(Arrays.asList("11", "yellow"), R.drawable.gelb11);
        cardIds.put(Arrays.asList("12", "yellow"), R.drawable.gelb12);

        cardIds.put(Arrays.asList("1", "green"), R.drawable.gruen1);
        cardIds.put(Arrays.asList("2", "green"), R.drawable.gruen2);
        cardIds.put(Arrays.asList("3", "green"), R.drawable.gruen3);
        cardIds.put(Arrays.asList("4", "green"), R.drawable.gruen4);
        cardIds.put(Arrays.asList("5", "green"), R.drawable.gruen5);
        cardIds.put(Arrays.asList("6", "green"), R.drawable.gruen6);
        cardIds.put(Arrays.asList("7", "green"), R.drawable.gruen7);
        cardIds.put(Arrays.asList("8", "green"), R.drawable.gruen8);
        cardIds.put(Arrays.asList("9", "green"), R.drawable.gruen9);
        cardIds.put(Arrays.asList("10", "green"), R.drawable.gruen10);
        cardIds.put(Arrays.asList("11", "green"), R.drawable.gruen11);
        cardIds.put(Arrays.asList("12", "green"), R.drawable.gruen12);
    }

    //ID zuweisung Karten
    public int cardID(Cards cards) { //TODO
        List<String> key = new ArrayList<>();
        key.add(String.valueOf((cards.getID()%12)+1));
        key.add(cards.getColor());
        System.out.println("POOP " + key);
        int drawableID = cardIds.get(key);
        return drawableID;
    }
}
