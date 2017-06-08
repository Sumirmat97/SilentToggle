package com.sumir.silentmodetoggle;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content);

        frameLayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                RingerHelper.performToggle(audioManager);
                updateUI();
            }
        });
    }

    private void updateUI()
    {
        ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
        int phoneImage = RingerHelper.isPhoneSilent(audioManager)?R.drawable.ringer_on:R.drawable.ringer_off;

        imageView.setImageResource(phoneImage);
    }

    @Override
    protected void onResume(){
        super.onResume();

        updateUI();
    }



}
