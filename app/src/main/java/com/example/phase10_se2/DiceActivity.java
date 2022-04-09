package com.example.phase10_se2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

public class DiceActivity extends AppCompatActivity implements SensorEventListener {
    private float shakeThreshold;  //Threshold for the acceleration sensor to trigger dice generation
    private TextView diceValue;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        initViews();
        initAccelerometer();
    }

    //onResume() register the accelerometer for listening the events
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void initViews() {
        this.diceValue = findViewById(R.id.textView);
    }

    private void initAccelerometer() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) { //if an accelerator got created
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            shakeThreshold = accelerometer.getMaximumRange() / 2;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { //needs to be implemented, but does nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("DiceActivity", "sensor has been activated");

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float acceleration = (float) (Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH);

        if (acceleration > shakeThreshold) {
            generateRandomDiceValue();
        }
    }


    private void generateRandomDiceValue() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis()); //to assure randomness
        int randomValue = random.nextInt(6) + 1;

        Log.i("Value", String.valueOf(randomValue));

        //TODO: only set new value, if none has been assigned yet
        if (true) {
            diceValue.setText(String.valueOf(randomValue));
            Log.i("DiceActivity", "dice value set");
        }
    }
}