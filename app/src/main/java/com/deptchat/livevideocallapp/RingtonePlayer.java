package com.deptchat.livevideocallapp;

import android.content.Context;
import android.media.MediaPlayer;

public class RingtonePlayer {

    private MediaPlayer mediaPlayer;

    public void playRingtone(Context context, int rawResourceId) {
        stopRingtone(); // Stop any currently playing ringtone

        mediaPlayer = MediaPlayer.create(context, rawResourceId);

        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true); // Set true if you want to play the ringtone in a loop
            mediaPlayer.start();
            mediaPlayer.isPlaying();// Start playing the ringtone
        }
    }

    public void stopRingtone() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
