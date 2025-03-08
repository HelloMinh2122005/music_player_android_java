package com.example.musicplayer.views;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.os.IBinder;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.musicplayer.R;
import com.example.musicplayer.entities.MusicEntity;
import com.example.musicplayer.entities.MusicListEntity;
import com.example.musicplayer.helper.adapter.MusicAdapter;
import com.example.musicplayer.services.MusicService;

import java.util.ArrayList;

public class MusicDashboardActivity extends AppCompatActivity {

    private MusicService musicService;
    private boolean serviceBound = false;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            musicService = binder.getService();
            serviceBound = true;
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
        setContentView(R.layout.activity_music_dashboard);

        MusicListEntity listEntity = new MusicListEntity();
        final ArrayList<MusicEntity> arrayList = listEntity.getMusicList();

        MusicAdapter adapter = new MusicAdapter(this, arrayList);
        ListView listView = findViewById(R.id.row3);
        listView.setAdapter(adapter);

        ImageButton btnViewMusic = findViewById(R.id.row5_col2);
        btnViewMusic.setOnClickListener(view -> {
            Intent intent = new Intent(MusicDashboardActivity.this, MusicPlayer.class);
            startActivity(intent);
            if (musicService != null && musicService.getMediaPlayer() != null && musicService.getMediaPlayer().isPlaying()) {
                intent.putExtra("play_init", 1);
            } else {
                intent.putExtra("play_init", 2);
            }
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        });

    }
}