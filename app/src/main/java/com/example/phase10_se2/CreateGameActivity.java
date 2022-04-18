package com.example.phase10_se2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        String playerName = findViewById(R.id.playerName).toString();
        RadioGroup rg =  findViewById(R.id.radioGroup);
        Button createGame = findViewById(R.id.createGameRoomBtn);
        String roomName = findViewById(R.id.roomName).toString();
        final String[] color = {""};
        final PlayerColor[] playerColor = {null};


        FirebaseFirestore database = FirebaseFirestore.getInstance();
        playerColor[0] = PlayerColor.RED;

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                    color[0] = ((RadioButton) findViewById(rg.getCheckedRadioButtonId()))
                            .getText().toString();


                    switch (color[0]) {
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
                }
            });



        CollectionReference dbPlayers = database.collection("users");
        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create player with given input
                Player player = new Player (playerName, playerColor[0], roomName);

                //add player and room to database
                Map<String, Object> user = new HashMap<>();
                user.put("Name", player.getName());
                user.put("Color", player.getColor());
                user.put("Room", player.getRoom());

                dbPlayers
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(CreateGameActivity.this, "creating new game...", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateGameActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }


        });
    }
}