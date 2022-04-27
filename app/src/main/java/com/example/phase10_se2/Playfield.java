package com.example.phase10_se2;

import static android.os.SystemClock.sleep;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Playfield extends AppCompatActivity {
    DiceFragment diceFragment;
    ImageView deckcard;
    ImageView defaultcard;
    LinearLayout layoutPlayer1;
    LinearLayout layoutPlayer2;
    LinearLayout layoutPlayer3;
    LinearLayout layoutPlayer4;

  

    ArrayList<Cards> cardlist;
    ArrayList<ImageView> Imagelist;
    ArrayList<Cards> drawpileList;      //Ziehstapel
    ArrayList<Cards> discardpileList;      //Ablagestapel
    TextView leererAblagestapel;

    Button btnHideAktionskarte;
    Button btnShowAktionskarte;
    ImageView ivShowAktionskarte;
    TextView tvAktuellePhase;

    Player playerGreen;
    Player playerRed;
    Player playerYellow;
    Player playerBlue;
    Player primaryPlayer;


    //light sensor
    SensorManager sm;
    SensorEventListener lightListener;
    Sensor light;
    AlertDialog.Builder builder;
    float floatThreshold = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfield);
        builder = new AlertDialog.Builder(Playfield.this);

        String currentRoom= getIntent().getExtras().getString("CurrentRoom");
        String userColor= getIntent().getExtras().getString("Color");
        Toast.makeText(this, "YOU ARE THE "+userColor+" PLAYER!", Toast.LENGTH_SHORT).show();
        FirebaseFirestore database;
        database = FirebaseFirestore.getInstance();    //verknuepfung
        database.collection("users")
                .whereEqualTo("Room", currentRoom)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()) {
                                if (Objects.equals(documentSnapshot.getString("Color"), userColor)){
                                    switch (userColor){
                                        case "RED":
                                            playerRed=new Player(documentSnapshot.getString("Name"), PlayerColor.RED, currentRoom);
                                            primaryPlayer=playerRed;
                                            break;
                                        case "BLUE":
                                            playerBlue=new Player(documentSnapshot.getString("Name"), PlayerColor.BLUE, currentRoom);
                                            primaryPlayer=playerBlue;
                                            break;
                                        case "YELLOW":
                                            playerYellow=new Player(documentSnapshot.getString("Name"), PlayerColor.YELLOW, currentRoom);
                                            primaryPlayer=playerYellow;
                                            break;
                                        case "GREEN":
                                            playerGreen=new Player(documentSnapshot.getString("Name"), PlayerColor.GREEN, currentRoom);
                                            primaryPlayer=playerGreen;
                                            break;
                                        default:
                                            break;

                                    }
                                    Log.i("-------------------------------------------", "Color: "+ primaryPlayer.getColor());

                                }

                                if (Objects.equals(documentSnapshot.getString("Color"), "RED")){
                                    playerRed=new Player(documentSnapshot.getString("Name"), PlayerColor.RED, currentRoom);
                                    playerRed.setPlayerview(findViewById(R.id.ivPR));
                                    playerRed.getPlayerview().setVisibility(View.VISIBLE);
                                }
                                if (Objects.equals(documentSnapshot.getString("Color"), "BLUE")){
                                    playerBlue=new Player(documentSnapshot.getString("Name"), PlayerColor.BLUE, currentRoom);
                                    playerBlue.setPlayerview(findViewById(R.id.ivPB));
                                    playerBlue.getPlayerview().setVisibility(View.VISIBLE);
                                }
                                if (Objects.equals(documentSnapshot.getString("Color"), "YELLOW")){
                                    playerYellow=new Player(documentSnapshot.getString("Name"), PlayerColor.YELLOW, currentRoom);
                                    playerYellow.setPlayerview(findViewById(R.id.ivPY));
                                    playerYellow.getPlayerview().setVisibility(View.VISIBLE);
                                }
                                if (Objects.equals(documentSnapshot.getString("Color"), "GREEN")){
                                    playerGreen=new Player(documentSnapshot.getString("Name"), PlayerColor.GREEN, currentRoom);
                                    playerGreen.setPlayerview(findViewById(R.id.ivPG));
                                    playerGreen.getPlayerview().setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    }
                });


        //entfernt die label Leiste (Actionbar) auf dem Playfield
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();

        //show dice
        diceFragment = DiceFragment.newInstance();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .show(diceFragment)
                .commit();
      

        btnHideAktionskarte=findViewById(R.id.btnHideAk);
        btnShowAktionskarte=findViewById(R.id.btnShowAk);
        ivShowAktionskarte=findViewById(R.id.ivShowAk);
        tvAktuellePhase=findViewById(R.id.tvAP);


        //Aktionskarte einblenden Show und Hide button tauschen
        btnShowAktionskarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivShowAktionskarte.setVisibility(View.VISIBLE);
                btnHideAktionskarte.setVisibility(View.VISIBLE);
                btnShowAktionskarte.setVisibility(View.INVISIBLE);
            }
        });
        //Aktionskarte ausblenden Hide und Show button austauschen
        btnHideAktionskarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivShowAktionskarte.setVisibility(View.INVISIBLE);
                btnHideAktionskarte.setVisibility(View.INVISIBLE);
                btnShowAktionskarte.setVisibility(View.VISIBLE);
            }
        });

        drawpileList= new ArrayList<>();
        discardpileList= new ArrayList<>();

        cardlist = new ArrayList<>();
        Imagelist = new ArrayList<>();

        //alle 96 Karten werden in eine ArrayList gespeichert
        //erstelle alle Blauen Karten
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("blue", (i % 12) + 1, null, i+1);  //%12 weil, es wird bei 0 gestartet und immer +1 gerechnet & somit wird jeder Karte doppelt eingefügt
            cardlist.add(card);
        }
        //erstelle alle Roten Karten
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("red", (i % 12) + 1, null, i+25);
            cardlist.add(card);
        }
        //erstelle alle Gelben Karten
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("yellow", (i % 12) + 1, null, i+49);
            cardlist.add(card);
        }
        //erstelle alle Gruenen Karte
        for (int i = 0; i < 24; i++) {
            Cards card = new Cards("green", (i % 12) + 1, null, i+73);
            cardlist.add(card);
        }

        //dynamisches erstellen der Karten ImageViews
        for(int i=0; i<96; i++){
            cardlist.get(i).setCardUI(createCardUI(cardlist.get(i)));
        }

        drawpileList.addAll(cardlist);

        deckcard= findViewById(R.id.deckblatt);
        defaultcard=findViewById(R.id.defaultcard);
        leererAblagestapel=findViewById(R.id.leererStapel);
        layoutPlayer1=findViewById(R.id.player1);
        layoutPlayer2=findViewById(R.id.player2);
        layoutPlayer3=findViewById(R.id.player3);
        layoutPlayer4=findViewById(R.id.player4);



        //Karten werden gemischt
        Collections.shuffle(cardlist);

        //Handkarten

        for(int i = 0; i<10;i++){
            if (playerBlue != null) {
                updateHand(playerBlue.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0);
            }
            if (playerRed != null) {
                updateHand(playerRed.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0);
            }
            if (playerYellow != null) {
                updateHand(playerYellow.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90);
            }
            if (playerGreen != null) {
                updateHand(playerGreen.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90);
            }
        }

        //Player Blue, Red, Yellow, Green
        deckcard.setOnClickListener(view -> {
               addCard();
        });



        //random Defaultcard
        Random rand = new Random();
        Cards randomCard = cardlist.get(rand.nextInt(cardlist.size()));
        cardlist.remove(randomCard);
        discardpileList.add(randomCard);
        defaultcard.setImageDrawable(createCardUI(randomCard).getDrawable());



        defaultcard.setOnClickListener(view -> {
                addCardsDiscardpile();
        });

        //light sensor to accuse of cheating
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
            float floatSensorValue = sensorEvent.values[0];
                if (floatSensorValue < floatThreshold){
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        sm.registerListener(lightListener, light, SensorManager.SENSOR_DELAY_NORMAL);


        //Alert dialog accuse someone of cheating
        builder.setTitle("Found a cheater?")
        .setMessage("Are you sure, you want to accuse 'CurrentPlayer' of cheating?")
                .setCancelable(false)
        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //check if cheating == true
                //give consequences
                //if accused right:
                Toast.makeText(Playfield.this, "PlayerXY cheated, you were right!", Toast.LENGTH_SHORT).show();
                //if accused wrong:
                Toast.makeText(Playfield.this, "PlayerXY did not cheat, you were wrong!", Toast.LENGTH_SHORT).show();
            }
        })
           .setNegativeButton("NO", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   Toast.makeText(Playfield.this, "No one got accused!", Toast.LENGTH_SHORT).show();
               }
           });



    }

    //Momentan kann nur der player1 eine Karte ziehen
    protected void addCardsDiscardpile() {
        if (discardpileList.size() != 0) {
            updateHand(playerBlue.getPlayerHand(), discardpileList.get(0), layoutPlayer1,0);
            discardpileList.remove(0);
        }else{
            leererAblagestapel.setVisibility(View.VISIBLE);
        }
    }

    protected void addRandomCardsDiscardpile() {
        if (discardpileList.size() != 0) {
            Random rand = new Random();
            Cards randomCard = discardpileList.get(rand.nextInt(discardpileList.size()));
            updateHand(playerBlue.getPlayerHand(), randomCard, layoutPlayer1,0);
            discardpileList.remove(randomCard);
        }else{
            leererAblagestapel.setVisibility(View.VISIBLE);
        }
    }


    //Karten werden angeordnet
    private void updateHand(List list, Cards cards, LinearLayout linearLayout, int grad){
        list.add(cards);
        linearLayout.addView(cards.getCardUI());
        //if(primaryPlayer.getColor().equals(PlayerColor.BLUE)&& list.equals(player1HandBlue)){
        //}
        cards.getCardUI().setVisibility(View.VISIBLE);      //Aktueller Spieler sichtbar
        drawpileList.add(cardlist.get(0));
        cardlist.remove(0);
        cards.getCardUI().setRotation(grad);

    }

    //Karte ziehen
    protected void addCard(){
        if (playerBlue != null) {
            updateHand(playerBlue.getPlayerHand(), cardlist.get(0), layoutPlayer1,0);
        }
        if (playerRed != null) {
            updateHand(playerRed.getPlayerHand(), cardlist.get(0), layoutPlayer2,0);
        }
        if (playerYellow != null) {
            updateHand(playerYellow.getPlayerHand(), cardlist.get(0), layoutPlayer3,90);
        }
        if (playerGreen != null) {
            updateHand(playerGreen.getPlayerHand(), cardlist.get(0), layoutPlayer4,-90);
        }
    }

    //Stapel leer
    private void resetDrawPile(){
        cardlist.addAll(drawpileList);
        Collections.shuffle(cardlist);
        //drawpileList.removeAll(drawpileList);
    }

    private ImageView createCardUI(Cards cards){
        ImageView imageView= new ImageView(getApplicationContext());
        setCardImage(cards, imageView);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(35, 120, 1);
        imageView.setLayoutParams(params);
        imageView.setTag("c"+ cards.getID());
        imageView.setVisibility(View.INVISIBLE);
        imageView.setClickable(true);

        return imageView;
    }

    private void setCardImage(Cards cards, ImageView image){
        cards.setCardUI(image);         //Imageview in card setzen
        int Id=0;       //grafik von Imageview setzen

        if(cards.getColor().equals("blue")){
            Id=Id+100;

        }else if(cards.getColor().equals("yellow")){
            Id=Id+200;

        }else if(cards.getColor().equals("green")){
            Id=Id+300;

        }else if(cards.getColor().equals("red")){
            Id=Id+400;
        }
        Id=Id+cards.getValue();
        cardID(Id, image);
    }

    //ID zuweisung Karten
    private void cardID(int card, ImageView image) {
        switch (card) {
            case (101):
                image.setImageResource(R.drawable.blau1);
                break;

            case (102):
                image.setImageResource(R.drawable.blau2);
                break;

            case (103):
                image.setImageResource(R.drawable.blau3);
                break;

            case (104):
                image.setImageResource(R.drawable.blau4);
                break;

            case (105):
                image.setImageResource(R.drawable.blau5);
                break;

            case (106):
                image.setImageResource(R.drawable.blau6);
                break;

            case (107):
                image.setImageResource(R.drawable.blau7);
                break;

            case (108):
                image.setImageResource(R.drawable.blau8);
                break;

            case (109):
                image.setImageResource(R.drawable.blau9);
                break;

            case (110):
                image.setImageResource(R.drawable.blau10);
                break;

            case (111):
                image.setImageResource(R.drawable.blau11);
                break;

            case (112):
                image.setImageResource(R.drawable.blau12);
                break;


            case (201):
                image.setImageResource(R.drawable.gelb1);
                break;

            case (202):
                image.setImageResource(R.drawable.gelb2);
                break;

            case (203):
                image.setImageResource(R.drawable.gelb3);
                break;

            case (204):
                image.setImageResource(R.drawable.gelb4);
                break;

            case (205):
                image.setImageResource(R.drawable.gelb5);
                break;

            case (206):
                image.setImageResource(R.drawable.gelb6);
                break;

            case (207):
                image.setImageResource(R.drawable.gelb7);
                break;

            case (208):
                image.setImageResource(R.drawable.gelb8);
                break;

            case (209):
                image.setImageResource(R.drawable.gelb9);
                break;

            case (210):
                image.setImageResource(R.drawable.gelb10);
                break;

            case (211):
                image.setImageResource(R.drawable.gelb11);
                break;

            case (212):
                image.setImageResource(R.drawable.gelb12);
                break;

            case (301):
                image.setImageResource(R.drawable.gruen1);
                break;

            case (302):
                image.setImageResource(R.drawable.gruen2);
                break;

            case (303):
                image.setImageResource(R.drawable.gruen3);
                break;

            case (304):
                image.setImageResource(R.drawable.gruen4);
                break;

            case (305):
                image.setImageResource(R.drawable.gruen5);
                break;

            case (306):
                image.setImageResource(R.drawable.gruen6);
                break;

            case (307):
                image.setImageResource(R.drawable.gruen7);
                break;

            case (308):
                image.setImageResource(R.drawable.gruen8);
                break;

            case (309):
                image.setImageResource(R.drawable.gruen9);
                break;

            case (310):
                image.setImageResource(R.drawable.gruen10);
                break;

            case (311):
                image.setImageResource(R.drawable.gruen11);
                break;

            case (312):
                image.setImageResource(R.drawable.gruen12);
                break;

            case (401):
                image.setImageResource(R.drawable.rot1);
                break;

            case (402):
                image.setImageResource(R.drawable.rot2);
                break;

            case (403):
                image.setImageResource(R.drawable.rot3);
                break;

            case (404):
                image.setImageResource(R.drawable.rot4);
                break;

            case (405):
                image.setImageResource(R.drawable.rot5);
                break;

            case (406):
                image.setImageResource(R.drawable.rot6);
                break;

            case (407):
                image.setImageResource(R.drawable.rot7);
                break;

            case (408):
                image.setImageResource(R.drawable.rot8);
                break;

            case (409):
                image.setImageResource(R.drawable.rot9);
                break;

            case (410):
                image.setImageResource(R.drawable.rot10);
                break;

            case (411):
                image.setImageResource(R.drawable.rot11);
                break;

            case (412):
                image.setImageResource(R.drawable.rot12);
                break;

        }
    }
    private void setPhasenText(int phase) {
        switch (phase) {
            case (1):
                tvAktuellePhase.setText("4 Zwillinge");
                break;

            case (2):
                tvAktuellePhase.setText("6 Karten einer Farbe");
                break;

            case (3):
                tvAktuellePhase.setText("1 Vierling + 1 Viererfolge");
                break;

            case (4):
                tvAktuellePhase.setText("1 Achterfolge");
                break;

            case (5):
                tvAktuellePhase.setText("7 Karten einer Farbe");
                break;

            case (6):
                tvAktuellePhase.setText("1 Neunerfolge");
                break;

            case (7):
                tvAktuellePhase.setText("2 Vierlinge");
                break;

            case (8):
                tvAktuellePhase.setText("1 Viererfolge einer Farbe + 1 Drilling");
                break;

            case (9):
                tvAktuellePhase.setText("1 Fünfling + 1 Drilling");
                break;

            case (10):
                tvAktuellePhase.setText("1 Fünfling + 1 Dreierfolge einer Farbe");
                break;

        }
    }



        private boolean checkblue(FieldColor fieldColor){
            if(fieldColor.equals(FieldColor.BLUE)){
                //Code..
            }
            return true;
        }


    public void decideStartingPlayer() {
        //get array of active players
        ArrayList<Player> activePlayers = getActivePlayers();
        SortedMap<Integer, Player> startingDiceValues = new TreeMap<>();

        for (Player player : activePlayers) {
            int lastDiceValue = 0;

            Toast.makeText(getApplicationContext(), player.getName() + "'s turn", Toast.LENGTH_LONG);
            if (player.equals(primaryPlayer)) {
                diceFragment.register();

                while (diceFragment.getAcceleration() < 0) {
                    sleep(10);
                }
                while (diceFragment.getAcceleration() > 1) {
                    lastDiceValue = diceFragment.getLastDiceValue();
                    sleep(10);
                }

                player.move(lastDiceValue);
            }
            Toast.makeText(getApplicationContext(), "Player " + player.getName() + " threw: " + lastDiceValue, Toast.LENGTH_LONG);

            startingDiceValues.put(lastDiceValue, player);
        }

        //set starting order in player class
        Set<Map.Entry<Integer, Player>> s = startingDiceValues.entrySet();
        Iterator<Map.Entry<Integer, Player>> i = s.iterator();
        StringBuilder startingOrderToastText = new StringBuilder();
        int j = 1;
        while (i.hasNext()) {
            Map.Entry<Integer, Player> m = i.next();

            Player p = m.getValue();
            p.setStartingOrder(j);
            startingOrderToastText.append(j).append(": ").append((m.getValue()).getName());
            j++;
        }

        Toast.makeText(diceFragment.getActivity().getApplicationContext(), startingOrderToastText.toString(), Toast.LENGTH_LONG).show();
    }


    //Getter und Setter
    public Player getPlayerGreen() {
        return playerGreen;
    }

    public Player getPlayerBlue() {
        return playerBlue;
    }

    public Player getPlayerRed() {
        return playerRed;
    }

    public Player getPlayerYellow() {
        return playerYellow;
    }

    public ArrayList<Player> getActivePlayers() {
        ArrayList<Player> activePlayers = new ArrayList<>();
        if (playerYellow != null) {
            activePlayers.add(playerYellow);
        }
        if (playerGreen != null) {
            activePlayers.add(playerGreen);
        }
        if (playerBlue != null) {
            activePlayers.add(playerBlue);
        }
        if (playerRed != null) {
            activePlayers.add(playerRed);
        }

        return activePlayers;
    }

}


