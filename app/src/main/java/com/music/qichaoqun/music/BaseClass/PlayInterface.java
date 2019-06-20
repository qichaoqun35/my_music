package com.music.qichaoqun.music.BaseClass;

public interface PlayInterface {
    /**
     * 播放开始更新界面
     */
    void startPlay();
    /**
     * 播放出错，更新界面
     */
    void palyError();
    /**
     * 播放完成
     */
    void playComplete();

}
