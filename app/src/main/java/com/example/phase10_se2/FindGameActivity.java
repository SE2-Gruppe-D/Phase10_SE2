package com.example.phase10_se2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindGameActivity extends AppCompatActivity {
    final String[] roomName = {""};
    final String[] color = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

        Log.i("TAG", "Helloo");

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


        adapter = new ArrayAdapter<>(FindGameActivity.this, android.R.layout.simple_list_item_1, gameRoomsList);


        database = FirebaseFirestore.getInstance();

        listView.setAdapter(adapter);

        //get all information from database
        database.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //add all available rooms to gameRoomsList
                                if(!gameRoomsList.contains(document.getString("Room"))){
                                gameRoomsList.add(document.getString("Room"));

                                adapter.notifyDataSetChanged();
                            }
                            }
                        } else {
                            Log.d(TAG, "Error getting Data from Firestore: ", task.getException());
                        }
                    }
                });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                listView.setItemChecked(position, true);
                roomName[0] = gameRoomsList.get(position);
                ArrayList<String> takenColors = new ArrayList<>();

                //reactivate all radio buttons so it is reset if more than one item is clicked
                radioRed.setEnabled(true);
                radioBlue.setEnabled(true);
                radioGreen.setEnabled(true);
                radioYellow.setEnabled(true);

                adapter.notifyDataSetChanged(); //update LV

                database.collection("users")
                        .whereEqualTo("Room", roomName[0])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
                            }

                        });



                joinRoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                        Player player = new Player(playerName, playerColor, roomName[0]);


                        //create user
                        Map<String, Object> user = new HashMap<>();
                        user.put("Name", player.getName());
                        user.put("Color", player.getColor());
                        user.put("Room", roomName[0]);

                        database.collection("users")
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(FindGameActivity.this, "joining game...", Toast.LENGTH_SHORT).show();
                                        goToPlayField();
                                    }
                                }).addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
                    }

                });
            }
        });
    }
    public void goToPlayField(){
        Intent intent = new Intent(FindGameActivity.this, Playfield.class);
        intent.putExtra("CurrentRoom", roomName[0]);
        intent.putExtra("Color", color[0]);
        startActivity(intent);
    }
}

