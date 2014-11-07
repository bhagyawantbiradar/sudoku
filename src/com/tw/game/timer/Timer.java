package com.tw.game.timer;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

public class Timer {
    private Handler customHandler;
    private String text;

    public String getTimerValue() {
        return timerValue != null ? timerValue.getText().toString() : text;
    }

    private TextView timerValue;
    private long startTime;

    public Timer(TextView view) {
        timerValue = view;
    }

    public void start(long startTime) {
        this.startTime = startTime;
        customHandler = new Handler();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    public void stop() {
        if (timerValue != null){
            text = timerValue.getText().toString();
            timerValue = null;
        }
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            if (timerValue == null)
                Thread.currentThread().interrupt();
            else {
                long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                int secs = (int) (timeInMilliseconds / 1000);
                int mins = secs / 60;
                int hour = mins / 60;
                secs = secs % 60;
                mins = mins % 60;
                timerValue.setText(String.format("%02d", hour)
                        + ":" + String.format("%02d", mins) + ":"
                        + String.format("%02d", secs));
                customHandler.postDelayed(this, 0);
            }
        }
    };
}
