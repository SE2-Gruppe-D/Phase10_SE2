package com.example.phase10_se2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class DiceActivity extends AppCompatActivity implements SensorEventListener {
    private float shakeThreshold;  //Threshold for the acceleration sensor to trigger dice generation
    private ImageView diceView;
    private Dice dice;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        dice = new Dice();
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
        this.diceView = findViewById(R.id.imageView);
    }

    private void initAccelerometer() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) { //if an accelerator got created
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            shakeThreshold = accelerometer.getMaximumRange() / 3;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { //needs to be implemented, but does nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float acceleration = (float) (Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH);

        if (acceleration > shakeThreshold) {
            Log.i("DiceActivity", "sensor has been activated. Trying to set dice image");

            switch(dice.roll()) {
                case(1):
                    diceView.setImageResource(R.drawable.dice_1);
                    break;
                case(2):
                    diceView.setImageResource(R.drawable.dice_2);
                    break;
                case(3):
                    diceView.setImageResource(R.drawable.dice_3);
                    break;
                case(4):
                    diceView.setImageResource(R.drawable.dice_4);
                    break;
                case(5):
                    diceView.setImageResource(R.drawable.dice_5);
                    break;
                case(6):
                    diceView.setImageResource(R.drawable.dice_6);
                    break;
            }
        }
    }
}