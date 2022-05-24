package com.example.phase10_se2;

import static android.os.SystemClock.sleep;

import android.content.ClipData;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Playfield extends AppCompatActivity {
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
    ArrayList<Cards> discardpileList;//Ablagestapel
    ArrayList<Cards> cardfieldCardlist;

    ArrayList<ImageView> Imagelist;
    ArrayList<Cards> drawpileList;      //Ziehstapel
    TextView leererAblagestapel;

    Button exitGame;        //Spiel verlassen Button
    Button btnHideAktionskarte;
    Button btnShowAktionskarte;
    ImageView ivShowAktionskarte;
    TextView tvAktuellePhase;
    Button btnCheckPhase;

    ImageView ivPlayerBlue;
    ImageView ivPlayerYellow;
    ImageView ivPlayerGreen;
    ImageView ivPlayerRed;

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
    ArrayList<Cards> playerHandPrimaryPlayer;

    public ArrayList<Cards> getPlayer1HandBlue() {
        return playerHandBlue;
    }

    public ArrayList<Cards> getPlayerHandRed() {
        return playerHandRed;
    }

    public ArrayList<Cards> getPlayerHandYellow() {
        return playerHandYellow;
    }

    public ArrayList<Cards> getPlayerHandGreen() {
        return playerHandGreen;
    }

    public Player getPrimaryPlayer() {
        return primaryPlayer;
    }

    Player player;

    //Round and phase
    Phase phase;
    boolean currentPhaseRight = false;
    private long doubleClickLastTime = 0L;
    int round = 1;
    ArrayList startOrder = new ArrayList();
    int currentDiceRoll = 0;
    boolean cheated = false;

    boolean newDBCollectionNeeded = false;


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
    private long leftTime = startTimer;


    FirebaseFirestore database;
    ArrayList<String> playerList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfield);
        builder = new AlertDialog.Builder(Playfield.this);

        currentRoom = getIntent().getExtras().getString("CurrentRoom");
        userColor = getIntent().getExtras().getString("Color");
        Toast.makeText(this, "YOU ARE THE " + userColor + " PLAYER!", Toast.LENGTH_LONG).show();
        database = FirebaseFirestore.getInstance();    //verknuepfung
        FirebaseFirestore.setLoggingEnabled(true);

        database.collection("users")
                .whereEqualTo("Room", currentRoom)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                initializePlayer(documentSnapshot, userColor, currentRoom);
                                playerList.add(documentSnapshot.getString("Color"));
                            }
                            CreatePlayfield();
                        }
                    }
                });

    }

    private void CreatePlayfield() {
        //ermitteln von current Player
        Log.i("TEST", playerList.toString());
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

        //entfernt die label Leiste (Actionbar) auf dem Playfield
        //Toast.makeText(Playfield.this, "Currentplayer: " + currentPlayer.getColor(), Toast.LENGTH_SHORT).show();

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
        btnCheckPhase = findViewById(R.id.buttonCheckPhase);


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
        layoutPlayer1CardField = findViewById(R.id.player1PhaseAblegen);
        layoutPlayer2CardField = findViewById(R.id.player2PhaseAblegen);
        // layoutPlayer2CardField.setOnDragListener(new ChoiceDragListener());
        layoutPlayer3CardField = findViewById(R.id.player3PhaseAblegen);
        //layoutPlayer3CardField.setOnDragListener(new ChoiceDragListener());
        layoutPlayer4CardField = findViewById(R.id.player4PhaseAblegen);
        //layoutPlayer4CardField.setOnDragListener(new ChoiceDragListener());


        //Button, um zu überprüfen, ob die Phase richtig ist
        cardfieldCardlist = new ArrayList<>();
        phase = new Phase();
        btnCheckPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ohne DB so prüfen
                if(phase.checkPhase2(cardfieldCardlist)){
                    int phase = 3;
            }
            */
                // funktionier noch nicht ohne DB
                if (phase.getRightPhase(cardfieldCardlist)) {
                    if (currentPlayer.getPhaseNumber() != 10) {
                        currentPlayer.setPhaseNumber(currentPlayer.getPhaseNumber() + 1);
                    }
                    currentPhaseRight = true; //TODO: pro Spieler in DB speichern
                } else {
                    while (layoutPlayer1CardField.getChildCount() != 0) { //TODO: richtiges Layout?
                        View v = layoutPlayer1CardField.getChildAt(0);
                        ViewGroup owner = (ViewGroup) v.getParent();
                        owner.removeView(v);
                        layoutPlayer1.addView(v);
                        v.setVisibility(View.VISIBLE);
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
        for (int i = 0; i < 96; i++) {
            cardlist.get(i).setCardUI(createCardUI(cardlist.get(i)));
        }
        updateCardlistDB();


        //Karten werden gemischt
        cardDrawer.shuffleCards(cardlist);

        //Ziehstapel leer?
        cardDrawer.isInitialCardsEmpty();

        //Handkarten werden ausgeteilt
        handCards.HandCardsPlayer(layoutPlayer1, layoutPlayer2, layoutPlayer3, layoutPlayer4, layoutPlayer1CardField, layoutPlayer2CardField, layoutPlayer3CardField, layoutPlayer4CardField, cardlist, playerBlue, playerGreen, playerYellow, playerRed, primaryPlayer);
        if (playerBlue != null) {
            for (Cards card : playerBlue.getPlayerHand()) {
                card.getCardUI().setOnClickListener(listener);
                card.getCardUI().setOnTouchListener(new ChoiceTouchListener());
            }
        }

        if (playerGreen != null) {
            for (Cards card : playerGreen.getPlayerHand()) {
                card.getCardUI().setOnClickListener(listener);
                card.getCardUI().setOnTouchListener(new ChoiceTouchListener());
            }
        }
        if (playerYellow != null) {
            for (Cards card : playerYellow.getPlayerHand()) {
                card.getCardUI().setOnClickListener(listener);
                card.getCardUI().setOnTouchListener(new ChoiceTouchListener());
            }
        }
        if (playerRed != null) {
            for (Cards card : playerRed.getPlayerHand()) {
                card.getCardUI().setOnClickListener(listener);
                card.getCardUI().setOnTouchListener(new ChoiceTouchListener());
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
        defaultcard.setImageDrawable(createCardUI(discardpileList.get(0)).getDrawable());
        defaultcard.setOnDragListener(new ChoiceDragListener());


        defaultcard.setOnClickListener(view -> {
            addCardsDiscardpile();
        });


        //Timer
        TextView timer = findViewById(R.id.Timer);
        classTimer = new Timer(timer, timerturn, leftTime);
        classTimer.startTimer();
        classTimer.updateCountDownText();


        gameInfoDB();
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
                .setMessage("Are you sure, you want to accuse 'CurrentPlayer' of cheating?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //check if cheating == true
                        database.collection("gameInfo").whereEqualTo("RoomName", currentRoom)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if (document.getBoolean("Cheated")) {
                                                    //give consequences
                                                    //if accused right:
                                                    ArrayList player = (ArrayList) document.get("CurrentPlayer");
                                                    int Phase = (int) player.get(3) - 1;
                                                    player.set(3, Phase);
                                                    document.getReference().update("CurrentPlayer", player);
                                                    Toast.makeText(Playfield.this, "Player " + currentPlayer + " cheated, you were right!", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    //if accused wrong:
                                                    //TODO: UPDATE RIGHT PLAYER
                                                    ArrayList player;
                                                    if (document.get("PlayerBlue") != null) {
                                                        if (primaryPlayer.getColor().equals(PlayerColor.BLUE)) {
                                                            player = (ArrayList) document.get("PlayerBlue");
                                                            int minusPoints = (int) player.get(4) + 10;
                                                            player.set(4, minusPoints);
                                                            document.getReference().update("PlayerBlue", player);
                                                        }
                                                    } else if (document.get("PlayerRed") != null) {
                                                        if (primaryPlayer.getColor().equals(PlayerColor.RED)) {
                                                            player = (ArrayList) document.get("PlayerRed");
                                                            int minusPoints = (int) player.get(4) + 10;
                                                            player.set(4, minusPoints);
                                                            document.getReference().update("PlayerRed", player);
                                                        }
                                                    } else if (document.get("PlayerYellow") != null) {
                                                        if (primaryPlayer.getColor().equals(PlayerColor.YELLOW)) {
                                                            player = (ArrayList) document.get("PlayerYellow");
                                                            int minusPoints = (int) player.get(4) + 10;
                                                            player.set(4, minusPoints);
                                                            document.getReference().update("PlayerYellow", player);

                                                        }
                                                    } else if (document.get("PlayerGreen") != null) {
                                                        if (primaryPlayer.getColor().equals(PlayerColor.GREEN)) {
                                                            player = (ArrayList) document.get("PlayerGreen");
                                                            int minusPoints = (int) player.get(4) + 10;
                                                            player.set(4, minusPoints);
                                                            document.getReference().update("PlayerGreen", player);

                                                        }
                                                    }

                                                    Toast.makeText(Playfield.this, "Player " + currentPlayer + " did not cheat, you were wrong!", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            }
                                        }
                                    }
                                });

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Playfield.this, "No one got accused!", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();

                    }
                });


        //TODO: delete button and move function to game start
        findViewById(R.id.button).setOnClickListener(view -> {
            throwingDice(player);
        });

        //Spiel verlassen
        exitGame = findViewById(R.id.leaveGame);
        exitGame.setOnClickListener(view -> leaveGame());
    }

    public void leaveGame() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //Bei 2+ Spieler wird weiter gespielt
        //TODO:Methode aufrufen wieviel Spieler sind
    }

    public void initializePlayer(DocumentSnapshot documentSnapshot, String userColor, String currentRoom) {
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


    //Eine Karte vom Ablagestapel ziehen
    protected void addCardsDiscardpile() {
        int size = discardpileList.size();
        if (size != 0) {

            discardpileList.get(size - 1).getCardUI().setVisibility(View.VISIBLE);
            if (playerYellow != null && playerYellow.getColor().equals(primaryPlayer.getColor())) {
                handCards.updateHand(playerYellow.getPlayerHand(), discardpileList.get(size - 1), layoutPlayer1, 0, cardlist);
                discardpileList.get(size - 1).getCardUI().setOnClickListener(listener);
                discardpileList.get(size - 1).getCardUI().setOnTouchListener(new ChoiceTouchListener());
            }
            if (playerBlue != null && playerBlue.getColor().equals(primaryPlayer.getColor())) {
                handCards.updateHand(playerBlue.getPlayerHand(), discardpileList.get(size - 1), layoutPlayer1, 0, cardlist);
                discardpileList.get(size - 1).getCardUI().setOnClickListener(listener);
                discardpileList.get(size - 1).getCardUI().setOnTouchListener(new ChoiceTouchListener());
            }
            if (playerRed != null && playerRed.getColor().equals(primaryPlayer.getColor())) {
                handCards.updateHand(playerRed.getPlayerHand(), discardpileList.get(size - 1), layoutPlayer1, 0, cardlist);
                discardpileList.get(size - 1).getCardUI().setOnClickListener(listener);
                discardpileList.get(size - 1).getCardUI().setOnTouchListener(new ChoiceTouchListener());
            }
            if (playerGreen != null && playerGreen.getColor().equals(primaryPlayer.getColor())) {
                handCards.updateHand(playerGreen.getPlayerHand(), discardpileList.get(size - 1), layoutPlayer1, 0, cardlist);
                discardpileList.get(size - 1).getCardUI().setOnClickListener(listener);
                discardpileList.get(size - 1).getCardUI().setOnTouchListener(new ChoiceTouchListener());
            }
            discardpileList.remove(size - 1);
            if ((size - 1) != 0) {
                defaultcard.setImageDrawable(createCardUI(discardpileList.get(size - 2)).getDrawable());
            }
        } else {
            leererAblagestapel.setVisibility(View.VISIBLE);
        }
    }

    //Für Aktionfeld
    protected void addRandomCardsDiscardpile() {
        if (discardpileList.size() != 0) {
            Random rand = new Random();
            Cards randomCard = discardpileList.get(rand.nextInt(discardpileList.size()));
            handCards.updateHand(playerBlue.getPlayerHand(), randomCard, layoutPlayer1, 0, cardlist);
            discardpileList.remove(randomCard);
        } else {
            leererAblagestapel.setVisibility(View.VISIBLE);
        }
    }


    //Karte ziehen
    protected void addCard() {
        //only currentPlayer kann ziehen
        if (playerYellow != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerYellow.getColor().equals(primaryPlayer.getColor())) {
            handCards.updateHand(playerYellow.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
            cardlist.get(0).getCardUI().setOnClickListener(listener);
            cardlist.get(0).getCardUI().setOnTouchListener(new ChoiceTouchListener());
        }
        if (playerBlue != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerBlue.getColor().equals(primaryPlayer.getColor())) {
            handCards.updateHand(playerBlue.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
            cardlist.get(0).getCardUI().setOnClickListener(listener);
            cardlist.get(0).getCardUI().setOnTouchListener(new ChoiceTouchListener());
        }
        if (playerRed != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerRed.getColor().equals(primaryPlayer.getColor())) {
            handCards.updateHand(playerRed.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
            cardlist.get(0).getCardUI().setOnClickListener(listener);
            cardlist.get(0).getCardUI().setOnTouchListener(new ChoiceTouchListener());
        }
        if (playerGreen != null && currentPlayer.getColor().equals(primaryPlayer.getColor()) && playerGreen.getColor().equals(primaryPlayer.getColor())) {
            handCards.updateHand(playerGreen.getPlayerHand(), cardlist.get(0), layoutPlayer1, 0, cardlist);
            cardlist.get(0).getCardUI().setOnClickListener(listener);
            cardlist.get(0).getCardUI().setOnTouchListener(new ChoiceTouchListener());
        }
    }

    private ImageView createCardUI(Cards cards) {
        ImageView imageView = new ImageView(getApplicationContext());
        cardUIManager.setCardImage(cards, imageView);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(35, 120, 1);
        imageView.setLayoutParams(params);
        imageView.setTag("c" + cards.getID());
        imageView.setVisibility(View.INVISIBLE);
        imageView.setClickable(true);
        imageView.setFocusable(true);
        return imageView;
    }


    public ArrayList<Cards> getPrimaryHandcards() {
        ArrayList<Cards> handcards;
        if (playerYellow != null && playerYellow.getColor().equals(primaryPlayer.getColor())) {
            return handcards = playerYellow.getPlayerHand();
        } else if (playerBlue != null && playerBlue.getColor().equals(primaryPlayer.getColor())) {
            return handcards = playerBlue.getPlayerHand();
        } else if (playerRed != null && playerRed.getColor().equals(primaryPlayer.getColor())) {
            return handcards = playerRed.getPlayerHand();
        } else if (playerGreen != null && playerGreen.getColor().equals(primaryPlayer.getColor())) {
            return handcards = playerGreen.getPlayerHand();
        }
        return null;
    }

    //Karten auslegen - 1x Click Karte wird ausgelegt, 2x Click Karte zurück auf die Hand
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Log.e("debugClick", view.toString());


            if (System.currentTimeMillis() - doubleClickLastTime < 700) {
                doubleClickLastTime = 0;
                View v = view;
                ViewGroup owner = (ViewGroup) v.getParent();
                //Handkarte zurück nehmen
                playerHandPrimaryPlayer = getPrimaryHandcards();
                for (int i = 0; i < cardfieldCardlist.size(); i++) {
                    if (v.equals(cardfieldCardlist.get(i).getCardUI())) {
                        playerHandPrimaryPlayer.add(cardfieldCardlist.get(i));
                        cardfieldCardlist.remove(cardfieldCardlist.get(i));
                    }
                }
                owner.removeView(v);
                layoutPlayer1.addView(v);
                v.setVisibility(View.VISIBLE);
            } else {
                doubleClickLastTime = System.currentTimeMillis();
                View v = view;
                ViewGroup owner = (ViewGroup) v.getParent();
                //Array mit den ausgelegten Karten befüllen
                playerHandPrimaryPlayer = getPrimaryHandcards();
                for (int i = 0; i < playerHandPrimaryPlayer.size(); i++) {
                    if (v.equals(playerHandPrimaryPlayer.get(i).getCardUI())) {
                        cardfieldCardlist.add(playerHandPrimaryPlayer.get(i));
                        playerHandPrimaryPlayer.remove(playerHandPrimaryPlayer.get(i));
                    }
                }
                owner.removeView(v);
                layoutPlayer1CardField.addView(v);
                v.setVisibility(View.VISIBLE);
            }
        }
    };

    //Class allows us to drag view
    private final class ChoiceTouchListener implements View.OnTouchListener {
        boolean isMoved = true;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    //prev = System.currentTimeMillis() / 100000;
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDragAndDrop(data, shadowBuilder, view, 0);
                    view.setVisibility(View.VISIBLE);
                    Log.e("touch", view.toString());
                    isMoved = false;
                    break;
            }
            if (!isMoved) {
                Log.e("click", view.toString());
            }


            return false;


        }
    }

    //--> funktion nicht mehr richtig wegen onClick Listener
    //Class to drop
    //ChoiceDragListener
    private class ChoiceDragListener implements View.OnDragListener {
        //Drawable enterShape = getResources().getDrawable(R.drawable.gruen12);
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
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
                    Log.e("debugN", dragEvent.toString());
                    View v = (View) dragEvent.getLocalState();
                    ViewGroup owner = (ViewGroup) v.getParent();
                    //Karte zum Ablegestapel hinzufügen
                    playerHandPrimaryPlayer = getPrimaryHandcards();
                    for (int i = 0; i < playerHandPrimaryPlayer.size(); i++) {
                        if (v.equals(playerHandPrimaryPlayer.get(i).getCardUI())) {
                            discardpileList.add(playerHandPrimaryPlayer.get(i));
                            defaultcard.setImageDrawable(createCardUI(playerHandPrimaryPlayer.get(i)).getDrawable());
                            playerHandPrimaryPlayer.remove(playerHandPrimaryPlayer.get(i));
                        }
                    }
                    owner.removeView(v);
                    //layoutDiscardpile.addView(v);
                    v.setVisibility(View.INVISIBLE);
                    break;

                case DragEvent.ACTION_DRAG_ENDED: //4
                    view.invalidate();
                default:
                    break;
            }
            return true;
        }
    }


    //Aktuelle in Player zugewiesene Phase wird in Textview am Spielfeld angezeigt
    public void setPhasenTextTextView() {
        tvAktuellePhase.setText(player.getPhaseText());
    }


    private boolean checkblue(FieldColor fieldColor) {
        if (fieldColor.equals(FieldColor.BLUE)) {

            //Code..
        }
        return true;
    }

    public void throwingDice(Player player) {
        int diceValue = 1;

//        while (diceFragment.getAcceleration() < 1) { //maybe replace with threshold
//            sleep(10);
//        }
//
//
//        while (diceFragment.getAcceleration() > 1) { //maybe replace with threshold
//            diceValue = diceFragment.getLastDiceValue();
//            sleep(100);
//
//            int timeSpent = 0;
//            int sleepDurationInMs = 10;
//            while (diceFragment.getAcceleration() < 1 && timeSpent < 3000) { //maybe replace with threshold
//                sleep(sleepDurationInMs);
//                timeSpent += sleepDurationInMs;
//            }
//        }

        //TODO: CANT MOVE BECAUSE PLAYERVIEW == NULL?!
        //TODO: FIX PLAYERVIEW
        playerBlue.move(diceValue);
    }

    public void decideStartingPlayer() { //TODO: problem: player != primary player wont get put into map
        //get array of active players
        ArrayList<Player> activePlayers = getActivePlayers();
        SortedMap<Integer, Player> startingDiceValues = new TreeMap<>();

        for (Player player : activePlayers) {
            int lastDiceValue = 0;

            Toast.makeText(getApplicationContext(), player.getName() + "'s turn", Toast.LENGTH_LONG);
            if (player.equals(this.player)) {
                diceFragment.register();

                while (diceFragment.getAcceleration() < 1) { //maybe replace with threshold
                    sleep(10);
                }
                while (diceFragment.getAcceleration() > 1) { //maybe replace with threshold
                    lastDiceValue = diceFragment.getLastDiceValue();
                    sleep(100);

                    int timeSpent = 0;
                    int sleepDurationInMs = 10;
                    while (diceFragment.getAcceleration() < 1 && timeSpent < 3000) {
                        sleep(sleepDurationInMs);
                        timeSpent += sleepDurationInMs;
                    }
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


    public String getCurrentRoom() {
        return currentRoom;
    }

    public String getUserColor() {
        return userColor;
    }

    public void gameInfoDB() {
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
            gameInfo.put("StartOrder", startOrder);
        }
        gameInfo.put("DiceRoll", currentDiceRoll);
        gameInfo.put("Cheated", cheated);
        gameInfo.put("Cardlist", cardlist);
        gameInfo.put("DiscardpileList", discardpileList);


        //Log.i("gameInfo------------------------------------------------------------", gameInfo.toString());
        database.collection("gameInfo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                }
            }
        });
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

    public ArrayList<String> playerToList(Player player) {
        ArrayList<String> playerList = new ArrayList();
        playerList.add(player.getName());
        playerList.add(player.getColor().toString());
        playerList.add(player.getRoom());
        playerList.add(String.valueOf(player.getPhaseNumber()));
        playerList.add(String.valueOf(player.getMinusPoints()));
        ArrayList<Integer> playerCardsID = new ArrayList();
        for (Cards c : player.getPlayerHand()) {
            playerCardsID.add(c.getID());
        }
        playerList.add(playerCardsID.toString());
        playerList.add(Arrays.toString(player.getCardField().toArray()));
        playerList.add(String.valueOf(player.abgelegt));
        playerList.add(String.valueOf(player.getCurrentPosition()));

        return playerList;
    }

    public void updateCardlistDB() {
        //update Database
        /* TODO: save card id + color in DB, not card view
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().update("Cardlist", cardlist);
                            }
                        }
                    }
                });

         */
    }

    public void updateDiscardpileListDB() {
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().update("DiscardpileList", discardpileList);
                            }
                        }
                    }
                });
    }

    public void updateRoundDB() {
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().update("Round", round);
                            }
                        }
                    }
                });
    }

    public void getCurrentPlayerDB() {
        database.collection("gameInfo")
                .whereEqualTo("RoomName", currentRoom)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                currentPlayer = (Player) document.get("CurrentPlayer");
                            }
                        }
                    }
                });
    }
}

