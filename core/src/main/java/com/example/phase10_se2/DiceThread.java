package com.example.phase10_se2;

import static android.os.SystemClock.sleep;

import android.annotation.SuppressLint;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;


public class DiceThread extends Thread {

    // Reference to service's handler.
    protected Handler playfieldHandler;
    protected Handler diceFragmentHandler;
    private DiceFragment diceFragment;

    public DiceThread(Handler handler, DiceFragment diceFragment) {
        super(DiceThread.class.getName());


        // Saving injected reference.
        this.playfieldHandler = handler;
        this.diceFragment = diceFragment;
    }

    @Override
    public void run() {
        //Looper
        Looper.prepare();

        diceFragmentHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            public void handleMessage(Message msg) {
                float acceleration = Float.parseFloat(msg.getData().getString("acceleration"));
                int lastDiceValue = Integer.parseInt(msg.getData().getString("lastDiceValue"));

                if (acceleration > 1 || acceleration < -1) {
                    Message message = playfieldHandler.obtainMessage();
                    message.obj = lastDiceValue;
                    Log.i("TEST", String.valueOf(lastDiceValue));
                    playfieldHandler.sendMessage(message);
                }
            }

        };
        diceFragment.setHandler(diceFragmentHandler);

        Looper.loop();
    }
}