package com.music.qichaoqun.music.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.TrafficStats;
import android.net.Uri;

import com.music.qichaoqun.music.BaseClass.ToBitmap;
import com.music.qichaoqun.music.R;
import com.music.qichaoqun.music.activity.PlayMusicActivity;
import com.music.qichaoqun.music.bean.NetMusic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class Utils {

    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private Context mContext = null;

    public Utils(Context context) {
        // 转换成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mContext = context;
    }

    /**
     * 把毫秒转换成：1:20:30这里形式
     *
     * @param timeMs
     * @return
     */
    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    /**
     * 判断是否是网络的资源
     * @param uri
     * @return
     */
    public boolean isNetUri(String uri) {
        boolean reault = false;
        if (uri != null) {
            if (uri.toLowerCase().startsWith("http") || uri.toLowerCase().startsWith("rtsp") || uri.toLowerCase().startsWith("mms")) {
                reault = true;
            }
        }
        return reault;
    }


    /**
     * 得到网络速度
     * 每隔两秒调用一次
     * @param context
     * @return
     */
    public String getNetSpeed(Context context) {
        String netSpeed = "0 kb/s";
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid)==TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB;
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        netSpeed  = String.valueOf(speed) + " kb/s";
        return  netSpeed;
    }

    /**
     * 格式化音乐的时间
     * @param time
     * @return
     */
    public String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    /**
     * 将网络图片转为bitmap
     * @param url
     * @param toBitmap
     */
    public void returnBitMap(final String url, final ToBitmap toBitmap){
        final Bitmap[] bitmap = {};
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;
                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    if(toBitmap != null){
                        toBitmap.getBitmap(BitmapFactory.decodeStream(is));
                    }
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 将数组转为bitmap
     * @param data
     * @return
     */
    public Bitmap byte2Bitmap(byte[] data) {
        if(data == null){
            data = bitmap2Bytes(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.load_faild, null));
        }
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * 将bitmap转为数组
     * @param bitmap
     * @return
     */
    public byte[] bitmap2Bytes(Bitmap bitmap) {
        if(bitmap != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        }
        return null;
    }

    /**
     * 获取当前歌曲的位置
     * @param currentMusicSongId
     */
    public int getCurrentMusicPosition(String[] songsId,String currentMusicSongId){
        if(songsId != null && songsId.length > 0){
            for (int i = 0; i < songsId.length; i++) {
                if(currentMusicSongId.equals(songsId[i])){
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * 截取歌曲的名称和歌手的名称
     * @param text
     * @return
     */
    public String splitText(String text){
        if(text.length() <= 10){
            return text;
        }else{
            return text.substring(0,11)+"...";
        }
    }

    /**
     * 设置音乐的专辑
     * @param album_id
     * @return
     */
    public Bitmap getAlbumArt(int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = mContext.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        Bitmap bm = null;
        if (album_art != null) {
            bm = BitmapFactory.decodeFile(album_art);
        } else {
            bm = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher_background);
        }
        return bm;
    }

    /**
     * 取出音乐集合中的所有的音乐的id
     */
    public String[] getSongsID(List<NetMusic.SongListBean> netMusics){
        String songsId[] = new String[netMusics.size()];
        //遍历集合取出songId
        for (int i = 0; i < netMusics.size(); i++) {
            songsId[i] = netMusics.get(i).getSong_id();
        }
        return songsId;
    }



}