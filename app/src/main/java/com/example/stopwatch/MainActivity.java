package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private View stopwatchHand;
    private boolean isRunning = false;
    private int degrees = 0;
    private int seconds = 0;

    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            degrees += 6; // Increment by 6 degrees every second
            seconds++;
            rotateStopwatchHand(degrees);
            updateTimerText(seconds);
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton startButton = findViewById(R.id.start_button);
        ImageButton stopButton = findViewById(R.id.stop_button);
        ImageButton resetButton = findViewById(R.id.reset_button);
        stopwatchHand = findViewById(R.id.stopwatch_hand);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startAnimation();
                    handler.postDelayed(runnable, 1000);
                    isRunning = true;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    handler.removeCallbacks(runnable);
                    isRunning = false;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    degrees = 0;
                    seconds = 0;
                    rotateStopwatchHand(degrees);
                    updateTimerText(seconds);
                }
            }
        });
    }

    private void rotateStopwatchHand(float degree) {
        Animation animation = new RotateAnimation(
                degrees - 6, degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(1000);
        animation.setFillAfter(true);
        stopwatchHand.startAnimation(animation);
    }

    private void startAnimation() {
        handler.postDelayed(runnable, 1000);
    }

    private void updateTimerText(int seconds) {
        TextView timerText = findViewById(R.id.timer_text);
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        timerText.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
    }
}
