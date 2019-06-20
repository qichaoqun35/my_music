package com.music.qichaoqun.music.bean;

public class LocalMusicMessage {
    private int position;

    public LocalMusicMessage(int position) {
        this.position = position;
    }

    public LocalMusicMessage() {
    }

    public int getSongId() {
        return position;
    }

    public void setSongId(int songId) {
        this.position = songId;
    }
}
