package com.example.phase10_se2;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CardUIManager {
    private HashMap<List<String>, Integer> cardIds = new HashMap<>();
    private final String BLUE = "blue";
    private final String RED = "red";
    private final String YELLOW = "yellow";
    private final String GREEN = "green";

    public CardUIManager() {
        initHashMap();
    }

    void setCardImage(Cards cards, ImageView image) {
        image.setImageResource(cardID(cards));
    }

    private void initHashMap() {
        cardIds.put(Arrays.asList("1", BLUE), R.drawable.blau1);
        cardIds.put(Arrays.asList("2", BLUE), R.drawable.blau2);
        cardIds.put(Arrays.asList("3", BLUE), R.drawable.blau3);
        cardIds.put(Arrays.asList("4", BLUE), R.drawable.blau4);
        cardIds.put(Arrays.asList("5", BLUE), R.drawable.blau5);
        cardIds.put(Arrays.asList("6", BLUE), R.drawable.blau6);
        cardIds.put(Arrays.asList("7", BLUE), R.drawable.blau7);
        cardIds.put(Arrays.asList("8", BLUE), R.drawable.blau8);
        cardIds.put(Arrays.asList("9", BLUE), R.drawable.blau9);
        cardIds.put(Arrays.asList("10", BLUE), R.drawable.blau10);
        cardIds.put(Arrays.asList("11", BLUE), R.drawable.blau11);
        cardIds.put(Arrays.asList("12", BLUE), R.drawable.blau12);

        cardIds.put(Arrays.asList("1", RED), R.drawable.rot1);
        cardIds.put(Arrays.asList("2", RED), R.drawable.rot2);
        cardIds.put(Arrays.asList("3", RED), R.drawable.rot3);
        cardIds.put(Arrays.asList("4", RED), R.drawable.rot4);
        cardIds.put(Arrays.asList("5", RED), R.drawable.rot5);
        cardIds.put(Arrays.asList("6", RED), R.drawable.rot6);
        cardIds.put(Arrays.asList("7", RED), R.drawable.rot7);
        cardIds.put(Arrays.asList("8", RED), R.drawable.rot8);
        cardIds.put(Arrays.asList("9", RED), R.drawable.rot9);
        cardIds.put(Arrays.asList("10", RED), R.drawable.rot10);
        cardIds.put(Arrays.asList("11", RED), R.drawable.rot11);
        cardIds.put(Arrays.asList("12", RED), R.drawable.rot12);

        cardIds.put(Arrays.asList("1", YELLOW), R.drawable.gelb1);
        cardIds.put(Arrays.asList("2", YELLOW), R.drawable.gelb2);
        cardIds.put(Arrays.asList("3", YELLOW), R.drawable.gelb3);
        cardIds.put(Arrays.asList("4", YELLOW), R.drawable.gelb4);
        cardIds.put(Arrays.asList("5", YELLOW), R.drawable.gelb5);
        cardIds.put(Arrays.asList("6", YELLOW), R.drawable.gelb6);
        cardIds.put(Arrays.asList("7", YELLOW), R.drawable.gelb7);
        cardIds.put(Arrays.asList("8", YELLOW), R.drawable.gelb8);
        cardIds.put(Arrays.asList("9", YELLOW), R.drawable.gelb9);
        cardIds.put(Arrays.asList("10", YELLOW), R.drawable.gelb10);
        cardIds.put(Arrays.asList("11", YELLOW), R.drawable.gelb11);
        cardIds.put(Arrays.asList("12", YELLOW), R.drawable.gelb12);

        cardIds.put(Arrays.asList("1", GREEN), R.drawable.gruen1);
        cardIds.put(Arrays.asList("2", GREEN), R.drawable.gruen2);
        cardIds.put(Arrays.asList("3", GREEN), R.drawable.gruen3);
        cardIds.put(Arrays.asList("4", GREEN), R.drawable.gruen4);
        cardIds.put(Arrays.asList("5", GREEN), R.drawable.gruen5);
        cardIds.put(Arrays.asList("6", GREEN), R.drawable.gruen6);
        cardIds.put(Arrays.asList("7", GREEN), R.drawable.gruen7);
        cardIds.put(Arrays.asList("8", GREEN), R.drawable.gruen8);
        cardIds.put(Arrays.asList("9", GREEN), R.drawable.gruen9);
        cardIds.put(Arrays.asList("10", GREEN), R.drawable.gruen10);
        cardIds.put(Arrays.asList("11", GREEN), R.drawable.gruen11);
        cardIds.put(Arrays.asList("12", GREEN), R.drawable.gruen12);
    }

    //ID zuweisung Karten
    public int cardID(Cards cards) {
        List<String> key = new ArrayList<>();
        key.add(String.valueOf(cards.getValue()));
        key.add(cards.getColor());
        return cardIds.get(key);
    }
}
