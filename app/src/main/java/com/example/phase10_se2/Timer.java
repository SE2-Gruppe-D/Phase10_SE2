package com.example.phase10_se2;

import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Timer {
    //Timer
    private static final long startTimer_inMiliS = 120000;
    private TextView timer;
    private CountDownTimer timerturn;
    private long leftTime= startTimer_inMiliS;

    public Timer(TextView timer, CountDownTimer timerturn, long leftTime) {
        this.timer = timer;
        this.timerturn = timerturn;
        this.leftTime = leftTime;
    }



    final void startTimer(){
        timerturn= new CountDownTimer(leftTime, 1000) {
            @Override
            public void onTick(long miliUntilFinish
            ) {
                leftTime= miliUntilFinish;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                this.start();
            }
        }.start();
    }

    void updateCountDownText(){
        int minutes= (int) (leftTime/1000)/60;
        int seconds= (int) (leftTime/1000)%60;

        String timeText= String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeText);
    }

}
