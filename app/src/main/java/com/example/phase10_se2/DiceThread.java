package com.example.phase10_se2;

import static android.os.SystemClock.sleep;

import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;


public class DiceThread extends Thread {

    // Reference to service's handler.
    protected Handler handler;
    protected AccelerationSensor accelerationSensor;
    private SensorManager sensorManager;

    public DiceThread(Handler handler, AccelerationSensor accelerationSensor) {
        super(DiceThread.class.getName());


        // Saving injected reference.
        this.handler = handler;
        this.accelerationSensor = accelerationSensor;
    }

    @Override
    public void run() {
        super.run();
        Log.i("TEST", "Thread started");

        int lastDiceValue = 1;
        accelerationSensor.register();

        Log.i("TEST", "started");

        while (accelerationSensor.acceleration < 1 && accelerationSensor.acceleration > -1) { //maybe replace with threshold
//            Log.i("TEST", "nothing happening " + diceFragment.getAcceleration());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.i("TEST", "skipped");

        while (accelerationSensor.acceleration > 1 || accelerationSensor.acceleration < -1) { //maybe replace with threshold
            lastDiceValue = accelerationSensor.lastDiceValue;

            Log.i("TEST", "P: " + accelerationSensor.acceleration);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("TEST", "D: " + accelerationSensor.acceleration);

            int timeSpent = 0;
            int sleepDurationInMs = 10;
            while (accelerationSensor.acceleration < 1 && accelerationSensor.acceleration > -1 && timeSpent < 3000) {
                try {
                    Thread.sleep(sleepDurationInMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timeSpent += sleepDurationInMs;

                Log.i("TEST", "asdasd: " + String.valueOf(timeSpent));
            }
            accelerationSensor.unregister();
        }

        Log.i("TEST", "rip?");
        //send dice value to handler
        Message message = this.handler.obtainMessage();
        message.obj = lastDiceValue;
        Log.i("TEST", String.valueOf(lastDiceValue));
        this.handler.sendMessage(message);
    }
}
