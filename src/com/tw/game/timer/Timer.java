package com.tw.game.timer;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

public class Timer {
    private Handler customHandler;
    private TextView timerValue;
    private long startTime;

    public Timer(TextView view) {
        timerValue = view;
    }

    public void start(long startTime){
        this.startTime = startTime;
        customHandler = new Handler();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            int hour = mins / 60;
            secs = secs % 60;

            timerValue.setText(String.format("%02d", hour)
                    + ":" + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));

            customHandler.postDelayed(this, 0);
        }
    };
}
