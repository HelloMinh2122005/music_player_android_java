package com.example.musicplayer.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.musicplayer.entities.MusicEntity;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    private final IBinder binder = new LocalBinder();
    private MusicEntity currentMusic;

    public class LocalBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int audioResId = intent.getIntExtra("audioResId", -1);
        int currentPosition = intent.getIntExtra("currentPosition", 0);

        if (audioResId != -1) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.reset();
            }

            mediaPlayer = MediaPlayer.create(this, audioResId);
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    public MusicEntity getCurrentMusic() {
        return currentMusic;
    }

    public void setCurrentMusic(MusicEntity currentMusic) {
        this.currentMusic = currentMusic;
    }
}