package io.github.controlwear.joystickdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.Toast;


public class app_settings extends MenuActivity {

    private AudioManager audioManager = null;
    private AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.app_settings);
        initControls();
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

    }


    private void initControls() {
        try {
            SeekBar volumeSeekbar = (SeekBar) findViewById(R.id.seekBar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BackToMenu(View view) {
        Intent in = new Intent(app_settings.this, MenuActivity.class);
        startActivity(in);
    }


    public void soundswitch(View view) {
        Switch switch1 = (Switch) findViewById(R.id.sound_switch);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    am.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    am.setRingerMode(2);
                    Toast.makeText(getBaseContext(),"UnMuted",Toast.LENGTH_SHORT).show();
                }
                else{
                    am.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    am.setRingerMode(1);
                    Toast.makeText(getBaseContext(),"Muted",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}