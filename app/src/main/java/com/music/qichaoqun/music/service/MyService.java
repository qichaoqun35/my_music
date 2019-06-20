package com.music.qichaoqun.music.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import com.music.qichaoqun.music.Contants.MyContants;
import com.music.qichaoqun.music.application.MyApplication;
import com.music.qichaoqun.music.bean.LastMusicMessage;
import com.music.qichaoqun.music.bean.LoadSuccessMessage;
import com.music.qichaoqun.music.bean.LocalMusic;
import com.music.qichaoqun.music.bean.LocalMusicMessage;
import com.music.qichaoqun.music.bean.MusicErrorMessage;
import com.music.qichaoqun.music.bean.MusicNextPlayMessage;
import com.music.qichaoqun.music.bean.MusicUpdateMessage;
import com.music.qichaoqun.music.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyService extends Service {

    private MediaPlayer mMediaPlayer;
    private Intent mIntent;
    public static ArrayList<LocalMusic> mLocalMusics = null;
    private int mPosition;
    private int mType;
    private String mPath;
    private int mNetMusicPosition;
    public static int isFirstPlay = 0;
    private Utils mUtils;
    private MyApplication mApplication;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建通用的Intent
        mIntent = new Intent();
        mUtils = new Utils(this);
        //加载音乐
        new MyAnsyTask().execute();
        mMediaPlayer = new MediaPlayer();
        mApplication = (MyApplication) getApplication();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    public class MyBinder extends Binder implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
        public MyService getService() {
            return MyService.this;
        }


        /**
         * 开启音乐的播放
         */
        public void play(String musicPath) {
            try {
                if (mMediaPlayer != null) {
                    mMediaPlayer.reset();
                }
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setOnPreparedListener(this);
                mMediaPlayer.setOnErrorListener(this);
                mMediaPlayer.setOnCompletionListener(this);
                mMediaPlayer.setDataSource(musicPath);
                mMediaPlayer.prepare();
                isFirstPlay = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 暂停音乐的播放
         */
        public void pause() {
            mMediaPlayer.pause();
        }


        /**
         * 获取播放器的当前的状态
         */
        public boolean isPlaying() {
            if (mMediaPlayer == null) {
                return false;
            }
            return mMediaPlayer.isPlaying();
        }

        /**
         * 判断当前的播放器中是否有歌曲
         *
         * @return
         */
        public boolean isNullMedia() {
            if (mMediaPlayer == null) {
                return true;
            }
            return false;
        }

        /**
         * 在暂停的位置重新开始播放
         */
        public void reStart() {
            mMediaPlayer.start();
        }


        /**
         * 音乐播放器准备好开始播放
         * @param mp
         */
        @Override
        public void onPrepared(MediaPlayer mp) {
            mMediaPlayer.start();
            EventBus.getDefault().post(new MusicUpdateMessage());
        }

        /**
         * 音乐播放器播放失败
         * @param mp
         * @param what
         * @param extra
         * @return
         */
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            mMediaPlayer.pause();
            //发送广播更换播放开关的图片
            EventBus.getDefault().post(new MusicErrorMessage());
            return false;
        }

        /**
         * 音乐播放器完成一首歌曲的播放
         * @param mp
         */
        @Override
        public void onCompletion(MediaPlayer mp) {
            //自动播放下一首
            nextMusic();
            //更新界面
            EventBus.getDefault().post(new MusicUpdateMessage());
        }

        /**
         * 设置当前播放的音乐类型
         */
        public void setMusicType(int type){
            mType = type;
        }

        /**
         * 获取当前音乐播放的类型
         */
        public int getMusicType(){
            return mType;
        }

        /**
         * 设置当前播放的位置
         */
        public void setPosition(int position){
            mPosition = position;
        }

        /**
         * 获取当前音乐播放的位置
         */
        public int getPosition(){
            return mPosition;
        }

        /**
         * 设置当前播放的网络音乐的路径
         */
        public void setMusicPath(String path){
            mPath = path;
        }

        /**
         * 获取当前网络音乐播放的路径
         */
        public String getMusicPath(){
            return mPath;
        }

        public void setNetPosition(int netPosition){
            mNetMusicPosition = netPosition;
        }
        public int getNetPosition(){
            return mNetMusicPosition;
        }

        /**
         * 上一首
         */
        public void lastMusic(){
            EventBus.getDefault().post(new LastMusicMessage());
        }

        /**
         * 下一首
         */
        public void nextMusic(){
            EventBus.getDefault().post(new MusicNextPlayMessage());
        }


        /**
         * 用于音乐的播放
         */
        public void play(){
            if(mType == 0){
                //本地音乐
                if(!isEmptyMusic()){
                    //不为空
                    play(mLocalMusics.get(mPosition).getData());
                }else{
                    //为空，发送广播告诉前台数据为空
                    EventBus.getDefault().post(new MusicErrorMessage());
                }
            }else{
                //网络音乐
                play(mPath);
            }
        }

        /**
         * 将音乐滑动到相关的播放位置
         */
        public void setMusicSeek(int position){
            if(!isNullMedia()){
                mMediaPlayer.seekTo(position);
            }
        }

        /**
         * 音乐的总的时常
         * @return
         */
        public Integer getDuration(){
            if(!isNullMedia()){
                return mMediaPlayer.getDuration();
            }
            return null;
        }

        /**
         * 获取音乐当前的播放的进度
         */
        public int getProgress(){
            return mMediaPlayer.getCurrentPosition();
        }

        /**
         * 为初始化binder设置
         */
        public void setData(String path){
            try {
                mMediaPlayer.setDataSource(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 判断本地音乐的集合是否为空
     */
    public boolean isEmptyMusic(){
        if(mLocalMusics != null && mLocalMusics.size() >0){
            return false;
        }
        return true;
    }

    /**
     * 异步获取音乐列表
     */
    public class MyAnsyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            mLocalMusics = new ArrayList<>();
            ContentResolver contentResolver = getContentResolver();
            Cursor mCursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Audio.Media._ID,
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.DURATION,
                            MediaStore.Audio.Media.SIZE,
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.ALBUM_ID}, null, null, null);
            while (mCursor.moveToNext()) {
                LocalMusic localMusic = new LocalMusic();
                localMusic.setMusicTitle(mCursor.getString(1));
                localMusic.setMusicId(mCursor.getLong(0));
                localMusic.setMusicAlbum(mCursor.getString(2));
                localMusic.setArtist(mCursor.getString(4));
                localMusic.setSize(mCursor.getLong(6));
                localMusic.setMusicDisplayName(mCursor.getString(3));
                localMusic.setDuration(mCursor.getLong(5));
                localMusic.setData(mCursor.getString(7));
                localMusic.setBitmap(mUtils.getAlbumArt(mCursor.getInt(8)));
                mLocalMusics.add(localMusic);
            }
            mCursor.close();
            return "succeed";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //通知前台数据加载已经完成，可以前往主页面
            EventBus.getDefault().post(new LoadSuccessMessage());
        }
    }

}
