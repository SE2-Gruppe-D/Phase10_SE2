package com.example.phase10_se2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase10_se2.ENUM.PlayerColor;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindGameActivity extends AppCompatActivity {
    final String[] color = new String[1];
    String roomName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

        ListView listView = findViewById(R.id.roomList);
        ArrayList<String> gameRoomsList = new ArrayList<>();
        EditText editTextName = findViewById(R.id.usernameJoinGame);
        Button joinRoom = findViewById(R.id.joinRoomBtn);
        FirebaseFirestore database;
        ArrayAdapter<String> adapter;
        RadioButton radioRed = findViewById(R.id.radioButtonRed);
        RadioButton radioBlue = findViewById(R.id.radioButtonBlue);
        RadioButton radioGreen = findViewById(R.id.radioButtonGreen);
        RadioButton radioYellow = findViewById(R.id.radioButtonYellow);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioRed.setEnabled(false);
        radioBlue.setEnabled(false);
        radioGreen.setEnabled(false);
        radioYellow.setEnabled(false);
        ArrayList<String> activeGames = new ArrayList<>();


        adapter = new ArrayAdapter<>(FindGameActivity.this, android.R.layout.simple_list_item_1, gameRoomsList);


        database = FirebaseFirestore.getInstance();

        listView.setAdapter(adapter);
        database.collection("activeGames")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            activeGames.add(documentSnapshot.getString("RoomName"));
                        }
                    }
                });
        //get all information from database
        database.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //add all available rooms to gameRoomsList
                            if (!gameRoomsList.contains(document.getString("Room"))) {
                                if (!activeGames.contains(document.getString("Room"))) {
                                    gameRoomsList.add(document.getString("Room"));
                                    adapter.notifyDataSetChanged();
                                }

                            }
                        }
                    } else {
                        Log.d(TAG, "Error getting Data from Firestore: ", task.getException());
                    }
                });


        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            listView.setItemChecked(position, true);

            roomName = gameRoomsList.get(position);
            ArrayList<String> takenColors = new ArrayList<>();

            //reactivate all radio buttons so it is reset if more than one item is clicked
            radioRed.setEnabled(true);
            radioBlue.setEnabled(true);
            radioGreen.setEnabled(true);
            radioYellow.setEnabled(true);

            adapter.notifyDataSetChanged(); //update LV

            database.collection("users")
                    .whereEqualTo("Room", roomName)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //add all takenColors
                                takenColors.add(document.getString("Color"));
                            }
                        }
                        if (takenColors.contains("RED")) {
                            radioRed.setEnabled(false);
                        }
                        if (takenColors.contains("BLUE")) {
                            radioBlue.setEnabled(false);
                        }
                        if (takenColors.contains("GREEN")) {
                            radioGreen.setEnabled(false);
                        }
                        if (takenColors.contains("YELLOW")) {
                            radioYellow.setEnabled(false);
                        }
                        adapter.notifyDataSetChanged();
                    });


            joinRoom.setOnClickListener(view1 -> {
                if (editTextName.getText().toString().isEmpty()) {
                    Toast.makeText(FindGameActivity.this, "Username can't be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    color[0] = ((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    PlayerColor playerColor = null;
                    if (!color[0].equals("")) {
                        switch (color[0]) {
                            case "RED":
                                playerColor = PlayerColor.RED;
                                break;
                            case "BLUE":
                                playerColor = PlayerColor.BLUE;
                                break;
                            case "YELLOW":
                                playerColor = PlayerColor.YELLOW;
                                break;
                            case "GREEN":
                                playerColor = PlayerColor.GREEN;
                                break;
                            default:
                                break;
                        }
                    }
                    String playerName = editTextName.getText().toString();
                    Player player = new Player(playerName, playerColor, roomName, 1, 0, new ArrayList<>(), new ArrayList<>());


                    //create user
                    Map<String, Object> user = new HashMap<>();
                    user.put("Name", player.getName());
                    user.put("Color", player.getColor());
                    user.put("Room", roomName);
                    user.put("Phase", player.getPhaseNumber());
                    user.put("Points", player.getMinusPoints());
                    user.put("Handcards", player.getPlayerHand());
                    user.put("CardsField", player.getCardField());

                    database.collection("users")
                            .add(user)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(FindGameActivity.this, "joining game...", Toast.LENGTH_SHORT).show();
                                goToFindPlayer();
                            }).addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                }
            });
        });
    }

    public void goToFindPlayer() {
        Intent intent = new Intent(FindGameActivity.this, FindPlayer.class);
        intent.putExtra("CurrentRoom", roomName);
        intent.putExtra("Color", color[0]);
        startActivity(intent);
    }
}