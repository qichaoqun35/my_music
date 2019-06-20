package com.music.qichaoqun.music.bean;

public class LocalMusicInfo {
    public String getTitle() {
        return title;
    }

    public LocalMusicInfo() {
    }

    public LocalMusicInfo(String title, String singer, byte[] image) {
        this.title = title;
        this.singer = singer;
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private String title;
    private String singer;
    private byte[] image;
}
