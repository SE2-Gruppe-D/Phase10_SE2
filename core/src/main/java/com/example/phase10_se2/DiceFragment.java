package com.example.phase10_se2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Locale;
import java.util.Objects;


public class DiceFragment extends Fragment implements SensorEventListener {
    private final boolean TESTMODE = true; //TODO: remove or set to false when multiplayer is implemented

    private float shakeThreshold;  //Threshold for the acceleration sensor to trigger dice generation
    private ImageView diceView;
    private Dice dice;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private int lastDiceValue;
    private float acceleration;
    private Handler handler;

    private PlayerColor playerColor;
    private String room;
    private FirebaseFirestore database;


    public static DiceFragment newInstance() {
        return new DiceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Playfield playfield = (Playfield) getActivity();

        room = playfield.getCurrentRoom();
        playerColor = definePlayerColor(playfield.getUserColor());
        database = createDBConnection();

//        getCurrentPlayerFromDatabase();

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

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    private void initViews() {
        this.diceView = (ImageView) getView().findViewById(R.id.DiceView);
    }

    private void initAccelerometer() {
        if (sensorManager == null) {
            sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) { //if an accelerator got created
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                shakeThreshold = accelerometer.getMaximumRange() / 10;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { //needs to be implemented, but does nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (TESTMODE || getCurrentPlayerFromDatabase().equals(playerColor)) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acceleration = (float) (Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH);

            if (acceleration > shakeThreshold) {
                Log.i("TEST", "dicefragment " + getAcceleration() + " " + acceleration);
                Log.i("DiceActivity", "sensor has been activated. Trying to set dice image");

                switch (dice.roll()) {
                    case (2):
                        diceView.setImageResource(R.drawable.dice_2);
                        lastDiceValue = 2;
                        sendMessage(acceleration);
                        break;
                    case (3):
                        diceView.setImageResource(R.drawable.dice_3);
                        lastDiceValue = 3;
                        sendMessage(acceleration);
                        break;
                    case (4):
                        diceView.setImageResource(R.drawable.dice_4);
                        lastDiceValue = 4;
                        sendMessage(acceleration);
                        break;
                    case (5):
                        diceView.setImageResource(R.drawable.dice_5);
                        lastDiceValue = 5;
                        sendMessage(acceleration);
                        break;
                    case (6):
                        diceView.setImageResource(R.drawable.dice_6);
                        lastDiceValue = 6;
                        sendMessage(acceleration);
                        break;
                    default:
                        diceView.setImageResource(R.drawable.dice_1);
                        lastDiceValue = 1;
                        sendMessage(acceleration);
                        break;
                }
            }
        }
    }

    //HELPER METHODS
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

    private PlayerColor getCurrentPlayerFromDatabase() {
        String plCol = null;
        //TODO: IMPLEMENT DB CALL FOR CURRENT PLAYER;

        return definePlayerColor(plCol);
    }

    private void pushDiceValueToDB(int diceValue) {
        //TODO: PUSH DICE VALUE TO DB FOR PLAYER
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

    private void sendMessage(float acceleration) {
        if (handler == null) {
            Log.d("DiceFragment", "Handler is null");
        } else {
            Message message = handler.obtainMessage();
            handler.obtainMessage(); //get null message
            Bundle bundle = new Bundle();
            bundle.putString("acceleration", String.valueOf(acceleration));
            bundle.putString("lastDiceValue", String.valueOf(lastDiceValue));
            message.setData(bundle);
            Log.i("TEST", "Send acceleration: " + acceleration + " from DiceFragment to Handler");
            handler.sendMessage(message);
        }
    }


    //GETTER AND SETTER
    public int getLastDiceValue() {
        return lastDiceValue;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}