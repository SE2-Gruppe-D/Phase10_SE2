package com.example.phase10_se2;

import static com.example.phase10_se2.Playfield.playfieldContext;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.ImageView;

public class AccelerationSensor implements SensorEventListener {
    float acceleration;
    int lastDiceValue;
    Sensor accelerometer;
    DiceFragment diceFragment;
    SensorManager sensorManager;
    Dice dice;

    public AccelerationSensor(DiceFragment diceFragment) {
        dice = new Dice();

        this.diceFragment = diceFragment;
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    private void initAccelerometer() {
        if (sensorManager == null) {
            sensorManager = (SensorManager) playfieldContext.getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) { //if an accelerator got created
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }

    public void register() {
        if (sensorManager != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        } else {
            initAccelerometer();
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

        acceleration = (float) (Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH);

        if (acceleration > 1) {
            Log.i("TEST", "sensor has been activated. Trying to set dice image");
            Log.i("TEST", "dice: " + dice.roll());
            Log.i("TEST", "view: " + (diceFragment.diceView == null));

            if (diceFragment.diceView != null && dice != null) {

                switch (dice.roll()) {
                    case (2):
                        diceFragment.diceView.setImageResource(R.drawable.dice_2);
                        this.lastDiceValue = 2;
                        break;
                    case (3):
                        diceFragment.diceView.setImageResource(R.drawable.dice_3);
                        this.lastDiceValue = 3;
                        break;
                    case (4):
                        diceFragment.diceView.setImageResource(R.drawable.dice_4);
                        this.lastDiceValue = 4;
                        break;
                    case (5):
                        diceFragment.diceView.setImageResource(R.drawable.dice_5);
                        this.lastDiceValue = 5;
                        break;
                    case (6):
                        diceFragment.diceView.setImageResource(R.drawable.dice_6);
                        this.lastDiceValue = 6;
                        break;
                    default:
                        diceFragment.diceView.setImageResource(R.drawable.dice_1);
                        this.lastDiceValue = 1;
                        break;
                }
            }

//            Log.i("TEST", "aWRWQRT: " + lastDiceValue + "   " + this.getId());
        }
    }
}
