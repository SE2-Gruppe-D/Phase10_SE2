package com.example.phase10_se2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindPlayer extends AppCompatActivity {
    String currentRoom = "";
    boolean started = false;
    final String[] color = new String[1];
    FirebaseFirestore database;
    ArrayAdapter<String> adapter;
    ArrayList<String> playerList = new ArrayList<>();
    Map<String, Object> currentRoomMap = new HashMap<>();

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

        currentRoom= getIntent().getExtras().getString("CurrentRoom");
        color[0]= getIntent().getExtras().getString("Color");
        roomName.setText(currentRoom);
        database.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null) {
                    getDataFromDatabase();
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        database.collection("activeGames").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                if (value != null) {
                    database.collection("activeGames")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (currentRoom.equals(document.getString("RoomName"))  && !started) {
                                                started = true;
                                                goToPlayfield();
                                            }
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting Data from Firestore: ", task.getException());
                                    }
                                }
                            });
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });


        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerList.size() < 2) {
                    startGame.setEnabled(false);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(FindPlayer.this, "Not enough players...", Toast.LENGTH_SHORT).show();
                } else {
                    startGame.setEnabled(true);
                    adapter.notifyDataSetChanged();
                    currentRoomMap.put("RoomName", currentRoom);

                    //eventlistener for startGame clicked -> all players go to playfield
                    database.collection("activeGames")
                            .add(currentRoomMap)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    //Toast.makeText(FindPlayer.this, "Starting game with " + playerList.size() + " players...", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FindPlayer.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
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

    public void goToPlayfield(){
        Intent intent = new Intent(FindPlayer.this, Playfield.class);
        intent.putExtra("CurrentRoom", currentRoom);
        intent.putExtra("Color", color[0]);
        startActivity(intent);
    }
    public  void getDataFromDatabase(){
        database.collection("users")
                .whereEqualTo("Room", currentRoom)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!playerList.contains(document.getString("Color"))&&!playerList.contains(document.getString("Name"))) {
                                    playerList.add(document.getString("Name"));

                                    adapter.notifyDataSetChanged();
                                }
                            }

                        } else {
                            Log.d(TAG, "Error getting Data from Firestore: ", task.getException());
                        }
                    }
                });
    }
}
