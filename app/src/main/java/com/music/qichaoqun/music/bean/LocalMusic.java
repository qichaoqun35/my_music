package com.music.qichaoqun.music.bean;

import android.graphics.Bitmap;

/**
 * @author qichaoqun
 * @date 2019/3/11
 */
public class LocalMusic {
    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public String getMusicAlbum() {
        return musicAlbum;
    }

    public void setMusicAlbum(String musicAlbum) {
        this.musicAlbum = musicAlbum;
    }

    public String getMusicDisplayName() {
        return musicDisplayName;
    }

    public void setMusicDisplayName(String musicDisplayName) {
        this.musicDisplayName = musicDisplayName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return Duration;
    }

    public void setDuration(long duration) {
        Duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    private long musicId;
    private String musicTitle;
    private String musicAlbum;
    private String musicDisplayName;
    private String artist;
    private long Duration;
    private long size;
    private String data;
    private int albumId;
    private Bitmap mBitmap;

}
