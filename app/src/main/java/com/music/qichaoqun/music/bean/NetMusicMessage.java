package com.music.qichaoqun.music.bean;

public class NetMusicMessage {
    public String getSongId() {
        return songId;
    }

    public NetMusicMessage() {
    }

    public NetMusicMessage(String songId) {
        this.songId = songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    private String songId;

}
