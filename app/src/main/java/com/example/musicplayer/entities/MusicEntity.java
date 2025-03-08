package com.example.musicplayer.entities;

import android.net.Uri;

import java.io.Serializable;

public class MusicEntity implements Serializable {
    private String id;
    private  String title;
    private  String artist;
    private  int fileUrl;
    private int imageUrl;

    public MusicEntity(String id, String title, String artist, int fileUrl, int imageUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.fileUrl = fileUrl;
        this.imageUrl = imageUrl;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(int fileUrl) {
        this.fileUrl = fileUrl;
    }
}
