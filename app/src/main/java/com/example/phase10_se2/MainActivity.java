package com.example.phase10_se2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button spielErstellen;
    Button spielBeitreten;
    Button hint;
    private MediaPlayer mediaPlayer;


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

        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.jazzcomedy);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

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

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}