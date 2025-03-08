package com.example.musicplayer.entities;

import com.example.musicplayer.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicListEntity implements Serializable {
    private ArrayList<MusicEntity> musicList = new ArrayList<>();
    public MusicListEntity() {
        this.musicList.add(new MusicEntity("1", "Attention", "Charlie Puth",
                R.raw.attention, R.drawable.attention_pic));

        this.musicList.add(new MusicEntity("2", "Fire", "Alan Walker",
                R.raw.fire, R.drawable.fire_pic));

        this.musicList.add(new MusicEntity("3", "Galway Girl", "Ed Sheeran",
                R.raw.galway_girl, R.drawable.galway_girl_pic));

        this.musicList.add(new MusicEntity("4", "Ghé qua", "Dick, Tofu, PC",
                R.raw.ghe_qua, R.drawable.ghequa));

        this.musicList.add(new MusicEntity("5", "Hay trao cho anh", "Sơn Tùng M-TP",
                R.raw.hay_trao_cho_anh, R.drawable.haytraochoanh));

        // Bạn có thể sửa tên bài hoặc tên ca sĩ tùy thuộc vào bài hát thực tế
        this.musicList.add(new MusicEntity("6", "Mất kết nối", "Dương Domic",
                R.raw.mat_ket_noi, R.drawable.matketnoi));

        this.musicList.add(new MusicEntity("7", "Shivers", "Ed Sheeran",
                R.raw.shivers, R.drawable.shivers));

        this.musicList.add(new MusicEntity("8", "Sugar", "Maroon 5",
                R.raw.sugar, R.drawable.sugar));

        this.musicList.add(new MusicEntity("9", "Sweet but psycho", "Ava Max",
                R.raw.sweet_but_pschyco, R.drawable.sweetbutpshycho));

        this.musicList.add(new MusicEntity("10", "The lazy song", "Bruno Mars",
                R.raw.the_lazy_song, R.drawable.thelazysong));

        this.musicList.add(new MusicEntity("11", "Too sweet", "Hozier",
                R.raw.too_sweet, R.drawable.too_sweet_pic));

        // Bạn có thể sửa tên bài hoặc tên ca sĩ tùy thuộc vào bài hát thực tế
        this.musicList.add(new MusicEntity("12", "Tung la", "Vũ Cát Tường",
                R.raw.tung_la, R.drawable.tungla));

        this.musicList.add(new MusicEntity("13", "Viva la vida", "Coldplay",
                R.raw.viva_la_vida, R.drawable.vivalavida));

        this.musicList.add(new MusicEntity("14", "Wake me up", "Avicii",
                R.raw.wake_me_up, R.drawable.wakemeup));

        this.musicList.add(new MusicEntity("15", "We Don't Talk Anymore", "Charlie Puth, Selena Gomez",
                R.raw.we_dont_talk_anymore, R.drawable.we_dont_talk_anymore));

        this.musicList.add(new MusicEntity("16", "What makes you beautiful", "One Direction",
                R.raw.what_makes_you_beautiful, R.drawable.wmyb));

        this.musicList.add(new MusicEntity("17", "Zoom", "Jessi",
                R.raw.zoom, R.drawable.zoom_jessi));
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
        if (currentIndex < musicList.size() - 1) {
            return musicList.get(currentIndex + 1);
        } else {
            return musicList.get(0);
        }
    }

    public MusicEntity getFirstMusic() {
        return musicList.get(0);
    }
}
