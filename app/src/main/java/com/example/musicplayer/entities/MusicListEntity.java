package com.example.musicplayer.entities;

import com.example.musicplayer.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicListEntity implements Serializable {
    private ArrayList<MusicEntity> musicList = new ArrayList<>();
    public MusicListEntity() {
        this.musicList.add(new MusicEntity("1", "Attention", "Charlie Puth", R.raw.attention, R.drawable.attention_pic));
        this.musicList.add(new MusicEntity("2", "Fire", "Alan Walker", R.raw.fire, R.drawable.fire_pic));
        this.musicList.add(new MusicEntity("3", "Galway Girl", "Ed Sheeran", R.raw.galway_girl, R.drawable.galway_girl_pic));
        this.musicList.add(new MusicEntity("4", "Too sweet", "Hozier", R.raw.too_sweet, R.drawable.too_sweet_pic));
        this.musicList.add(new MusicEntity("5", "We Don't Talk Anymore", "Charlie Puth, Selene Gomez", R.raw.we_dont_talk_anymore, R.drawable.we_dont_talk_anymore));
        this.musicList.add(new MusicEntity("6", "What makes you beautiful", "One Direction", R.raw.what_makes_you_beautiful, R.drawable.wmyb));
        this.musicList.add(new MusicEntity("7", "Zoom", "Jessi", R.raw.zoom, R.drawable.zoom_jessi));
    }

    public ArrayList<MusicEntity> getMusicList() {
        return musicList;
    }

    public MusicEntity getPreviousMusic(String currentId) {
        int currentIndex = -1;
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getId().equals(currentId)) {
                currentIndex = i;
                break;
            }
        }
        if (currentIndex > 0) {
            return musicList.get(currentIndex - 1);
        } else {
            return musicList.get(6);
        }
    }

    public MusicEntity getNextMusic(String currentId) {
        int currentIndex = -1;
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getId().equals(currentId)) {
                currentIndex = i;
                break;
            }
        }
        if (currentIndex < 6) {
            return musicList.get(currentIndex + 1);
        } else {
            return musicList.get(0);
        }
    }

    public MusicEntity getFirstMusic() {
        return musicList.get(0);
    }
}
