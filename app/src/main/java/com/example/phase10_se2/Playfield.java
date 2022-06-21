package com.example.phase10_se2;

import static android.content.ContentValues.TAG;

import android.content.ClipData;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.phase10_se2.enums.PlayerColor;
import com.example.phase10_se2.helper.Timer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Playfield extends AppCompatActivity {
    private static final long START_TIMER = 120000;  //Timer wird in milli Skunden gestartet
    private final long leftTime = START_TIMER;
    DiceFragment diceFragment;
    String currentRoom = "";
    ImageView deckcard;
    ImageView defaultcard;
    LinearLayout layoutPlayer1;
    LinearLayout layoutPlayer2;
    LinearLayout layoutPlayer3;
    LinearLayout layoutPlayer4;
    LinearLayout layoutPlayer1CardField;
    LinearLayout layoutPlayer2CardField;
    LinearLayout layoutPlayer3CardField;
    LinearLayout layoutPlayer4CardField;
    CardUIManager cardUIManager;
    CardDrawer cardDrawer;
    CardsPrimaryPlayer cardsPrimaryPlayer;
    ArrayList<Cards> cardlist;
    ArrayList<Cards> allCards = new ArrayList<>();
    ArrayList<Cards> discardpileList;//Ablagestapel
    LinearLayout[] layoutPlayers;

    TextView leererAblagestapel;
    Button exitGame;        //Spiel verlassen Button
    Button btnHideAktionskarte;
    Button btnShowAktionskarte;
    Button btnCheat;
    ImageView ivShowAktionskarte;
    TextView tvAktuellePhase;
    Button btnCheckPhase;
    String userColor;
    Player playerGreen;
    Player playerRed;
    Player playerYellow;
    Player playerBlue;
    Player primaryPlayer;
    Player currentPlayer;
    HandCards handCards;
    ArrayList<Cards> playerHandBlue;
    ArrayList<Cards> playerHandRed;
    ArrayList<Cards> playerHandYellow;
    ArrayList<Cards> playerHandGreen;
    List<Cards> playerHandPrimaryPlayer;
    ArrayList<Player> playerArrayList;
    WinnerDecision wd;
    Player player;
    Actionfield actionfield;
    private boolean initToastShown = false;
    //Round and phase
    Phase phase;
    int round = 1;
    ArrayList startOrder = new ArrayList();
    int currentDiceRoll = 0;
    boolean cheated = false;
    boolean newDBCollectionNeeded = true;
    boolean putCard = false;
    //light sensor
    SensorManager sm;
    SensorEventListener lightListener;
    Sensor light;
    AlertDialog.Builder builder;
    float floatThreshold = 1;
    //Timer
    Timer classTimer;
    FirebaseFirestore database;
    ArrayList<String> playerList = new ArrayList();
    private long doubleClickLastTime = 0L;
    private CountDownTimer timerturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfield);
        builder = new AlertDialog.Builder(Playfield.this);

        currentRoom = getIntent().getExtras().getString("CurrentRoom");
        userColor = getIntent().getExtras().getString("Color");
        database = FirebaseFirestore.getInstance();    //verknuepfung
        FirebaseFirestore.setLoggingEnabled(true);

        database.collection("users")
                .whereEqualTo("Room", currentRoom)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            initializePlayer(documentSnapshot, userColor, currentRoom);
                            playerList.add(documentSnapshot.getString("Color"));
                        }
                        createPlayfield();
                    }
                });

        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .addSnapshotListener((value, error) -> {

                    if (error != null) {
                        Log.w(TAG, "Listen failed.", error);
                        return;
                    }

                    if (value != null) {
                        database.collection("gameInfo")
                                .whereEqualTo("RoomName", currentRoom)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                //show current player on init
                                                if (!initToastShown) {
                                                    currentPlayerToast(currentPlayer.getColorAsString());
                                                    initToastShown = true;
                                                }

                                                //current player
                                                ArrayList currentPlayerArray = (ArrayList) document.get("CurrentPlayer");
                                                if (currentPlayer != null && !currentPlayer.getColorAsString().equals(currentPlayerArray.get(1))) {
                                                    getPlayerFromDB(String.valueOf(currentPlayerArray.get(1)));

                                                    //current player toast
                                                    currentPlayerToast(currentPlayerArray.get(1).toString());


                                                    if (currentPlayerArray.get(1).equals("RED")) {
                                                        currentPlayer = playerRed;
                                                    }
                                                    if (currentPlayerArray.get(1).equals("BLUE")) {
                                                        currentPlayer = playerBlue;
                                                    }
                                                    if (currentPlayerArray.get(1).equals("YELLOW")) {
                                                        currentPlayer = playerYellow;
                                                    }
                                                    if (currentPlayerArray.get(1).equals("GREEN")) {
                                                        currentPlayer = playerGreen;
                                                    }
                                                    classTimer.restart();
                                                }

                                                //count active players
                                                ArrayList playerRedArr = (ArrayList) document.get("PlayerRed");
                                                ArrayList playerYellowArr = (ArrayList) document.get("PlayerYellow");
                                                ArrayList playerBlueArr = (ArrayList) document.get("PlayerBlue");
                                                ArrayList playerGreenArr = (ArrayList) document.get("PlayerGreen");

                                                int playercount = 4;
                                                if (playerRedArr == null) {
                                                    playercount--;
                                                    if (playerRed != null) {
                                                        playerRed.getPlayerview().setVisibility(View.INVISIBLE);
                                                        playerRed.getLinearLayout().setVisibility(View.INVISIBLE);
                                                    }
                                                }
                                                if (playerYellowArr == null) {
                                                    playercount--;
                                                    if (playerYellow != null) {
                                                        playerYellow.getPlayerview().setVisibility(View.INVISIBLE);
                                                        playerYellow.getLinearLayout().setVisibility(View.INVISIBLE);
                                                    }
                                                }
                                                if (playerBlueArr == null) {
                                                    playercount--;
                                                    if (playerBlue != null) {
                                                        playerBlue.getPlayerview().setVisibility(View.INVISIBLE);
                                                        playerBlue.getLinearLayout().setVisibility(View.INVISIBLE);
                                                    }
                                                }
                                                if (playerGreenArr == null) {
                                                    playercount--;
                                                    if (playerGreen != null) {
                                                        playerGreen.getPlayerview().setVisibility(View.INVISIBLE);
                                                        playerGreen.getLinearLayout().setVisibility(View.INVISIBLE);
                                                    }
                                                }

                                                if (playercount <= 1) {
                                                    goToMainMenu();
                                                }

                                                //sync discardpile
                                                String discardpileListString = String.valueOf(document.get("DiscardpileList"));
                                                if (!discardpileListString.isEmpty()) {
                                                    discardpileList = addCardsToList(discardpileListString);
                                                    discardpileList.get(discardpileList.size() - 1).getCardUI().setVisibility(View.VISIBLE);
                                                    defaultcard.setImageDrawable(discardpileList.get(discardpileList.size() - 1).getCardUI().getDrawable());
                                                }


                                                //sync handcards and cardfield
                                                if (playerBlueArr != null) {
                                                    ArrayList<Cards> newHandCards = addCardsToList(String.valueOf(playerBlueArr.get(5)));
                                                    ArrayList<Cards> newCardField = addCardsToList(String.valueOf(playerBlueArr.get(6)));

                                                    if (Integer.parseInt(String.valueOf(playerBlueArr.get(3))) > 10) {
                                                        Toast.makeText(Playfield.this, "Player " + wd.getWinner().get(0).getColorAsString() + " won!", Toast.LENGTH_LONG).show();
                                                    }

                                                    if (playerBlue.getColor().equals(primaryPlayer.getColor())) {
                                                        handCards.updateHandCompletely(playerBlue.getPlayerHand(), newHandCards, layoutPlayer1);
                                                        resetCardListeners(newHandCards);
                                                        playerBlue.updateCardfieldCompletely(newCardField, layoutPlayer1CardField);
                                                    } else if (playerBlue.abgelegt){
                                                        playerBlue.updateCardfieldCompletely(newCardField, playerBlue.getLinearLayout());
                                                    }

                                                    //make player cardfield cards visible
                                                    for (Cards card : playerBlue.getCardField()) {
                                                        if (card != null && card.getCardUI() != null) {
                                                            card.getCardUI().setClickable(false);
                                                            card.getCardUI().setVisibility(View.VISIBLE);
                                                        }
                                                    }

                                                    playerBlue.setPlayerHand(newHandCards);
                                                }
                                                if (playerRedArr != null) {
                                                    ArrayList<Cards> newHandCards = addCardsToList(String.valueOf(playerRedArr.get(5)));
                                                    ArrayList<Cards> newCardField = addCardsToList(String.valueOf(playerRedArr.get(6)));

                                                    if (Integer.parseInt(String.valueOf(playerRedArr.get(3))) > 10) {
                                                        Toast.makeText(Playfield.this, "Player " + wd.getWinner().get(0).getColorAsString() + " won!", Toast.LENGTH_LONG).show();
                                                    }

                                                    if (playerRed.getColor().equals(primaryPlayer.getColor())) {
                                                        handCards.updateHandCompletely(playerRed.getPlayerHand(), newHandCards, layoutPlayer1);
                                                        resetCardListeners(newHandCards);
                                                        playerRed.updateCardfieldCompletely(newCardField, layoutPlayer1CardField);
                                                    } else if (playerRed.abgelegt){
                                                        playerRed.updateCardfieldCompletely(newCardField, playerRed.getLinearLayout());
                                                    }

                                                    //make player cardfield cards visible
                                                    for (Cards card : playerRed.getCardField()) {
                                                        if (card.getCardUI() != null) {
                                                            card.getCardUI().setClickable(false);
                                                            card.getCardUI().setVisibility(View.VISIBLE);
                                                        }
                                                    }

                                                    playerRed.setPlayerHand(newHandCards);
                                                }
                                                if (playerYellowArr != null) {
                                                    ArrayList<Cards> newHandCards = addCardsToList(String.valueOf(playerYellowArr.get(5)));
                                                    ArrayList<Cards> newCardField = addCardsToList(String.valueOf(playerYellowArr.get(6)));

                                                    if (Integer.parseInt(String.valueOf(playerYellowArr.get(3))) > 10) {
                                                        Toast.makeText(Playfield.this, "Player " + wd.getWinner().get(0).getColorAsString() + " won!", Toast.LENGTH_LONG).show();
                                                    }

                                                    if (playerYellow.getColor().equals(primaryPlayer.getColor())) {
                                                        handCards.updateHandCompletely(playerYellow.getPlayerHand(), newHandCards, layoutPlayer1);
                                                        resetCardListeners(newHandCards);
                                                        playerYellow.updateCardfieldCompletely(newCardField, layoutPlayer1CardField);
                                                    } else if (playerYellow.abgelegt){
                                                        playerYellow.updateCardfieldCompletely(newCardField, playerYellow.getLinearLayout());
                                                    }

                                                    //make player cardfield cards visible
                                                    for (Cards card : playerYellow.getCardField()) {
                                                        if (card != null && card.getCardUI() != null) {
                                                            card.getCardUI().setClickable(false);
                                                            card.getCardUI().setVisibility(View.VISIBLE);
                                                        }
                                                    }

                                                    playerYellow.setPlayerHand(newHandCards);
                                                }
                                                if (playerGreenArr != null) {
                                                    ArrayList<Cards> newHandCards = addCardsToList(String.valueOf(playerGreenArr.get(5)));
                                                    ArrayList<Cards> newCardField = addCardsToList(String.valueOf(playerGreenArr.get(6)));

                                                    if (Integer.parseInt(String.valueOf(playerGreenArr.get(3))) > 10) {
                                                        Toast.makeText(Playfield.this, "Player " + wd.getWinner().get(0).getColorAsString() + " won!", Toast.LENGTH_LONG).show();
                                                    }

                                                    if (playerGreen.getColor().equals(primaryPlayer.getColor())) {
                                                        handCards.updateHandCompletely(playerGreen.getPlayerHand(), newHandCards, layoutPlayer1);
                                                        resetCardListeners(newHandCards);
                                                        playerGreen.updateCardfieldCompletely(newCardField, layoutPlayer1CardField);
                                                    } else if (playerGreen.abgelegt){
                                                        playerGreen.updateCardfieldCompletely(newCardField, playerGreen.getLinearLayout());
                                                    }

                                                    //make player cardfield cards visible
                                                    for (Cards card : playerGreen.getCardField()) {
                                                        if (card.getCardUI() != null) {
                                                            card.getCardUI().setClickable(false);
                                                            card.getCardUI().setVisibility(View.VISIBLE);
                                                        }
                                                    }

                                                    playerGreen.setPlayerHand(newHandCards);
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
                });
        //EventListener if anything is changed in DB "gameInfo"
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .addSnapshotListener((value, error) -> {

                    if (error != null) {
                        Log.w(TAG, "Listen failed.", error);
                        return;
                    }

                    if (value != null) {
                        database.collection("gameInfo")
                                .whereEqualTo("RoomName", currentRoom)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {

                                                if (playerGreen != null) {
                                                    getPlayerFromDB("GREEN");
                                                }
                                                if (playerRed != null) {
                                                    getPlayerFromDB("RED");
                                                }
                                                if (playerBlue != null) {
                                                    getPlayerFromDB("BLUE");
                                                }
                                                if (playerYellow != null) {
                                                    getPlayerFromDB("YELLOW");
                                                }

                                            }

                                        }
                                    }
                                });
                    }
                });
    }

    private void resetCardListeners(ArrayList<Cards> newHandCards) {
        for (Cards card : newHandCards) {
            if (card != null && card.getCardUI() != null) {
                card.getCardUI().setClickable(true);
                card.getCardUI().setOnClickListener(listener);
                card.getCardUI().setOnTouchListener(new ChoiceTouchListener());
            }
        }
    }

    private void goToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
    }

    private void createPlayfield() {
        //ermitteln von current Player
        if (playerBlue != null && playerList.get(0).equals("BLUE")) {
            currentPlayer = playerBlue;
        }
        if (playerRed != null && playerList.get(0).equals("RED")) {
            currentPlayer = playerRed;
        }
        if (playerYellow != null && playerList.get(0).equals("YELLOW")) {
            currentPlayer = playerYellow;
        }
        if (playerGreen != null && playerList.get(0).equals("GREEN")) {
            currentPlayer = playerGreen;
        }

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
        btnCheckPhase = findViewById(R.id.buttonCheckPhase);
        btnCheat = findViewById(R.id.buttonCheat);

        //Aktionskarte einblenden Show und Hide button tauschen
        btnShowAktionskarte.setOnClickListener(view -> {
            ivShowAktionskarte.setVisibility(View.VISIBLE);
            btnCheat.setVisibility(View.INVISIBLE);
            btnHideAktionskarte.setVisibility(View.VISIBLE);
            btnShowAktionskarte.setVisibility(View.INVISIBLE);
        });
        //Aktionskarte ausblenden Hide und Show button austauschen
        btnHideAktionskarte.setOnClickListener(view -> {
            ivShowAktionskarte.setVisibility(View.INVISIBLE);
            btnCheat.setVisibility(View.VISIBLE);
            btnHideAktionskarte.setVisibility(View.INVISIBLE);
            btnShowAktionskarte.setVisibility(View.VISIBLE);
        });
        //cheatbutton listener
        btnCheat.setOnClickListener(view -> {
            if (currentPlayer.getColor().equals(primaryPlayer.getColor())) {
               updateCheated(true);
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
        layoutPlayer1CardField = findViewById(R.id.player1PhaseAblegen);
        layoutPlayer2CardField = findViewById(R.id.player2PhaseAblegen);
        layoutPlayer2CardField.setOnDragListener(new ChoiceDragListener2());
        layoutPlayer3CardField = findViewById(R.id.player3PhaseAblegen);
        layoutPlayer3CardField.setOnDragListener(new ChoiceDragListener3());
        layoutPlayer4CardField = findViewById(R.id.player4PhaseAblegen);
        layoutPlayer4CardField.setOnDragListener(new ChoiceDragListener4());

        //Button, um zu überprüfen, ob die Phase richtig ist
        phase = new Phase();
        actionfield = new Actionfield();
        btnCheckPhase.setVisibility(View.VISIBLE);

        btnCheckPhase.setOnClickListener(view -> {
            if(!currentPlayer.isAbgelegt()) {
                if (phase.getRightPhase(getPhasenumberDB(), getCardfieldCardlistDB())) {
                    if (getPhasenumberDB() != 10) {
                        setPhasenumberDB(); //Phase wird um 1 erhöht und abgelegt wird auf true gesetzt
                        for (int i = 0; i < getCardfieldCardlistDB().size(); i++) {
                            getCardfieldCardlistDB().get(i).getCardUI().setClickable(false);
                            getCardfieldCardlistDB().get(i).getCardUI().setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    playerHandPrimaryPlayer = getHandCardsDB();
                    while (layoutPlayer1CardField.getChildCount() != 0) {
                        View v = layoutPlayer1CardField.getChildAt(0);
                        ViewGroup owner = (ViewGroup) v.getParent();
                        owner.removeView(v);
                        layoutPlayer1.addView(v);
                        v.setVisibility(View.VISIBLE);
                        v.setClickable(true);
                        for (int i = 0; i < getCardfieldCardlistDB().size(); i++) {
                            Cards card = getCardfieldCardlistDB().get(i);
                            if (card != null && card.getCardUI() != null && v.equals(card.getCardUI())) {
                                playerHandPrimaryPlayer.add(getCardfieldCardlistDB().get(i));
                                getCardfieldCardlistDB().remove(getCardfieldCardlistDB().get(i));
                            }
                        }
                    }
                }
            }
        });

        cardUIManager = new CardUIManager();
        cardDrawer = new CardDrawer();
        cardsPrimaryPlayer = new CardsPrimaryPlayer();
        handCards = new HandCards();

        cardlist.addAll(cardDrawer.getInitialCardsList());
        //dynamisches erstellen der Karten ImageViews
        setUI(cardlist);

        for (Cards card : cardlist) {
            card.getCardUI().setOnClickListener(listener);
            card.getCardUI().setOnTouchListener(new ChoiceTouchListener());
        }

        allCards.addAll(cardlist);  //copy card list
        allCards.sort(Comparator.comparing(Cards::getID));

        //Karten werden gemischt
        cardDrawer.shuffleCards(cardlist);

        //Ziehstapel leer?
        cardDrawer.isInitialCardsEmpty();

        //Handkarten werden ausgeteilt
        layoutPlayers = new LinearLayout[] {layoutPlayer1, layoutPlayer2, layoutPlayer3, layoutPlayer4};
        handCards.handCardsPlayer(layoutPlayers, cardlist, playerBlue, playerGreen, playerYellow, playerRed, primaryPlayer);

        //Auslegefelder werden zugeteilt
        currentPlayer.getCardsLayOut(layoutPlayer1CardField, layoutPlayer2CardField, layoutPlayer3CardField, layoutPlayer4CardField, playerBlue, playerGreen, playerYellow, playerRed, primaryPlayer);

        //Player Blue, Red, Yellow, Green
        deckcard.setOnClickListener(view ->
                addCard()
        );

        //random Defaultcard
        SecureRandom rand = new SecureRandom();
        Cards randomCard = cardlist.get(rand.nextInt(cardlist.size()));
        cardlist.remove(randomCard);
        discardpileList.add(randomCard);
        defaultcard.setImageDrawable(createCardUI(discardpileList.get(0)).imageView.getDrawable());
        defaultcard.setOnDragListener(new ChoiceDragListener1());

        if (currentPlayer.getColor().equals(primaryPlayer.getColor())) {
            gameInfoDB();
        }

        defaultcard.setOnClickListener(view ->
                    addCardsDiscardpile()
        );

        //Timer
        TextView timer = findViewById(R.id.Timer);
        classTimer = new Timer(timer, timerturn, leftTime, this);
        classTimer.startTimer();

        //light sensor to accuse of cheating
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightListener = new SensorEventListener() {
            @Override
            public synchronized void onSensorChanged(SensorEvent sensorEvent) {
                float floatSensorValue = sensorEvent.values[0];
                if (floatSensorValue < floatThreshold) {
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
                .setMessage("Are you sure, you want to accuse the current player of cheating?")
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, which) -> {
                    //check if cheating == true
                    database.collection("gameInfo").whereEqualTo("RoomName", currentRoom)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String lastCurrentPlayerColor = playerList.get((playerList.indexOf(currentPlayer.getColorAsString())));

                                        if (document.getBoolean("Cheated")) {
                                            //give consequences
                                            //if accused right:
                                            String playerDB = "Player" + lastCurrentPlayerColor.charAt(0) + lastCurrentPlayerColor.substring(1).toLowerCase();
                                            ArrayList player = (ArrayList) document.get(playerDB);


                                            int phaseplayer = Math.max((Integer.parseInt(String.valueOf(player.get(3))) - 1), 1);
                                            player.set(3, phaseplayer);
                                            document.getReference().update(playerDB, player);
                                            Toast.makeText(Playfield.this, "Player " + lastCurrentPlayerColor + " cheated, you were right!", Toast.LENGTH_SHORT).show();

                                        } else {
                                            //if accused wrong:
                                            ArrayList player;
                                            if (document.get("PlayerBlue") != null) {
                                                if (primaryPlayer.getColor().equals(PlayerColor.BLUE)) {
                                                    player = (ArrayList) document.get("PlayerBlue");
                                                    int minusPoints = Integer.parseInt(String.valueOf(player.get(4))) + 10;
                                                    player.set(4, minusPoints);
                                                    document.getReference().update("PlayerBlue", player);
                                                }
                                            } else if (document.get("PlayerRed") != null) {
                                                if (primaryPlayer.getColor().equals(PlayerColor.RED)) {
                                                    player = (ArrayList) document.get("PlayerRed");
                                                    int minusPoints = Integer.parseInt(String.valueOf(player.get(4))) + 10;
                                                    player.set(4, minusPoints);
                                                    document.getReference().update("PlayerRed", player);
                                                }
                                            } else if (document.get("PlayerYellow") != null) {
                                                if (primaryPlayer.getColor().equals(PlayerColor.YELLOW)) {
                                                    player = (ArrayList) document.get("PlayerYellow");
                                                    int minusPoints = Integer.parseInt(String.valueOf(player.get(4))) + 10;
                                                    player.set(4, minusPoints);
                                                    document.getReference().update("PlayerYellow", player);

                                                }
                                            } else if (document.get("PlayerGreen") != null) {
                                                if (primaryPlayer.getColor().equals(PlayerColor.GREEN)) {
                                                    player = (ArrayList) document.get("PlayerGreen");
                                                    int minusPoints = Integer.parseInt(String.valueOf(player.get(4))) + 10;
                                                    player.set(4, minusPoints);
                                                    document.getReference().update("PlayerGreen", player);

                                                }
                                            }

                                            Toast.makeText(Playfield.this, "Player " + lastCurrentPlayerColor + " did not cheat, you were wrong!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                }
                            });

                })
                .setNegativeButton("NO", (dialogInterface, i) -> {
                    Toast.makeText(Playfield.this, "No one got accused!", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();

                });

        //aktualisiert den Text für die Phase
        setPhasenTextTextView();

        //entfernt die label Leiste (Actionbar) auf dem Playfield
        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.hide();
        } catch (Exception ignored) {
        }

        ArrayList<Player> playerArrayList = new ArrayList<>();
        if (playerBlue != null) {
            playerArrayList.add(playerBlue);
        }
        if (playerYellow != null) {
            playerArrayList.add(playerYellow);
        }
        if (playerRed != null) {
            playerArrayList.add(playerRed);
        }
        if (playerGreen != null) {
            playerArrayList.add(playerGreen);
        }


        wd = new WinnerDecision(playerArrayList);

        //Spiel verlassen
        exitGame = findViewById(R.id.leaveGame);
        exitGame.setOnClickListener(view -> leaveGame());
    }

    public void setNextCurrentPlayer() {
        String color = playerList.get((playerList.indexOf(currentPlayer.getColorAsString()) + 1) % playerList.size());
        Player player = getPlayerFromColor(color);
        updateCheated(false);
        updatePlayers();
        setCurrentPlayerInDB(player);
        putCard = false;
    }

    private Player getPlayerFromColor(String color) {
        if (color.equals("GREEN")) {
            return playerGreen;
        }
        if (color.equals("RED")) {
            return playerRed;
        }
        if (color.equals("BLUE")) {
            return playerBlue;
        }
        if (color.equals("YELLOW")) {
            return playerYellow;
        }

        return null;
    }

    private void leaveGame() {
        deletePlayerDB(primaryPlayer);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void deletePlayerDB(Player primaryPlayer) {
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> delete = new HashMap<>();

                            if (playerBlue != null && primaryPlayer.getColor().equals(playerBlue.getColor())) {
                                delete.put("PlayerBlue", FieldValue.delete());
                            }

                            if (playerRed != null && primaryPlayer.getColor().equals(playerRed.getColor())) {
                                delete.put("PlayerRed", FieldValue.delete());
                            }

                            if (playerYellow != null && primaryPlayer.getColor().equals(playerYellow.getColor())) {
                                delete.put("PlayerYellow", FieldValue.delete());
                            }

                            if (playerGreen != null && primaryPlayer.getColor().equals(playerGreen.getColor())) {
                                delete.put("PlayerGreen", FieldValue.delete());
                            }

                            if (!delete.isEmpty()) {
                                document.getReference().update(delete);
                            }
                        }
                    }
                });
    }

    private void initializePlayer(DocumentSnapshot documentSnapshot, String userColor, String currentRoom) {
        if (Objects.equals(documentSnapshot.getString("Color"), userColor)) {
            switch (userColor) {
                case "RED":
                    playerRed = new Player(documentSnapshot.getString("Name"), PlayerColor.RED, currentRoom, 1, 0, playerHandRed, new ArrayList<>());
                    primaryPlayer = playerRed;
                    break;
                case "BLUE":
                    playerBlue = new Player(documentSnapshot.getString("Name"), PlayerColor.BLUE, currentRoom, 1, 0, playerHandBlue, new ArrayList<>());
                    primaryPlayer = playerBlue;
                    break;
                case "YELLOW":
                    playerYellow = new Player(documentSnapshot.getString("Name"), PlayerColor.YELLOW, currentRoom, 1, 0, playerHandYellow, new ArrayList<>());
                    primaryPlayer = playerYellow;
                    break;
                case "GREEN":
                    playerGreen = new Player(documentSnapshot.getString("Name"), PlayerColor.GREEN, currentRoom, 1, 0, playerHandGreen, new ArrayList<>());
                    primaryPlayer = playerGreen;
                    break;
                default:
                    break;

            }
        }

        if (Objects.equals(documentSnapshot.getString("Color"), "RED")) {
            playerRed = new Player(documentSnapshot.getString("Name"), PlayerColor.RED, currentRoom, 1, 0, playerHandRed, new ArrayList<>());
            playerRed.setPlayerview(findViewById(R.id.ivPR));
            playerRed.getPlayerview().setVisibility(View.VISIBLE);
        }
        if (Objects.equals(documentSnapshot.getString("Color"), "BLUE")) {
            playerBlue = new Player(documentSnapshot.getString("Name"), PlayerColor.BLUE, currentRoom, 1, 0, playerHandBlue, new ArrayList<>());
            playerBlue.setPlayerview(findViewById(R.id.ivPB));
            playerBlue.getPlayerview().setVisibility(View.VISIBLE);
        }
        if (Objects.equals(documentSnapshot.getString("Color"), "YELLOW")) {
            playerYellow = new Player(documentSnapshot.getString("Name"), PlayerColor.YELLOW, currentRoom, 1, 0, playerHandYellow, new ArrayList<>());
            playerYellow.setPlayerview(findViewById(R.id.ivPY));
            playerYellow.getPlayerview().setVisibility(View.VISIBLE);
        }
        if (Objects.equals(documentSnapshot.getString("Color"), "GREEN")) {
            playerGreen = new Player(documentSnapshot.getString("Name"), PlayerColor.GREEN, currentRoom, 1, 0, playerHandGreen, new ArrayList<>());
            playerGreen.setPlayerview(findViewById(R.id.ivPG));
            playerGreen.getPlayerview().setVisibility(View.VISIBLE);
        }
    }

    private void setUI(ArrayList<Cards> cardlist) {
        for (int i = 0; i < 96; i++) {
            cardlist.get(i).setCardUI(createCardUI(cardlist.get(i)));
        }
    }
    //Eine Karte vom Ablagestapel ziehen
    private void addCardsDiscardpile() {
        int size = discardpileList.size();
        if ((actionfield.cardToPullBoth > 0 && actionfield.cardToPullDiscardpileList < 1 && actionfield.cardToPullCardlist < 1) || (actionfield.cardToPullBoth < 1 && actionfield.cardToPullDiscardpileList > 0 && actionfield.cardToPullCardlist < 1)) {
            if (size != 0) {
                Cards helpcard;
                if(actionfield.randomCard){
                    SecureRandom rand = new SecureRandom();
                    helpcard = discardpileList.get(rand.nextInt(discardpileList.size()));
                    helpcard.getCardUI().setVisibility(View.VISIBLE);
                }else{
                    helpcard =discardpileList.get(size - 1);
                    discardpileList.get(size - 1).getCardUI().setVisibility(View.VISIBLE); //Karte die man zieht wird auf der Hand sichbar
                }
                if (playerYellow != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerYellow.getColor().equals(primaryPlayer.getColor())) {
                    handCards.updateHand(getHandCardsDB(), helpcard, layoutPlayer1, cardlist, true);
                    helpcard.getCardUI().setOnClickListener(listener);
                    helpcard.getCardUI().setOnTouchListener(new ChoiceTouchListener());
                    discardpileList.remove(helpcard);
                }
                if (playerBlue != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerBlue.getColor().equals(primaryPlayer.getColor())) {
                    handCards.updateHand(getHandCardsDB(), helpcard, layoutPlayer1, cardlist, true);
                    helpcard.getCardUI().setOnClickListener(listener);
                    helpcard.getCardUI().setOnTouchListener(new ChoiceTouchListener());
                    discardpileList.remove(helpcard);
                }
                if (playerRed != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerRed.getColor().equals(primaryPlayer.getColor())) {
                    handCards.updateHand(getHandCardsDB(), helpcard, layoutPlayer1, cardlist, true);
                    helpcard.getCardUI().setOnClickListener(listener);
                    helpcard.getCardUI().setOnTouchListener(new ChoiceTouchListener());
                    discardpileList.remove(helpcard);
                }
                if (playerGreen != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerGreen.getColor().equals(primaryPlayer.getColor())) {
                    handCards.updateHand(getHandCardsDB(), helpcard, layoutPlayer1, cardlist, true);
                    helpcard.getCardUI().setOnClickListener(listener);
                    helpcard.getCardUI().setOnTouchListener(new ChoiceTouchListener());
                    discardpileList.remove(helpcard);
                }

                if ((size - 1) != 0) {
                        defaultcard.setImageDrawable(createCardUI(discardpileList.get(size-2)).imageView.getDrawable());
                }else{
                    leererAblagestapel.setVisibility(View.VISIBLE);
                }
                actionfield.cardToPullBoth--;
                actionfield.cardToPullDiscardpileList--;
            } else {
                leererAblagestapel.setVisibility(View.VISIBLE);
            }
            putCard=true;
        }
    }

    public boolean addEnoughCards(){
        return putCard && actionfield.cardToPullBoth < 1 && actionfield.cardToPullDiscardpileList < 1 && actionfield.cardToPullCardlist < 1;
    }

    public void getActionfield() {
        switch (actionfield.getRightFieldColor(getCurrentPositionDB())) {
            case GREY:
                actionfield.greyFieldColor();
                break;
            case GREEN:
                actionfield.greenFieldColor();
                break;
            case ORANGE:
                actionfield.orangeFieldColor();
                break;
            case BLUE:
                actionfield.blueFieldColor();
                break;
            case RED:
                actionfield.redFieldColor();
                break;
            case PURPLE:
                putCard=true;
                actionfield.purpleFieldColor();
                break;
            case PINK:
                actionfield.pinkFieldColor();
                break;
        }
    }

    //Karte ziehen
    private void addCard() {
        //only currentPlayer kann ziehen
        if (((actionfield.cardToPullBoth > 0 && actionfield.cardToPullDiscardpileList < 1 && actionfield.cardToPullCardlist < 1) || (actionfield.cardToPullBoth < 1 && actionfield.cardToPullDiscardpileList < 1 && actionfield.cardToPullCardlist > 0)))  {
            cardlist.get(0).getCardUI().setVisibility(View.VISIBLE);
            cardlist.get(0).getCardUI().setOnClickListener(listener);
            cardlist.get(0).getCardUI().setOnTouchListener(new ChoiceTouchListener());
            if (playerYellow != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerYellow.getColor().equals(primaryPlayer.getColor())) {
                handCards.updateHand(getHandCardsDB(), cardlist.get(0), layoutPlayer1, cardlist, true);
                actionfield.cardToPullBoth--;
                actionfield.cardToPullCardlist--;
            }
            if (playerBlue != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerBlue.getColor().equals(primaryPlayer.getColor())) {
                handCards.updateHand(getHandCardsDB(), cardlist.get(0), layoutPlayer1, cardlist, true);
                actionfield.cardToPullBoth--;
                actionfield.cardToPullCardlist--;
            }
            if (playerRed != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerRed.getColor().equals(primaryPlayer.getColor())) {
                handCards.updateHand(getHandCardsDB(), cardlist.get(0), layoutPlayer1, cardlist, true);
                actionfield.cardToPullBoth--;
                actionfield.cardToPullCardlist--;
            }
            if (playerGreen != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerGreen.getColor().equals(primaryPlayer.getColor())) {
                handCards.updateHand(getHandCardsDB(), cardlist.get(0), layoutPlayer1, cardlist, true);
                actionfield.cardToPullBoth--;
                actionfield.cardToPullCardlist--;
            }
            putCard=true;
        }
    }

    private CardUI createCardUI(Cards cards) {
        ImageView imageView = new ImageView(getApplicationContext());
        cardUIManager.setCardImage(cards, imageView);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(35, 120, 1);
        imageView.setLayoutParams(params);
        imageView.setTag("c" + cards.getID());
        imageView.setVisibility(View.INVISIBLE);
        imageView.setClickable(true);
        imageView.setFocusable(true);
        return new CardUI(imageView);
    }

    //Karten auslegen - 1x Click Karte wird ausgelegt, 2x Click Karte zurück auf die Hand
    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setClickable(true);
            view.setOnTouchListener(new ChoiceTouchListener());
            if (primaryPlayer.getColor().equals(currentPlayer.getColor())) {
                //Karte zurück
                if (System.currentTimeMillis() - doubleClickLastTime < 700) {
                    doubleClickLastTime = 0;
                    View v = view;
                    ViewGroup owner = (ViewGroup) v.getParent();
                    //Handkarte zurück nehmen
                    playerHandPrimaryPlayer = getHandCardsDB();
                    for (int i = 0; i < getCardfieldCardlistDB().size(); i++) {
                        if (getCardfieldCardlistDB().get(i) != null && getCardfieldCardlistDB().get(i).getCardUI() != null && v.equals(getCardfieldCardlistDB().get(i).getCardUI())) {
                            playerHandPrimaryPlayer.add(getCardfieldCardlistDB().get(i));
                            getCardfieldCardlistDB().remove(getCardfieldCardlistDB().get(i));
                            break;
                        }
                    }
                    owner.removeView(v);
                    layoutPlayer1.addView(v);
                    v.setVisibility(View.VISIBLE);
                    v.setOnTouchListener(new ChoiceTouchListener());
                    v.setClickable(true);
                } else {
                    doubleClickLastTime = System.currentTimeMillis();
                    View v = view;
                    ViewGroup owner = (ViewGroup) v.getParent();
                    //Hilfsarray mit den ausgelegten Karten befüllen
                    playerHandPrimaryPlayer = getHandCardsDB();
                    Cards helper = null;
                    if (playerHandPrimaryPlayer.size() != 0) {
                        for (int i = 0; i < playerHandPrimaryPlayer.size(); i++) {
                            if (v.equals(playerHandPrimaryPlayer.get(i).getCardUI())) {
                                helper = playerHandPrimaryPlayer.get(i);
                                break;
                            }
                        }
                        //Wenn Phase nocht nicht beendet wurde Karten auslegen
                        if(!currentPlayer.isAbgelegt()) {
                            getCardfieldCardlistDB().add(helper);
                            playerHandPrimaryPlayer.remove(helper);
                            owner.removeView(v);
                            layoutPlayer1CardField.addView(v);
                            v.setOnTouchListener(null);
                        }
                        //Nach Phasenende bei eigenen Handkarten dazulegen
                        else if(addEnoughCards() && currentPlayer.isAbgelegt() && phase.getRightPhaseOtherPlayer(getPhasenumberDB()-1, helper, getCardfieldCardlistDB())) {
                            getCardfieldCardlistDB().add(helper);
                            playerHandPrimaryPlayer.remove(helper);
                            owner.removeView(v);
                            layoutPlayer1CardField.addView(v);
                            v.setOnTouchListener(null);
                            v.setClickable(false);
                        }
                    }
                }
            }
        }
    };

    //Class allows us to drag view
    private final class ChoiceTouchListener implements View.OnTouchListener {
        boolean isMoved = true;
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (primaryPlayer.getColor().equals(currentPlayer.getColor())) {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_MOVE:
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                        view.startDragAndDrop(data, shadowBuilder, view, 0);
                        view.setVisibility(View.VISIBLE);
                        isMoved = false;
                        break;
                    default:
                        break;
                }
                if (!isMoved) {
                    Log.e("click", view.toString());
                }
            }
            return false;
        }
    }

    //Class to drop
    //ChoiceDragListener für Ablegestapel
    private class ChoiceDragListener1 implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            if (addEnoughCards() && primaryPlayer.getColor().equals(currentPlayer.getColor())) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED: //1
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DRAG_EXITED: //6
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED: //5
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DROP: //Action 3
                        View v = (View) dragEvent.getLocalState();
                        ViewGroup owner = (ViewGroup) v.getParent();
                        //Karte zum Ablegestapel hinzufügen
                        playerHandPrimaryPlayer = getHandCardsDB();
                        if (playerHandPrimaryPlayer.size() != 0) {
                            for (int i = 0; i < playerHandPrimaryPlayer.size(); i++) {
                                if (v.equals(playerHandPrimaryPlayer.get(i).getCardUI())) {
                                    discardpileList.add(playerHandPrimaryPlayer.get(i));
                                    defaultcard.setImageDrawable(createCardUI(playerHandPrimaryPlayer.get(i)).imageView.getDrawable());
                                    playerHandPrimaryPlayer.remove(playerHandPrimaryPlayer.get(i));

                                    nextRoundCards();
                                    setNextCurrentPlayer();
                                    updateDiscardpileListDB();
                                    break; //break, because you can only drag one card
                                }
                            }
                            if (owner != null) {
                                owner.removeView(v);
                            }
                            v.setVisibility(View.INVISIBLE);
                            leererAblagestapel.setVisibility(View.INVISIBLE);
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED: //4
                        view.invalidate();
                        break;
                    default:
                        break;
                }
            }
            return true;
        }
    }

    //Drag and Drop Auslegefeld vom Spieler 2
    private class ChoiceDragListener2 implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            if (addEnoughCards() && primaryPlayer.getColor().equals(currentPlayer.getColor())) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED: //1
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DRAG_EXITED: //6
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED: //5
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DROP: //Action 3
                        View v = (View) dragEvent.getLocalState();
                        ViewGroup owner = (ViewGroup) v.getParent();
                        if(currentPlayer.isAbgelegt()) { //currentPlayer hat Phase ausgelegt
                            //richtigen Spieler herausfinden
                            if (playerBlue != null && playerBlue.getLinearLayout().getId() == layoutPlayer2CardField.getId()) {
                                player = playerBlue;
                            } else if (playerGreen != null && playerGreen.getLinearLayout().getId() == layoutPlayer2CardField.getId()) {
                                player = playerGreen;
                            } else if (playerYellow != null && playerYellow.getLinearLayout().getId() == layoutPlayer2CardField.getId()) {
                                player = playerYellow;
                            } else if (playerRed != null && playerRed.getLinearLayout().getId() == layoutPlayer2CardField.getId()) {
                                player = playerRed;
                            }
                            if (player.isAbgelegt()) {
                                playerHandPrimaryPlayer = getHandCardsDB(); //Handkarten vom Currentplayer
                                if (playerHandPrimaryPlayer.size() != 0) {
                                    for (int i = 0; i < playerHandPrimaryPlayer.size(); i++) {
                                        if (v.equals(playerHandPrimaryPlayer.get(i).getCardUI()) && phase.getRightPhaseOtherPlayer(getPhasenumberPlayersDB(player)-1, playerHandPrimaryPlayer.get(i), getCardfieldCardlistPlayersDB(player))) {
                                                getCardfieldCardlistPlayersDB(player).add(playerHandPrimaryPlayer.get(i));
                                                playerHandPrimaryPlayer.remove(playerHandPrimaryPlayer.get(i));
                                                owner.removeView(v);
                                                layoutPlayer2CardField.addView(v);
                                                v.setVisibility(View.VISIBLE);
                                                v.setClickable(false);
                                                break;
                                        }
                                    }

                                }
                            }
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED: //4
                        view.invalidate();
                        break;
                    default:
                        break;
                }
            }
            return true;
        }
    }

    //Drag and Drop Auslegefeld Spieler 3
    private class ChoiceDragListener3 implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            if (addEnoughCards() && primaryPlayer.getColor().equals(currentPlayer.getColor())) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED: //1
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DRAG_EXITED: //6
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED: //5
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DROP: //Action 3
                        View v = (View) dragEvent.getLocalState();
                        ViewGroup owner = (ViewGroup) v.getParent();
                        if(currentPlayer.isAbgelegt()) { //currentPlayer hat Phase ausgelegt
                            //richtigen Spieler herausfinden
                            if (playerBlue != null && playerBlue.getLinearLayout().getId() == layoutPlayer3CardField.getId()) {
                                player = playerBlue;
                            } else if (playerGreen != null && playerGreen.getLinearLayout().getId() == layoutPlayer3CardField.getId()) {
                                player = playerGreen;
                            } else if (playerYellow != null && playerYellow.getLinearLayout().getId() == layoutPlayer3CardField.getId()) {
                                player = playerYellow;
                            } else if (playerRed != null && playerRed.getLinearLayout().getId() == layoutPlayer3CardField.getId()) {
                                player = playerRed;
                            }
                            if (player.isAbgelegt()) {
                                playerHandPrimaryPlayer = getHandCardsDB(); //Handkarten vom Currentplayer
                                if (playerHandPrimaryPlayer.size() != 0) {
                                    for (int i = 0; i < playerHandPrimaryPlayer.size(); i++) {
                                        if (v.equals(playerHandPrimaryPlayer.get(i).getCardUI()) && phase.getRightPhaseOtherPlayer((getPhasenumberPlayersDB(player)-1), playerHandPrimaryPlayer.get(i), getCardfieldCardlistPlayersDB(player))) {
                                            getCardfieldCardlistPlayersDB(player).add(playerHandPrimaryPlayer.get(i));
                                            playerHandPrimaryPlayer.remove(playerHandPrimaryPlayer.get(i));
                                            owner.removeView(v);
                                            layoutPlayer3CardField.addView(v);
                                            v.setVisibility(View.VISIBLE);
                                            v.setClickable(false);
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED: //4
                        view.invalidate();
                        break;
                    default:
                        break;
                }
            }
            return true;
        }
    }

    //Drag and Drop Auslegefeld Spieler 4
    private class ChoiceDragListener4 implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            if (addEnoughCards() && primaryPlayer.getColor().equals(currentPlayer.getColor())) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED: //1
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DRAG_EXITED: //6
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED: //5
                        //no action necessary
                        break;

                    case DragEvent.ACTION_DROP: //Action 3
                        View v = (View) dragEvent.getLocalState();
                        ViewGroup owner = (ViewGroup) v.getParent();
                        if(currentPlayer.isAbgelegt()) { //currentPlayer hat Phase ausgelegt
                            //richtigen Spieler herausfinden
                            if (playerBlue != null && playerBlue.getLinearLayout().getId() == layoutPlayer4CardField.getId()) {
                                player = playerBlue;
                            } else if (playerGreen != null && playerGreen.getLinearLayout().getId() == layoutPlayer4CardField.getId()) {
                                player = playerGreen;
                            } else if (playerYellow != null && playerYellow.getLinearLayout().getId() == layoutPlayer4CardField.getId()) {
                                player = playerYellow;
                            } else if (playerRed != null && playerRed.getLinearLayout().getId() == layoutPlayer4CardField.getId()) {
                                player = playerRed;
                            }
                            if (player.isAbgelegt()) {
                                playerHandPrimaryPlayer = getHandCardsDB(); //Handkarten vom Currentplayer
                                if (playerHandPrimaryPlayer.size() != 0) {
                                    for (int i = 0; i < playerHandPrimaryPlayer.size(); i++) {
                                        if (v.equals(playerHandPrimaryPlayer.get(i).getCardUI()) && phase.getRightPhaseOtherPlayer((getPhasenumberPlayersDB(player)-1), playerHandPrimaryPlayer.get(i), getCardfieldCardlistPlayersDB(player))) {
                                            getCardfieldCardlistPlayersDB(player).add(playerHandPrimaryPlayer.get(i));
                                            playerHandPrimaryPlayer.remove(playerHandPrimaryPlayer.get(i));
                                            owner.removeView(v);
                                            layoutPlayer4CardField.addView(v);
                                            v.setVisibility(View.VISIBLE);
                                            v.setClickable(false);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        break;

                    case DragEvent.ACTION_DRAG_ENDED: //4
                        view.invalidate();
                        break;
                    default:
                        break;
                }
            }
            return true;
        }
    }

    //Aktuelle in Player zugewiesene Phase wird in Textview am Spielfeld angezeigt
    private void setPhasenTextTextView() {
        getPlayerFromColor(primaryPlayer.getColorAsString()).setPhaseText();
        tvAktuellePhase.setText(getPlayerFromColor(primaryPlayer.getColorAsString()).getPhaseText());
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

    public String getCurrentRoom() {
        return currentRoom;
    }

    public String getUserColor() {
        return userColor;
    }

    private void gameInfoDB() {
        //database with synched info to play game
        Map<String, Object> gameInfo = new HashMap<>();
        gameInfo.put("RoomName", currentRoom);
        gameInfo.put("Round", round);
        Log.i("PlayerToList--------------------------------------------------------------", playerToList(currentPlayer).toString());
        gameInfo.put("CurrentPlayer", playerToList(currentPlayer));
        if (playerYellow != null) {
            gameInfo.put("PlayerYellow", playerToList(playerYellow));
        }
        if (playerBlue != null) {
            gameInfo.put("PlayerBlue", playerToList(playerBlue));
        }
        if (playerRed != null) {
            gameInfo.put("PlayerRed", playerToList(playerRed));
        }
        if (playerGreen != null) {
            gameInfo.put("PlayerGreen", playerToList(playerGreen));
        }
        if (startOrder != null) {
            gameInfo.put("StartOrder", playerList);
        }
        ArrayList<Integer> cards = new ArrayList<>();
        for (int i = 1; i <= 96; i++) {
            cards.add(i);
        }

        ArrayList<Integer> newCardList = new ArrayList<>();
        for (Cards card : cardlist) {
            newCardList.add(card.getID());
        }
        //discard pile
        StringBuilder newDiscardPile = new StringBuilder();
        for (Cards card : discardpileList) {
            newDiscardPile.append(card.getID());
        }

        gameInfo.put("DiceRoll", currentDiceRoll);
        gameInfo.put("Cheated", cheated);
        gameInfo.put("Cardlist", newCardList);
        gameInfo.put("DiscardpileList", newDiscardPile.toString());


        Log.i("gameInfo------------------------------------------------------------", gameInfo.toString());
        database.collection("gameInfo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (Objects.equals(document.get("RoomName"), currentRoom)) {
                                    newDBCollectionNeeded = false;
                                    break;
                                } else {
                                    newDBCollectionNeeded = true;
                                }
                            }

                            if (newDBCollectionNeeded) {
                                database.collection("gameInfo")
                                        .add(gameInfo)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.i("GameInfo -----------------------------", "success");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("EXCEPTION---------------------------------------------------------", e.getMessage());
                                    }
                                });
                            }
                        }
                    }
                });
    }

    public List<String> playerToList(Player player) {
        ArrayList<String> playerlist = new ArrayList<>();
        playerlist.add(player.getName());
        playerlist.add(player.getColor().toString());
        playerlist.add(player.getRoom());
        playerlist.add(String.valueOf(player.getPhaseNumber()));
        playerlist.add(String.valueOf(player.getMinusPoints()));
        String playerCardsID = "";
        for (Cards c : player.getPlayerHand()) {
            playerCardsID += (c.getID() + " ");
        }
        playerlist.add(playerCardsID);
        String cardField = "";
        for (Cards c : player.getCardField()) {
            if(c != null) {
                cardField += (c.getID() + " ");
            }
        }
        playerlist.add(cardField);
        playerlist.add(String.valueOf(player.abgelegt));
        playerlist.add(String.valueOf(player.getCurrentPosition()));

        return playerlist;
    }

    private void updateDiscardpileListDB() {
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String discardpile = "";

                            for (Cards cards : discardpileList) {
                                discardpile += cards.getID() + " ";
                            }

                            document.getReference().update("DiscardpileList", discardpile);
                        }
                    }
                });
    }

    //currentPlayer cheats
    private void updateCheated(boolean bool) {
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().update("Cheated", bool);
                        }
                    }
                });
    }

    public void setPhasenumberDB() {
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ArrayList playerl = (ArrayList) document.get("CurrentPlayer"); //welchen playerl du haben möchtest
                            playerl.set(3, (getPhasenumberDB() + 1)); //du setzt nun bei playerl index 3 einen neuen wert, und zwar der alte + 1
                            currentPlayer.setPhaseNumber((getPhasenumberDB() + 1));
                            playerl.set(7, true);
                            currentPlayer.setAbgelegt(true);
                            document.getReference().update("CurrentPlayer", playerl); //hier updatest den playerl in der DB mit den neu gesetzten werten, falls du was geändert hast

                            //update playerl phase number
                            if (playerl.get(1).equals("YELLOW")) {
                                playerYellow.setPhaseNumber(getPhasenumberDB());
                                playerYellow.setAbgelegt(true);
                            }
                            if (playerl.get(1).equals("BLUE")) {
                                playerBlue.setPhaseNumber(getPhasenumberDB());
                                playerBlue.setAbgelegt(true);
                            }
                            if (playerl.get(1).equals("GREEN")) {
                                playerGreen.setPhaseNumber(getPhasenumberDB());
                                playerGreen.setAbgelegt(true);
                            }
                            if (playerl.get(1).equals("RED")) {
                                playerRed.setPhaseNumber(getPhasenumberDB());
                                playerRed.setAbgelegt(true);
                            }
                            updatePlayers();
                        }
                    } else {
                        Log.d("DB phasenumber", "Error setting Data to Firestore: ", task.getException());
                    }
                });
    }

    public int getPhasenumberDB() {
        return currentPlayer.getPhaseNumber();
    }

    public int getPhasenumberPlayersDB(Player player) {
        return player.getPhaseNumber();
    }

    public boolean getPhaseAusgelegtDB(Player player) {
        return player.isAbgelegt();
    }

    public List<Cards> getCardfieldCardlistDB() {
        return currentPlayer.getCardField();
    }

    public List<Cards> getCardfieldCardlistPlayersDB(Player player) {
        return player.getCardField();
    }

    public int getCurrentPositionDB() {
        return currentPlayer.getCurrentPosition();
    }

    public List<Cards> getHandCardsDB() {
        return currentPlayer.getPlayerHand();
    }

    public void nextRoundCards() {
        if (getHandCardsDB().size() == 0) {
            ArrayList<Player> playerArrayList = new ArrayList<>();
            playerArrayList.add(playerBlue);
            playerArrayList.add(playerYellow);
            playerArrayList.add(playerRed);
            playerArrayList.add(playerGreen);

            //calc minuspunkte
            for (Player p : playerArrayList) {
                if (p != null) {
                    p.updateMinusPoints(p.getPlayerHand());
                }
            }

            //handcards
            cardlist = new ArrayList<>(allCards);
            cardDrawer.shuffleCards(cardlist);

            for (Player p : playerArrayList) {
                if (p != null) {
                    p.setPlayerHand(new ArrayList<>());
                    p.getLinearLayout().removeAllViews();
                }
            }

            layoutPlayer1.removeAllViews();
            layoutPlayer2.removeAllViews();
            layoutPlayer3.removeAllViews();
            layoutPlayer4.removeAllViews();
            layoutPlayer1CardField.removeAllViews();
            layoutPlayer2CardField.removeAllViews();
            layoutPlayer3CardField.removeAllViews();
            layoutPlayer4CardField.removeAllViews();

            for (Cards card : cardlist) {
                card.getCardUI().setClickable(true);
                card.getCardUI().setVisibility(View.INVISIBLE);
            }
            for (Cards card : allCards) {
                card.getCardUI().setClickable(true);
                card.getCardUI().setVisibility(View.INVISIBLE);
            }

            handCards.handCardsPlayer(layoutPlayers, cardlist, playerBlue, playerGreen, playerYellow, playerRed, primaryPlayer);


            //discardpile
            discardpileList = new ArrayList<>();

            SecureRandom rand = new SecureRandom();
            Cards randomCard = cardlist.get(rand.nextInt(cardlist.size()));
            cardlist.remove(randomCard);
            discardpileList.add(randomCard);
            defaultcard.setImageDrawable(createCardUI(discardpileList.get(0)).imageView.getDrawable());
            defaultcard.setOnDragListener(new ChoiceDragListener1());


            //cardfield and phase_abgelegt
            for (Player p : playerArrayList) {
                if (p != null) {
                    p.setCardField(new ArrayList<>());
                    p.setAbgelegt(false);
                }
            }
            setPhasenTextTextView();
        }
    }

    //update players
    private void updatePlayers() {
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.get("PlayerRed") != null && playerRed != null) {
                                document.getReference().update("PlayerRed", playerToList(playerRed));
                            }
                            if (document.get("PlayerBlue") != null && playerBlue != null) {
                                document.getReference().update("PlayerBlue", playerToList(playerBlue));
                            }
                            if (document.get("PlayerYellow") != null && playerYellow != null) {
                                document.getReference().update("PlayerYellow", playerToList(playerYellow));
                            }
                            if (document.get("PlayerGreen") != null && playerGreen != null) {
                                document.getReference().update("PlayerGreen", playerToList(playerGreen));
                            }

                        }

                    }
                });
    }

    private void currentPlayerToast(String color) {
        Toast.makeText(Playfield.this, "Player " + color + " is current Player", Toast.LENGTH_SHORT).show();
    }

    //get playerArray from DB and save as Player
    private void getPlayerFromDB(String color) {
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ArrayList tempPlayerList;
                            ArrayList lol;

                            if (Objects.equals(color, "BLUE")) {
                                tempPlayerList = (ArrayList) document.get("PlayerBlue");
                                if (tempPlayerList != null) {
                                    lol = is(tempPlayerList);
                                    playerBlue.setPhaseNumber(Integer.parseInt(tempPlayerList.get(3).toString()));
                                    playerBlue.setMinusPoints(Integer.parseInt(tempPlayerList.get(4).toString()));
                                    playerBlue.setPlayerHand((ArrayList<Cards>) lol.get(0));
                                    playerBlue.setCardField((ArrayList<Cards>) lol.get(1));
                                    playerBlue.setAbgelegt(Boolean.parseBoolean(tempPlayerList.get(7).toString()));

                                    if (playerBlue.abgelegt) {
                                        playerBlue.updateCardfieldCompletely(playerBlue.getCardField(), playerBlue.getLinearLayout());
                                    }
                                }
                            } else if (Objects.equals(color, "RED")) {
                                tempPlayerList = (ArrayList) document.get("PlayerRed");
                                if (tempPlayerList != null) {
                                    lol = is(tempPlayerList);
                                    playerRed.setPhaseNumber(Integer.parseInt(tempPlayerList.get(3).toString()));
                                    playerRed.setMinusPoints(Integer.parseInt(tempPlayerList.get(4).toString()));
                                    playerRed.setPlayerHand((ArrayList<Cards>) lol.get(0));
                                    playerRed.setCardField((ArrayList<Cards>) lol.get(1));
                                    playerRed.setAbgelegt(Boolean.parseBoolean(tempPlayerList.get(7).toString()));

                                    if (playerRed.abgelegt) {
                                        playerRed.updateCardfieldCompletely(playerRed.getCardField(), playerRed.getLinearLayout());
                                    }
                                }
                            } else if (Objects.equals(color, "YELLOW")) {
                                tempPlayerList = (ArrayList) document.get("PlayerYellow");
                                if (tempPlayerList != null) {
                                    lol = is(tempPlayerList);
                                    playerYellow.setPhaseNumber(Integer.parseInt(tempPlayerList.get(3).toString()));
                                    playerYellow.setMinusPoints(Integer.parseInt(tempPlayerList.get(4).toString()));
                                    playerYellow.setPlayerHand((ArrayList<Cards>) lol.get(0));
                                    playerYellow.setCardField((ArrayList<Cards>) lol.get(1));
                                    playerYellow.setAbgelegt(Boolean.parseBoolean(tempPlayerList.get(7).toString()));

                                    if (playerYellow.abgelegt) {
                                        playerYellow.updateCardfieldCompletely(playerYellow.getCardField(), playerYellow.getLinearLayout());
                                    }
                                }
                            } else if (Objects.equals(color, "GREEN")) {
                                tempPlayerList = (ArrayList) document.get("PlayerGreen");
                                if (tempPlayerList != null) {
                                    lol = is(tempPlayerList);
                                    playerGreen.setPhaseNumber(Integer.parseInt(tempPlayerList.get(3).toString()));
                                    playerGreen.setMinusPoints(Integer.parseInt(tempPlayerList.get(4).toString()));
                                    playerGreen.setPlayerHand((ArrayList<Cards>) lol.get(0));
                                    playerGreen.setCardField((ArrayList<Cards>) lol.get(1));
                                    playerGreen.setAbgelegt(Boolean.parseBoolean(tempPlayerList.get(7).toString()));

                                    if (playerGreen.abgelegt) {
                                        playerGreen.updateCardfieldCompletely(playerGreen.getCardField(), playerGreen.getLinearLayout());
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private ArrayList<ArrayList<Cards>> is(ArrayList playerList) {
        //player hand cards
        ArrayList<String> cardIds = new ArrayList(Arrays.asList(playerList.get(5).toString().trim().split(" ")));
        ArrayList<Cards> cards = new ArrayList<Cards>();

        for (String id : cardIds) {
            if (id.length() != 0) {
                cards.add(allCards.get(Integer.parseInt(id) - 1));
            }
        }

        //card field cards
        ArrayList<String> cardIdsDepo = new ArrayList(Arrays.asList(playerList.get(6).toString().trim().split(" ")));
        ArrayList<Cards> cardsDepo = new ArrayList<>();
        for (String id : cardIdsDepo) {
            if (id.length() != 0) {
                cardsDepo.add(allCards.get(Integer.parseInt(id) - 1));
            }
        }

        ArrayList<ArrayList<Cards>> lol = new ArrayList<>();
        lol.add(cards);
        lol.add(cardsDepo);

        return lol;
    }

    private void setCurrentPlayerInDB(Player currentplayer) {
        database.collection("gameInfo").whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            //give consequences
                            //if accused right:
                            List<String> playerC = playerToList(currentplayer);
                            document.getReference().update("CurrentPlayer", playerC);
                        }
                    }
                });
    }

    private ArrayList<Cards> addCardsToList(String from) {
        ArrayList<Cards> newList = new ArrayList<>();
        String[] ids = from.trim().split(" ");

        if (ids[0].isEmpty()) {
            return new ArrayList<>();
        }

        for (String id : ids) {
            newList.add(allCards.get(Integer.parseInt(id) - 1));
        }
        return newList;
    }

}