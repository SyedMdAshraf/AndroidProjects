package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekBar;
    boolean counterIsActive=false;
    CountDownTimer countDownTimer;
    Button goButton;


    public void resetTimer(){
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO");
        counterIsActive=false;
    }

    public void startTimer(View view){
        if(counterIsActive){
            resetTimer();

        }else {

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP");
            MediaPlayer gPlayer = MediaPlayer.create(getApplicationContext(), R.raw.gunshot);
            gPlayer.start();
            gPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }

                ;
            });
            countDownTimer= new CountDownTimer(timerSeekBar.getProgress() *
                    1000 + 500, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft){
        int minute=secondsLeft/60;
        int seconds=secondsLeft-(minute*60);

        String secondsString=Integer.toString(seconds);
        if(seconds<=9){
            secondsString="0"+secondsString;
        }
        String minutesString;
        minutesString = Integer.toString(minute);
        if(minute<=9){
            minutesString="0"+minutesString;
        }
        timerTextView.setText(minutesString+":"+secondsString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar=findViewById(R.id.hornSeekBar);
        timerTextView=findViewById(R.id.countdownTextView);
        goButton=findViewById(R.id.buttonClick);
        //imageView=findViewById(R.id.eggImageView);

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
