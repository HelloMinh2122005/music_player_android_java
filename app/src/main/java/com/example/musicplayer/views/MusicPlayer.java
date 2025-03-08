package com.example.musicplayer.views;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.entities.MusicEntity;
import com.example.musicplayer.entities.MusicListEntity;
import com.example.musicplayer.services.MusicService;

public class MusicPlayer extends AppCompatActivity {
    private boolean serviceBound = false;
    private MusicService musicService;
    private SeekBar seekBar;
    private final Handler handler = new Handler();
    private Runnable updateSeekBar;
    private final MusicListEntity currentMusicList = new MusicListEntity();
    private TextView tvElapsedTime, tvTotalTime;
    private  MusicEntity musicItem;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            musicService = binder.getService();
            musicService.setCurrentMusic(musicItem);
            serviceBound = true;
            setupSeekBar();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_music_player);

        int currentPosition = 0;

        int play_init = getIntent().getIntExtra("play_init", 1);
        if (play_init == 3) {
            musicItem = (MusicEntity) getIntent().getSerializableExtra("musicEntity");
        } else if (play_init == 2) {
            musicItem = currentMusicList.getFirstMusic();
        } else {
            musicItem = (MusicEntity) getIntent().getSerializableExtra("musicEntity");
            currentPosition = getIntent().getIntExtra("currentPosition", 0);
        }

        TextView title = findViewById(R.id.tvSongName);
        TextView singer = findViewById(R.id.tvSingerName);
        ImageView albumImage = findViewById(R.id.image);

        assert musicItem != null;
        title.setText(musicItem.getTitle());
        singer.setText(musicItem.getArtist());
        albumImage.setImageResource(musicItem.getImageUrl());
        tvElapsedTime = findViewById(R.id.tvElapsedTime);
        tvTotalTime = findViewById(R.id.tvTotalTime);

        ImageButton btnBackToList = findViewById(R.id.btnBack);
        ImageButton btnBack = findViewById(R.id.btnBackMusic);
        ImageButton btnStop = findViewById(R.id.btnStopMusic);
        ImageButton btnNext = findViewById(R.id.btnNextMusic);
        ImageButton btnListBottom = findViewById(R.id.back_bottom);

        btnBackToList.setOnClickListener(v -> {
            Intent intent = new Intent(MusicPlayer.this, MusicDashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });

        btnListBottom.setOnClickListener(v -> {
            Intent intent = new Intent(MusicPlayer.this, MusicDashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });

        btnStop.setOnClickListener(v -> {
            if (musicService != null && musicService.getMediaPlayer() != null) {
                MediaPlayer mp = musicService.getMediaPlayer();
                if (mp.isPlaying()) {
                    mp.pause();
                    btnStop.setImageResource(R.drawable.play);
                } else {
                    mp.start();
                    btnStop.setImageResource(R.drawable.pause);
                }
            }
        });

        btnBack.setOnClickListener(v -> {
            if (musicService != null && musicService.getMediaPlayer() != null) {

                MusicEntity prevMusic = currentMusicList.getPreviousMusic(musicItem.getId());

                Intent intent = new Intent(MusicPlayer.this, MusicPlayer.class);
                intent.putExtra("musicEntity", prevMusic);
                intent.putExtra("play_init", 3);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (musicService != null && musicService.getMediaPlayer() != null) {

                MusicEntity nextMusic = currentMusicList.getNextMusic(musicItem.getId());

                Intent intent = new Intent(MusicPlayer.this, MusicPlayer.class);
                intent.putExtra("musicEntity", nextMusic);
                intent.putExtra("play_init", 3);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        Intent serviceIntent = new Intent(this, MusicService.class);
        serviceIntent.putExtra("audioResId", musicItem.getFileUrl());
        serviceIntent.putExtra("currentPosition", currentPosition);
        startService(serviceIntent);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);

        seekBar = findViewById(R.id.seekBar);
    }

    private void setupSeekBar() {
        if (musicService != null && musicService.getMediaPlayer() != null) {
            MediaPlayer mp = musicService.getMediaPlayer();
            tvTotalTime.setText(formatTime(mp.getDuration()));
            seekBar.setMax(mp.getDuration());

            updateSeekBar = new Runnable() {
                @Override
                public void run() {
                    if (mp.isPlaying()) {
                        int currentPos = mp.getCurrentPosition();
                        seekBar.setProgress(mp.getCurrentPosition());
                        tvElapsedTime.setText(formatTime(currentPos));
                        handler.postDelayed(this, 500);
                    }
                }
            };
            handler.postDelayed(updateSeekBar, 0);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mp.seekTo(progress);
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) { }
                @Override public void onStopTrackingTouch(SeekBar seekBar) { }
            });
        }
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(int millis) {
        int minutes = (millis / 1000) / 60;
        int seconds = (millis / 1000) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        if (handler != null && updateSeekBar != null) {
            handler.removeCallbacks(updateSeekBar);
        }
        if (serviceBound) {
            unbindService(serviceConnection);
            serviceBound = false;
        }
        super.onDestroy();
    }
}