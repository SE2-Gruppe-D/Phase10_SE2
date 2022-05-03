package com.example.phase10_se2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button spielErstellen;
    Button spielBeitreten;
    Button hint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spielErstellen=findViewById(R.id.createGameBtn);
        spielErstellen.setOnClickListener(view -> createGame());

        spielBeitreten=findViewById(R.id.joinGameBtn);
        spielBeitreten.setOnClickListener(view -> joinGame());

        hint=findViewById(R.id.button4);
        hint.setOnClickListener(view -> showPlayRules());


    }
    public void createGame(){
        Intent intent = new Intent(this, CreateGameActivity.class);
        startActivity(intent);
    }
    public void joinGame() {
        Intent intent = new Intent(this, FindGameActivity.class);
        startActivity(intent);
    }
    public void showPlayRules(){
        Intent intent= new Intent(this, PlayRules.class);
        startActivity(intent);
    }
    public void showPlayField(){
        Intent i= new Intent(MainActivity.this, Playfield.class);
        startActivity(i);
    }
}