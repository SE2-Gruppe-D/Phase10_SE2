package com.example.phase10_se2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



public class DiceFragment extends Fragment implements SensorEventListener{

    private float shakeThreshold;  //Threshold for the acceleration sensor to trigger dice generation
    private ImageView diceView;
    private Dice dice;
    private SensorManager sensorManager;
    private Sensor accelerometer;


    public static DiceFragment newInstance() {
        return new DiceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //onPause() unregister the accelerometer for stop listening the events
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void initViews() {
        this.diceView = (ImageView) getView().findViewById(R.id.DiceView);
    }

    private void initAccelerometer() {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) { //if an accelerator got created
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            shakeThreshold = accelerometer.getMaximumRange() / 10;
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
                default:
                    diceView.setImageResource(R.drawable.dice_1);
                    break;
            }
        }
    }
}