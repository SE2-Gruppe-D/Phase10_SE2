package com.example.phase10_se2.helper;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.phase10_se2.Playfield;

import java.util.Locale;

public class Timer {
    //Timer
    private static final long START_TIMER = 120000;
    private final TextView textView;
    Playfield playfield;
    private CountDownTimer timerturn;
    private long leftTime = START_TIMER;

    public Timer(TextView textView, CountDownTimer timerturn, long leftTime, Playfield playfield) {
        this.textView = textView;
        this.timerturn = timerturn;
        this.leftTime = leftTime;
        this.playfield = playfield;
    }

    public final void startTimer() {
        timerturn = new CountDownTimer(leftTime, 1000) {
            @Override
            public void onTick(long miliUntilFinish
            ) {
                leftTime = miliUntilFinish;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                playfield.setNextCurrentPlayer();
            }
        }.start();
    }

    public void restart() {
        timerturn.cancel();
        timerturn.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (leftTime / 1000) / 60;
        int seconds = (int) (leftTime / 1000) % 60;

        String timeText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textView.setText(timeText);
    }

}
