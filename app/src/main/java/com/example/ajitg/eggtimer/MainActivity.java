package com.example.ajitg.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar ;
    TextView timerTextView;
    Button controllerButton;
    CountDownTimer countDownTimer;
    boolean timerIsActive = true ;
    public void setDefault(){
        timerTextView.setText("0:30s");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        timerIsActive = true;
    }
    public void updateTimer(int leftSeconds){
        int minutes = (int)(leftSeconds/60);
        Log.i("Minutes" , Integer.toString(minutes));
        int seconds = leftSeconds - minutes*60;
        String second = Integer.toString(seconds) + "s";
        if(seconds<=9){
            second = "0" + second;
            timerTextView.setText(Integer.toString(minutes) + ":" + second );
        }else{
            timerTextView.setText(Integer.toString(minutes) + ":" + second );
        }
    }
    public void timerStart(View view){
        if(timerIsActive) {
            controllerButton.setText("Stop!");
            timerIsActive = false;
            timerSeekBar.setEnabled(false);
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                    mPlayer.start();
                    setDefault();

                }
            }.start();
        } else {
            controllerButton.setText("Go!");
            countDownTimer.cancel();
            setDefault();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
