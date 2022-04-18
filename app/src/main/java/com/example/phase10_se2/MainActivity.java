package com.example.phase10_se2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        Player p = new Player("nonde", PlayerColor.GREEN);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            DiceFragment diceFragment = DiceFragment.newInstance();
//            diceFragment.setPlayer(p);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.dice_fragment, DiceFragment.class, null)
                    .commit();
        });
    }
}