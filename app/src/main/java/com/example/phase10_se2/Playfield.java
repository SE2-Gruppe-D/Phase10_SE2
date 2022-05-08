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
    LinearLayout layoutPlayer1CardField;

    CardUIManager cardUIManager;
    CardDrawer cardDrawer;
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
    private long leftTime = startTimer;

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

        //Button, um zu überprüfen, ob die Phase richtig ist

        btnCheckPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPhase) --> next Phase
                //else if false ->
                    while (layoutPlayer1CardField.getChildCount()!=0 ) {
                        View v = layoutPlayer1CardField.getChildAt(0);
                        ViewGroup owner = (ViewGroup) v.getParent();
                        owner.removeView(v);
                        layoutPlayer1.addView(v);
                        v.setVisibility(View.VISIBLE);
                    }
            }
        });

        discardpileList = new ArrayList<>();
        cardlist = new ArrayList<>();
        cardfieldCardlist = new ArrayList<>();

        deckcard = findViewById(R.id.deckblatt);
        defaultcard = findViewById(R.id.defaultcard);
        leererAblagestapel = findViewById(R.id.leererStapel);
        layoutPlayer1 = findViewById(R.id.player1);
        layoutPlayer2 = findViewById(R.id.player2);
        layoutPlayer3 = findViewById(R.id.player3);
        layoutPlayer4 = findViewById(R.id.player4);
        layoutPlayer1CardField = findViewById(R.id.player1PhaseAblegen);

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

        //Timer
        TextView timer = findViewById(R.id.Timer);
        classTimer = new Timer(timer, timerturn, leftTime);
        classTimer.startTimer();
        classTimer.updateCountDownText();

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

        //Spiel verlassen
        exitGame= findViewById(R.id.leaveGame);
        exitGame.setOnClickListener(view -> leaveGame());
    }
    public void leaveGame(){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initializePlayer(DocumentSnapshot documentSnapshot, String userColor, String currentRoom) {
        if (Objects.equals(documentSnapshot.getString("Color"), userColor)) {
            switch (userColor) {
                case "RED":
                    playerRed = new Player(documentSnapshot.getString("Name"), PlayerColor.RED, currentRoom, 1, 0, new ArrayList<>(), new ArrayList<>());
                    primaryPlayer = playerRed;
                    break;
                case "BLUE":
                    playerBlue = new Player(documentSnapshot.getString("Name"), PlayerColor.BLUE, currentRoom, 1, 0, new ArrayList<>(), new ArrayList<>());
                    primaryPlayer = playerBlue;
                    break;
                case "YELLOW":
                    playerYellow = new Player(documentSnapshot.getString("Name"), PlayerColor.YELLOW, currentRoom, 1, 0, new ArrayList<>(), new ArrayList<>());
                    primaryPlayer = playerYellow;
                    break;
                case "GREEN":
                    playerGreen = new Player(documentSnapshot.getString("Name"), PlayerColor.GREEN, currentRoom, 1, 0, new ArrayList<>(), new ArrayList<>());
                    primaryPlayer = playerGreen;
                    break;
                default:
                    break;

            }
            Log.i("-------------------------------------------", "Color: " + primaryPlayer.getColor());

        }

        if (Objects.equals(documentSnapshot.getString("Color"), "RED")) {
            playerRed = new Player(documentSnapshot.getString("Name"), PlayerColor.RED, currentRoom, 1, 0, new ArrayList<>(), new ArrayList<>());
            playerRed.setPlayerview(findViewById(R.id.ivPR));
            playerRed.getPlayerview().setVisibility(View.VISIBLE);
        }
        if (Objects.equals(documentSnapshot.getString("Color"), "BLUE")) {
            playerBlue = new Player(documentSnapshot.getString("Name"), PlayerColor.BLUE, currentRoom, 1, 0, new ArrayList<>(), new ArrayList<>());
            playerBlue.setPlayerview(findViewById(R.id.ivPB));
            playerBlue.getPlayerview().setVisibility(View.VISIBLE);
        }
        if (Objects.equals(documentSnapshot.getString("Color"), "YELLOW")) {
            playerYellow = new Player(documentSnapshot.getString("Name"), PlayerColor.YELLOW, currentRoom, 1, 0, new ArrayList<>(), new ArrayList<>());
            playerYellow.setPlayerview(findViewById(R.id.ivPY));
            playerYellow.getPlayerview().setVisibility(View.VISIBLE);
        }
        if (Objects.equals(documentSnapshot.getString("Color"), "GREEN")) {
            playerGreen = new Player(documentSnapshot.getString("Name"), PlayerColor.GREEN, currentRoom, 1, 0, new ArrayList<>(), new ArrayList<>());
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
        cards.getCardUI().setOnTouchListener(new ChoiceTouchListener());
        cards.getCardUI().setOnDragListener(new ChoiceDragListener());
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

        } else if (playerGreen != null) {
            updateHand(playerGreen.getPlayerHand(), cardlist.get(0), layoutPlayer4, -90);
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


    //Class allows us to drag view
    private final class ChoiceTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if ((motionEvent.getAction() == MotionEvent.ACTION_DOWN) && ((ImageView) view).getDrawable() != null) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                return true;
            } else return false;
        }
    }

    //Class to drop
    private class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;

                case DragEvent.ACTION_DROP:
                    ClipData.Item item = dragEvent.getClipData().getItemAt(0);//the source image
                    //view.invalidate();
                    //löschen im altem Layout
                    View v = (View) dragEvent.getLocalState();
                    ViewGroup owner = (ViewGroup) v.getParent();

                    //Array mit den ausgelegten Karten befüllen
                    //primaryPlayer an farbe anpassen, weil primaryPlayer.getHand = null
                    primaryPlayer = playerBlue;
                    for(int i = 0; i < primaryPlayer.getPlayerHand().size(); i++){
                        if(v.equals(primaryPlayer.getPlayerHand().get(i).getCardUI())){
                            cardfieldCardlist.add(primaryPlayer.getPlayerHand().get(i));
                            primaryPlayer.getPlayerHand().remove(primaryPlayer.getPlayerHand().get(i));
                        }
                    }
                    owner.removeView(v);
                    layoutPlayer1CardField.addView(v);
                    v.setVisibility(View.VISIBLE);

                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    view.invalidate();
                    return true;
            }
            return true;
        }
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


