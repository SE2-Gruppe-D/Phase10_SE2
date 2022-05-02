package com.example.phase10_se2;

import static android.os.SystemClock.sleep;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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

    CardUIManager cardUIManager;
    CardDrawer cardDrawer;
    ArrayList<Cards> cardlist;
    ArrayList<Cards> discardpileList;//Ablagestapel

    ArrayList<ImageView> Imagelist;
    ArrayList<Cards> drawpileList;      //Ziehstapel
    TextView leererAblagestapel;

    Button btnHideAktionskarte;
    Button btnShowAktionskarte;
    ImageView ivShowAktionskarte;
    TextView tvAktuellePhase;

    ImageView ivPlayerBlue;
    ImageView ivPlayerYellow;
    ImageView ivPlayerGreen;
    ImageView ivPlayerRed;

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

    //Timer
    Timer classTimer;
    private static final long startTimer = 120000;  //Timer wird in milli Skunden gestartet
    private CountDownTimer timerturn;
    private long leftTime= startTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfield);
        builder = new AlertDialog.Builder(Playfield.this);

        String currentRoom = getIntent().getExtras().getString("CurrentRoom");
        String userColor = getIntent().getExtras().getString("Color");
        Toast.makeText(this, "YOU ARE THE " + userColor + " PLAYER!", Toast.LENGTH_SHORT).show();
        FirebaseFirestore database;
        database = FirebaseFirestore.getInstance();    //verknuepfung
        database.collection("users")
                .whereEqualTo("Room", currentRoom)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                initializePlayer(documentSnapshot, userColor, currentRoom);
                            }
                            CreatePlayfield();
                        }
                    }
                });
    }

    private void CreatePlayfield() {
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


        btnHideAktionskarte = findViewById(R.id.btnHideAk);
        btnShowAktionskarte = findViewById(R.id.btnShowAk);
        ivShowAktionskarte = findViewById(R.id.ivShowAk);
        tvAktuellePhase = findViewById(R.id.tvAP);


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

        discardpileList = new ArrayList<>();
        cardlist = new ArrayList<>();

        deckcard = findViewById(R.id.deckblatt);
        defaultcard = findViewById(R.id.defaultcard);
        leererAblagestapel = findViewById(R.id.leererStapel);
        layoutPlayer1 = findViewById(R.id.player1);
        layoutPlayer2 = findViewById(R.id.player2);
        layoutPlayer3 = findViewById(R.id.player3);
        layoutPlayer4 = findViewById(R.id.player4);

        cardUIManager = new CardUIManager();
        cardDrawer = new CardDrawer();

        cardlist.addAll(cardDrawer.getInitialCardsList());
        //dynamisches erstellen der Karten ImageViews
        for (int i = 0; i < 96; i++) {
            cardlist.get(i).setCardUI(createCardUI(cardlist.get(i)));
        }

        //Karten werden gemischt
        cardDrawer.shuffleCards(cardlist);

        //Ziehstapel leer?
        cardDrawer.isInitialCardsEmpty();

        //Handkarten werden ausgeteilt
        for (int i = 0; i < 10; i++) {
            if (playerBlue != null) {
                updateHand(playerBlue.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0);
            }
            if (playerRed != null) {
                updateHand(playerRed.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0);
            }
            if (playerYellow != null) {
                updateHand(playerYellow.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90);
            }
            if (playerGreen != null){
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

        //Timer
        TextView timer = findViewById(R.id.Timer);
        classTimer= new Timer(timer, timerturn, leftTime);
        classTimer.startTimer();
        classTimer.updateCountDownText();

        //light sensor to accuse of cheating
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightListener = new SensorEventListener() {
            @Override
            public synchronized void onSensorChanged(SensorEvent sensorEvent) {
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
                dialog.dismiss();
            }
        })
           .setNegativeButton("NO", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   Toast.makeText(Playfield.this, "No one got accused!", Toast.LENGTH_SHORT).show();
                   dialogInterface.dismiss();

               }
           });
    }

    private void initializePlayer(DocumentSnapshot documentSnapshot, String userColor, String currentRoom) {
        if (Objects.equals(documentSnapshot.getString("Color"), userColor)) {
            switch (userColor) {
                case "RED":
                    playerRed = new Player(documentSnapshot.getString("Name"), PlayerColor.RED, currentRoom,1,0,playerRed.getPlayerHand(),playerRed.getCardField());
                    primaryPlayer = playerRed;
                    break;
                case "BLUE":
                    playerBlue = new Player(documentSnapshot.getString("Name"), PlayerColor.BLUE, currentRoom,1,0,playerBlue.getPlayerHand(),playerBlue.getCardField());
                    primaryPlayer = playerBlue;
                    break;
                case "YELLOW":
                    playerYellow = new Player(documentSnapshot.getString("Name"), PlayerColor.YELLOW, currentRoom,1,0,playerYellow.getPlayerHand(),playerYellow.getCardField());
                    primaryPlayer = playerYellow;
                    break;
                case "GREEN":
                    playerGreen = new Player(documentSnapshot.getString("Name"), PlayerColor.GREEN, currentRoom,1,0,playerYellow.getPlayerHand(),playerYellow.getCardField());
                    primaryPlayer = playerGreen;
                    break;
                default:
                    break;

            }
            Log.i("-------------------------------------------", "Color: " + primaryPlayer.getColor());

        }

        if (Objects.equals(documentSnapshot.getString("Color"), "RED")) {
            playerRed = new Player(documentSnapshot.getString("Name"), PlayerColor.RED, currentRoom,1,0,playerRed.getPlayerHand(),playerRed.getCardField());
            playerRed.setPlayerview(findViewById(R.id.ivPR));
            playerRed.getPlayerview().setVisibility(View.VISIBLE);
        }
        if (Objects.equals(documentSnapshot.getString("Color"), "BLUE")) {
            playerBlue = new Player(documentSnapshot.getString("Name"), PlayerColor.BLUE, currentRoom,1,0,playerBlue.getPlayerHand(),playerBlue.getCardField());
            playerBlue.setPlayerview(findViewById(R.id.ivPB));
            playerBlue.getPlayerview().setVisibility(View.VISIBLE);
        }
        if (Objects.equals(documentSnapshot.getString("Color"), "YELLOW")) {
            playerYellow = new Player(documentSnapshot.getString("Name"), PlayerColor.YELLOW, currentRoom,1,0,playerYellow.getPlayerHand(),playerYellow.getCardField());
            playerYellow.setPlayerview(findViewById(R.id.ivPY));
            playerYellow.getPlayerview().setVisibility(View.VISIBLE);
        }
        if (Objects.equals(documentSnapshot.getString("Color"), "GREEN")) {
            playerGreen = new Player(documentSnapshot.getString("Name"), PlayerColor.GREEN, currentRoom,1,0,playerYellow.getPlayerHand(),playerYellow.getCardField());
            playerGreen.setPlayerview(findViewById(R.id.ivPG));
            playerGreen.getPlayerview().setVisibility(View.VISIBLE);
        }

    }
    //Karten werden den Spieler angepasst/ Handkarten-Layout
    public void updateHand(List list, Cards cards, LinearLayout linearLayout, int grad) {
        list.add(cards);
        linearLayout.addView(cards.getCardUI());
        cards.getCardUI().setVisibility(View.VISIBLE);      //Aktueller Spieler sichtbar
        cardlist.remove(0);
        cards.getCardUI().setRotation(grad);
    }

    //Momentan kann nur der player1 eine Karte ziehen
    protected void addCardsDiscardpile() {
        if (discardpileList.size() != 0) {
            updateHand(playerBlue.getPlayerHand(), discardpileList.get(0), layoutPlayer1, 0);
            discardpileList.remove(0);
        } else {
            leererAblagestapel.setVisibility(View.VISIBLE);
        }
    }

    protected void addRandomCardsDiscardpile() {
        if (discardpileList.size() != 0) {
            Random rand = new Random();
            Cards randomCard = discardpileList.get(rand.nextInt(discardpileList.size()));
            updateHand(playerBlue.getPlayerHand(), randomCard, layoutPlayer1, 0);
            discardpileList.remove(randomCard);
        } else {
            leererAblagestapel.setVisibility(View.VISIBLE);
        }
    }

    //Karte ziehen
    protected void addCard() {
        if (playerBlue != null) {
            updateHand(playerBlue.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0);

        } else if (playerRed != null) {
            updateHand(playerRed.getPlayerHand(), cardlist.get(0), layoutPlayer2, 0);

        } else if (playerYellow != null) {
            updateHand(playerYellow.getPlayerHand(), cardlist.get(0), layoutPlayer3, 90);

        } else if (playerGreen != null){
            updateHand(playerGreen.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90);
        }
    }

    private ImageView createCardUI(Cards cards){
        ImageView imageView= new ImageView(getApplicationContext());
        cardUIManager.setCardImage(cards, imageView);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(35, 120, 1);
        imageView.setLayoutParams(params);
        imageView.setTag("c"+ cards.getID());
        imageView.setVisibility(View.INVISIBLE);
        imageView.setClickable(true);
        return imageView;
    }

    //Aktuelle in Player zugewiesene Phase wird in Textview am Spielfeld angezeigt
    public void setPhasenTextTextView() {
        tvAktuellePhase.setText(primaryPlayer.getPhaseText());
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


