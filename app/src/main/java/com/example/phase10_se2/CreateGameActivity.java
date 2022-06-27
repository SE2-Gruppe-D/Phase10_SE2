package com.example.phase10_se2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase10_se2.enums.PlayerColor;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateGameActivity extends AppCompatActivity {
    String color = "RED";
    String roomName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        //have to save first as EditText to read value later
        EditText editTextName = findViewById(R.id.playerName);
        EditText editTextRoom = findViewById(R.id.roomName);

        RadioGroup rg = findViewById(R.id.radioGroup);
        Button createGame = findViewById(R.id.createGameRoomBtn);

        PlayerColor[] playerColor = {PlayerColor.RED};
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        rg.setOnCheckedChangeListener((radioGroup, checkedID) -> {
              color = ((RadioButton) findViewById(rg.getCheckedRadioButtonId()))
                    .getText().toString();


            switch (color) {
                case "RED":
                    playerColor[0] = PlayerColor.RED;
                    break;
                case "BLUE":
                    playerColor[0] = PlayerColor.BLUE;
                    break;
                case "YELLOW":
                    playerColor[0] = PlayerColor.YELLOW;
                    break;
                default:
                    playerColor[0] = PlayerColor.GREEN;
                    break;

            }
        });


        CollectionReference dbPlayers = database.collection("users");
        createGame.setOnClickListener(view -> {
            //convert content of editTexts to String using getText().toString() for right value in firestore
            String playerName = editTextName.getText().toString();
            roomName = editTextRoom.getText().toString();

            //create player with given input
            Player player = new Player(playerName, playerColor[0], roomName, 1, 0, new ArrayList<>(), new ArrayList<>());

            //add player and room to database
            Map<String, Object> user = new HashMap<>();
            user.put("Name", player.getName());
            user.put("Color", player.getColor());
            user.put("Room", player.getRoom());
            user.put("Phase", player.getPhaseNumber());
            user.put("Points", player.getMinusPoints());
            user.put("Handcards", player.getPlayerHand());
            user.put("CardsField", player.getCardField());


            dbPlayers
                    .add(user)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(CreateGameActivity.this, "creating new room...", Toast.LENGTH_SHORT).show();
                        goToFindPlayer();
                    }).addOnFailureListener(e -> Toast.makeText(CreateGameActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());


        });

    }

    public void goToFindPlayer() {
        Intent intent = new Intent(CreateGameActivity.this, FindPlayer.class);
        intent.putExtra("CurrentRoom", roomName);
        intent.putExtra("Color", color);
        startActivity(intent);
    }
}