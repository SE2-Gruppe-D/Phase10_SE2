package com.example.phase10_se2;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phase10_se2.enums.PlayerColor;
import com.example.phase10_se2.helper.Dice;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class DiceFragment extends Fragment implements SensorEventListener {
    private static final boolean TESTMODE = false;
    private float shakeThreshold;  //Threshold for the acceleration sensor to trigger dice generation
    private ImageView diceView;
    private boolean moved = false;
    private boolean cheatUsed = false;
    private Dice dice;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int lastDiceValue = -1;
    private PlayerColor currentPlayerColor = null;
    private PlayerColor playerColor;
    private String room;
    private FirebaseFirestore database;
    private Playfield playfield;
    private static final String GAME_INFO = "gameInfo";
    private static final String ROOM_NAME = "RoomName";


    public static DiceFragment newInstance() {
        return new DiceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        playfield = (Playfield) getActivity();

        room = Objects.requireNonNull(playfield).getCurrentRoom();
        playerColor = definePlayerColor(playfield.getUserColor());
        database = createDBConnection();

        database.collection(GAME_INFO)
                .whereEqualTo(ROOM_NAME, room)
                .addSnapshotListener((value, error) -> {

                    if (error != null) {
                        Log.w(TAG, "Listen failed.", error);
                        return;
                    }

                    if (value != null) {
                        database.collection(GAME_INFO)
                                .whereEqualTo(ROOM_NAME, room)
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            //CurrentPlayer for dice throwing
                                            ArrayList currentPlayer = (ArrayList) document.get("CurrentPlayer");
                                            boolean cheated = Boolean.parseBoolean(String.valueOf(document.get("Cheated")));
                                            if (cheated && ! cheatUsed) {
                                                moved = false;
                                                cheatUsed = true;
                                            }

                                            if ((currentPlayer != null && currentPlayerColor == null) || (currentPlayer != null && !currentPlayerColor.equals(definePlayerColor((String) currentPlayer.get(1))))) {
                                                currentPlayerColor = definePlayerColor((String) currentPlayer.get(1));
                                                cheatUsed = false;
                                                moved = false;
                                            }

                                            //last dice value for cheating
                                            int diceRoll = document.get("DiceRoll", Integer.class);

                                            if (diceRoll != 0 && lastDiceValue != diceRoll && !playerColor.equals(currentPlayerColor)) {
                                                lastDiceValue = diceRoll;
                                                setDiceView(diceRoll);
                                                startCheatTimer();
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

        return inflater.inflate(R.layout.dice_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        dice = new Dice();
        initViews();
        initAccelerometer();
    }

    //onResume() register the accelerometer for listening the events
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    //onPause() unregister the accelerometer for stop listening the events
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public boolean register() {
        initAccelerometer();
        return sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    private void initViews() {
        this.diceView = getView().findViewById(R.id.DiceView);
    }

    private void initAccelerometer() {
        if (sensorManager == null) {
            sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) { //if an accelerator got created
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
                shakeThreshold = accelerometer.getMaximumRange() / 10;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { //needs to be implemented, but does nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (TESTMODE || (currentPlayerColor != null && currentPlayerColor.equals(playerColor))) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) (Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH);

            if (acceleration > 2) {
                Log.i("DiceActivity", "sensor has been activated. Trying to set dice image");

                switch (dice.roll()) {

                    case (2):
                        diceView.setImageResource(R.drawable.dice_2);
                        lastDiceValue = 2;
                        startCheatTimer();
                        break;
                    case (3):
                        diceView.setImageResource(R.drawable.dice_3);
                        lastDiceValue = 3;
                        startCheatTimer();
                        break;
                    case (4):
                        diceView.setImageResource(R.drawable.dice_4);
                        lastDiceValue = 4;
                        startCheatTimer();
                        break;
                    case (5):
                        diceView.setImageResource(R.drawable.dice_5);
                        lastDiceValue = 5;
                        startCheatTimer();
                        break;
                    case (6):
                        diceView.setImageResource(R.drawable.dice_6);
                        lastDiceValue = 6;
                        startCheatTimer();
                        break;
                    default:
                        diceView.setImageResource(R.drawable.dice_1);
                        lastDiceValue = 1;
                        startCheatTimer();
                        break;
                }

                database.collection(GAME_INFO)
                        .whereEqualTo(ROOM_NAME, room)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    document.getReference().update("DiceRoll", lastDiceValue);
                                }

                            } else {
                                Log.d(TAG, "Error getting Data from Firestore: ", task.getException());
                            }
                        });
            }
        }
    }

    //HELPER METHODS
    private void startCheatTimer() {
        new Thread(() -> {
            int diceValueBeforeStart = lastDiceValue;
            if (playerColor.equals(currentPlayerColor)) {
                SystemClock.sleep(3000);
                Player p = getPlayer(currentPlayerColor);

                if (p != null && !moved && lastDiceValue == diceValueBeforeStart) {
                    moved = true;

                    p.move(lastDiceValue);
                    playfield.getActionfield();
                }
            }
        }).start();
    }

    private Player getPlayer(PlayerColor playerColor) {
        if (playerColor.equals(PlayerColor.GREEN)) {
            return playfield.getPlayerGreen();
        }
        if (playerColor.equals(PlayerColor.RED)) {
            return playfield.getPlayerRed();
        }
        if (playerColor.equals(PlayerColor.BLUE)) {
            return playfield.getPlayerBlue();
        }
        if (playerColor.equals(PlayerColor.YELLOW)) {
            return playfield.getPlayerYellow();
        }

        return null;
    }

    private PlayerColor definePlayerColor(String playerColor) {
        switch (playerColor) {
            case "RED":
                return PlayerColor.RED;
            case "BLUE":
                return PlayerColor.BLUE;
            case "GREEN":
                return PlayerColor.GREEN;
            case "YELLOW":
                return PlayerColor.YELLOW;
            default:
                Log.e("ERROR", "Couldn't set Playercolor in DiceFragment");
                return null;
        }
    }

    private FirebaseFirestore createDBConnection() {
        return FirebaseFirestore.getInstance();
    }

    public void setDiceView(int diceValue) {
        switch (diceValue) {
            case (2):
                diceView.setImageResource(R.drawable.dice_2);
                break;
            case (3):
                diceView.setImageResource(R.drawable.dice_3);
                break;
            case (4):
                diceView.setImageResource(R.drawable.dice_4);
                break;
            case (5):
                diceView.setImageResource(R.drawable.dice_5);
                break;
            case (6):
                diceView.setImageResource(R.drawable.dice_6);
                break;
            default:
                diceView.setImageResource(R.drawable.dice_1);
                break;
        }
    }
}