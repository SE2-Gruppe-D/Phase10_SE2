package com.example.phase10_se2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        spielErstellen=findViewById(R.id.button2);
        spielErstellen.setOnClickListener(view -> createGame());

        spielBeitreten=findViewById(R.id.button);
        spielBeitreten.setOnClickListener(view -> joinGame());

        hint=findViewById(R.id.button4);
        hint.setOnClickListener(view -> showPlayRules());

    }
    public void createGame(){
    }
    public void joinGame() {
    }
    public void showPlayRules(){
        Intent intent= new Intent(this, PlayRules.class);
        startActivity(intent);
    }
}