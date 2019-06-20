package com.music.qichaoqun.music.bean;

public class NetMusicInfoMessage {
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
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

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public String getMusicLyric() {
        return musicLyric;
    }

    public NetMusicInfoMessage() {
    }

    public NetMusicInfoMessage(String image, String title, String singer, String musicPath, String musicLyric) {
        this.image = image;
        this.title = title;
        this.singer = singer;
        this.musicPath = musicPath;
        this.musicLyric = musicLyric;
    }

    public void setMusicLyric(String musicLyric) {
        this.musicLyric = musicLyric;
    }

    private String image;
    private String title;
    private String singer;
    private String musicPath;
    private String musicLyric;
}
