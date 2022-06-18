package com.example.phase10_se2;

import android.widget.ImageView;

public class CardUI {
    ImageView imageView;

    public CardUI(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setRotation(float rotation) {
        imageView.setRotation(rotation);
    }
}
