package com.deptchat.livevideocallapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class callring extends AppCompatActivity {
    TextView name;
    FrameLayout acceptcal;
    ImageView rejectcall;
    private CircleImageView circularImageView;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callring);
        circularImageView = findViewById(R.id.profile_image);
        name = findViewById(R.id.nametxt);
//        acceptcal = findViewById(R.id.accept);
        rejectcall = findViewById(R.id.reject);
        // Initialize MediaPlayer and Vibrator
        mediaPlayer = MediaPlayer.create(this, R.raw.simple);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Start playing background sound
        startBackgroundSound();

        // Vibrate for 500 milliseconds (adjust duration as needed)
        vibrate(5000);
        String imageUrl = getIntent().getStringExtra("img");
        name.setText(getIntent().getStringExtra("name"));
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.g1)
                .error(R.drawable.g1)
                .into(circularImageView);

        rejectcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(callring.this, details_activity.class);
                startActivity(intent);
                finish();
            }
        });

        acceptcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(callring.this, ConnectionVideoActivity.class);
                intent.putExtra("video", getIntent().getStringExtra("video"));

//        new intersital(callring.this).Show_Ads();

            }
        });
    }

    private void startBackgroundSound() {
        // Check if mediaPlayer is null or not initialized
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.simple);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } else if (!mediaPlayer.isPlaying()) {
            // If mediaPlayer is not null but not playing, start it again
            mediaPlayer.start();
        }
    }


    private void vibrate(long duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            // Deprecated in API 26
            vibrator.vibrate(duration);
        }
    }

    @Override
    protected void onDestroy() {
        // Release resources when the activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundSound();

        vibrate(5000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            stopBackgroundSound();
        }
        stopVibration();
    }

    private void stopBackgroundSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void stopVibration() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(callring.this, details_activity.class);
        startActivity(intent);
        finish();
    }
}