package com.example.phase10_se2;

import androidx.appcompat.app.AppCompatActivity;

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



    }
    public void createGame(){
    }
}