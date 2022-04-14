package com.example.phase10_se2;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

        ListView listView = findViewById(R.id.roomList);
        ArrayList<Player> playerList = new ArrayList<>();
        List<String> gameRoomsList;
        String playerName = findViewById(R.id.usernameJoinGame).toString();
        Button joinRoom= findViewById(R.id.joinRoomBtn);
        final String[] roomName = {""};
        FirebaseFirestore database;
        ArrayAdapter<String> adapter;
        RadioButton radioRed = findViewById(R.id.radioButtonRed);
        RadioButton radioBlue = findViewById(R.id.radioButtonBlue);
        RadioButton radioGreen = findViewById(R.id.radioButtonGreen);
        RadioButton radioYellow = findViewById(R.id.radioButtonYellow);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        String color;


        List<PlayerColor> availableColors = new ArrayList<>();
        gameRoomsList = new ArrayList<>();


        adapter = new ArrayAdapter<>(FindGameActivity.this, android.R.layout.simple_list_item_1,gameRoomsList);

        //get all users from database
        database = FirebaseFirestore.getInstance();
        database.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d : list){
                                Player p = d.toObject(Player.class);
                                playerList.add(p);
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }
                });

        listView.setAdapter(adapter);

        //add all available rooms to gameRoomsList
        for (Player p: playerList
             ) {
            if(!gameRoomsList.contains(p.getRoom())) {
                gameRoomsList.add(p.getRoom());
                adapter.notifyDataSetChanged();
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                roomName[0] = gameRoomsList.get(position);
                for (Player p : playerList
                ) {
                    if(p.getRoom().equals(roomName[0])){
                    if (!p.getColor().equals(PlayerColor.RED)) {
                        availableColors.add(PlayerColor.RED);
                    }
                    if (!p.getColor().equals(PlayerColor.BLUE)) {
                        availableColors.add(PlayerColor.BLUE);
                    }
                    if (!p.getColor().equals(PlayerColor.GREEN)) {
                        availableColors.add(PlayerColor.GREEN);
                    }
                    if (!p.getColor().equals(PlayerColor.YELLOW)) {
                        availableColors.add(PlayerColor.YELLOW);
                    }
                }
            } }
        });
        if(!availableColors.contains(PlayerColor.RED)){
            radioRed.setEnabled(false);
        }
        if(!availableColors.contains(PlayerColor.BLUE)){
            radioBlue.setEnabled(false);
        }
        if(!availableColors.contains(PlayerColor.GREEN)){
            radioGreen.setEnabled(false);
        }
        if(!availableColors.contains(PlayerColor.YELLOW)){
            radioYellow.setEnabled(false);
        }
        color = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId()))
                .getText().toString();

        PlayerColor playerColor = null;
        if(!color.equals("")){
            switch (color){
                case "RED": playerColor = PlayerColor.RED;
                    break;
                case "BLUE": playerColor = PlayerColor.BLUE;
                    break;
                case "YELLOW": playerColor = PlayerColor.YELLOW;
                    break;
                default: playerColor = PlayerColor.GREEN;
                    break;
            }
        }
        Player player = new Player(playerName, playerColor, roomName[0]);

        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create user
                Map<String, Object> user = new HashMap<>();
                user.put("Name", player.getName());
                user.put("Color", player.getColor());
                user.put("Room", roomName[0]);

                database.collection("users")
                .add(user)
                        .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                        .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
            }

    });


    }

}