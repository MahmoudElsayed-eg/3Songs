package com.example.a3songs;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
        ,SeekBar.OnSeekBarChangeListener{

    private SeekBar seekBar,seekBarVolume;
    private MediaPlayer aqua, afro, beyonce;
    private AudioManager audioManager;
    private Timer timer1,timer2,timer3;
    private String isPlaying,isClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btnAqua = findViewById(R.id.btnAqua);
        ImageButton btnAfro = findViewById(R.id.btnAfro);
        ImageButton btnBeyonce =findViewById(R.id.btnBeyonce);
        timer1 = new Timer();
        timer2 = new Timer();
        timer3 = new Timer();
        seekBar = findViewById(R.id.seekBar);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maximumVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBarVolume.setMax(maximumVolume);
        seekBarVolume.setProgress(currentVolume);
        isClicked = "";
        aqua = MediaPlayer.create(this,R.raw.aqua);
        afro = MediaPlayer.create(this,R.raw.afro);
        beyonce = MediaPlayer.create(this,R.raw.beyonce);
        btnAfro.setOnClickListener(this);
        btnAqua.setOnClickListener(this);
        btnBeyonce.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        seekBarVolume.setOnSeekBarChangeListener(this);
        afro.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                timer1.cancel();
            }
        });
        aqua.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                timer2.cancel();
            }
        });
        beyonce.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                timer3.cancel();
            }
        });
    }

    @Override
    public void onClick(View buttonView) {
        switch (buttonView.getId()) {
            case R.id.btnAfro :
                seekBar.setMax(afro.getDuration());
                isPlaying = "afro";
                if (isClicked.equals("aqua")) {
                    timer2.cancel();
                    aqua.stop();
                }else if (isClicked.equals("beyonce")) {
                    timer3.cancel();
                    beyonce.stop();
                }
                isClicked = "afro";
                afro.start();
                timer1.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        seekBar.setProgress(afro.getCurrentPosition());
                    }
                },0,1000);
                break;
            case R.id.btnAqua :
                seekBar.setMax(aqua.getDuration());
                isPlaying = "aqua";
                if (isClicked.equals("afro")) {
                    timer1.cancel();
                    afro.stop();
                }else if (isClicked.equals("beyonce")) {
                    timer3.cancel();
                    beyonce.stop();
                }
                aqua.start();
                isClicked = "aqua";
                timer2.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        seekBar.setProgress(aqua.getCurrentPosition());
                    }
                },0,1000);
                break;
            case R.id.btnBeyonce :
                seekBar.setMax(beyonce.getDuration());
                isPlaying = "beyonce";
                if (isClicked.equals("aqua")) {
                    timer2.cancel();
                    aqua.stop();
                }else if (isClicked.equals("afro")) {
                    timer1.cancel();
                    afro.stop();
                }
                beyonce.start();
                isClicked = "beyonce";
                timer3.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        seekBar.setProgress(beyonce.getCurrentPosition());
                    }
                },0,1000);
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBar :
                if (fromUser) {
                    if (isPlaying.equals("aqua")) {
                        aqua.seekTo(progress);
                    }else if (isPlaying.equals("afro")) {
                        afro.seekTo(progress);
                    }else if (isPlaying.equals("beyonce")) {
                        beyonce.seekTo(progress);
                    }
                }
                break;
            case R.id.seekBarVolume :
                if (fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                }
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.seekBar :
                if (isPlaying.equals("aqua")) {
                    aqua.pause();
                }else if (isPlaying.equals("afro")) {
                    afro.pause();
                }else if (isPlaying.equals("beyonce")) {
                    beyonce.pause();
                }
                break;
            case R.id.seekBarVolume :
                break;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.seekBar :
                if (isPlaying.equals("aqua")) {
                    aqua.start();
                }else if (isPlaying.equals("afro")) {
                    afro.start();
                }else if (isPlaying.equals("beyonce")) {
                    beyonce.start();
                }
                break;
            case R.id.seekBarVolume :
                break;
        }
    }

}
