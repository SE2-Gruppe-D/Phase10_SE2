package com.example.phase10_se2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindPlayer extends AppCompatActivity {
    final String[] color = new String[1];
    String currentRoom = "";
    boolean started = false;
    FirebaseFirestore database;
    ArrayAdapter<String> adapter;
    ArrayList<String> playerList = new ArrayList<>();
    Map<String, Object> currentRoomMap = new HashMap<>();
    final static String COLOR_CONST = "Color";
    final static String ACTIVE_GAMES_CONST = "activeGames";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_player);

        TextView roomName = findViewById(R.id.RoomName);
        Button startGame = findViewById(R.id.startGame);
        ListView listView = findViewById(R.id.playerList);
        database = FirebaseFirestore.getInstance();


        adapter = new ArrayAdapter<>(FindPlayer.this, android.R.layout.simple_list_item_1, playerList);

        listView.setAdapter(adapter);

        currentRoom = getIntent().getExtras().getString("CurrentRoom");
        color[0] = getIntent().getExtras().getString(COLOR_CONST);
        roomName.setText(currentRoom);
        database.collection("users").addSnapshotListener((snapshot, e) -> {

            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }

            if (snapshot != null) {
                getDataFromDatabase();
            } else {
                Log.d(TAG, "Current data: null");
            }
        });

        database.collection(ACTIVE_GAMES_CONST).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen failed.", error);
                return;
            }
            if (value != null) {
                database.collection(ACTIVE_GAMES_CONST)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (currentRoom.equals(document.getString("RoomName")) && !started) {
                                        started = true;
                                        goToPlayfield();
                                    }
                                }
                            } else {
                                Log.d(TAG, "Error getting Data from Firestore: ", task.getException());
                            }
                        });
            } else {
                Log.d(TAG, "Current data: null");
            }
        });


        startGame.setOnClickListener(view -> {
            if (playerList.size() < 2) {
                startGame.setEnabled(false);
                adapter.notifyDataSetChanged();
                Toast.makeText(FindPlayer.this, "Not enough players...", Toast.LENGTH_SHORT).show();
            } else {
                startGame.setEnabled(true);
                adapter.notifyDataSetChanged();
                currentRoomMap.put("RoomName", currentRoom);

                //eventlistener for startGame clicked -> all players go to playfield
                database.collection(ACTIVE_GAMES_CONST)
                        .add(currentRoomMap)
                        .addOnSuccessListener(documentReference -> {
                        }).addOnFailureListener(e -> Toast.makeText(FindPlayer.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        started = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        started = false;
    }

    public void goToPlayfield() {
        Intent intent = new Intent(FindPlayer.this, Playfield.class);
        intent.putExtra("CurrentRoom", currentRoom);
        intent.putExtra(COLOR_CONST, color[0]);
        startActivity(intent);
    }

    public void getDataFromDatabase() {
        database.collection("users")
                .whereEqualTo("Room", currentRoom)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (!playerList.contains(document.getString(COLOR_CONST)) && !playerList.contains(document.getString("Name"))) {
                                playerList.add(document.getString("Name"));

                                adapter.notifyDataSetChanged();
                            }
                        }

                    } else {
                        Log.d(TAG, "Error getting Data from Firestore: ", task.getException());
                    }
                });
    }
}
