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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindPlayer extends AppCompatActivity {
    String currentRoom = "";
    final String[] color = new String[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_player);

        TextView roomName = findViewById(R.id.RoomName);
        Button startGame = findViewById(R.id.startGame);
        ListView listView = findViewById(R.id.playerList);
        ArrayList<String> playerList = new ArrayList<>();
        FirebaseFirestore database;
        database = FirebaseFirestore.getInstance();


        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(FindPlayer.this, android.R.layout.simple_list_item_1, playerList);

        listView.setAdapter(adapter);

        currentRoom= getIntent().getExtras().getString("CurrentRoom");
        color[0]= getIntent().getExtras().getString("Color");
        roomName.setText(currentRoom);

        database.collection("users")
                .whereEqualTo("Room", currentRoom)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               playerList.add(document.getString("Name"));

                                    adapter.notifyDataSetChanged();
                                }

                        } else {
                            Log.d(TAG, "Error getting Data from Firestore: ", task.getException());
                        }
                    }
                });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playerList.size()<2){
                    startGame.setEnabled(false);
                    Toast.makeText(FindPlayer.this, "Not enough players...", Toast.LENGTH_SHORT).show();
                }
                else{
                    startGame.setEnabled(true);
                    Toast.makeText(FindPlayer.this, "Starting game with "+ playerList.size()+" players...", Toast.LENGTH_SHORT).show();
                    goToPlayfield();
                }
            }
        });
    }
    public void goToPlayfield(){
        Intent intent = new Intent(FindPlayer.this, Playfield.class);
        intent.putExtra("CurrentRoom", currentRoom);
        intent.putExtra("Color", color[0]);
        startActivity(intent);
    }
}
