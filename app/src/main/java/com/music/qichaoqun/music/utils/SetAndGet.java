package com.music.qichaoqun.music.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SetAndGet {

    private Context mContext = null;
    private final SharedPreferences mSharedPreferences;
    private final SharedPreferences.Editor mEditor;

    public SetAndGet(Context context){
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("MUSIC",Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * 将歌曲的名称和序号保存起来
     */
    public void saveMusicInfo(String musicName,int musicPosition){
        mEditor.putString("music_name",musicName);
        mEditor.putInt("music_position",musicPosition);
        mEditor.commit();
    }

    /**
     * 获取音乐的名称和序号
     */
    public List<String> getMusicInfo(){
        List<String> list = new ArrayList<>();
        list.add(mSharedPreferences.getString("music_name",null));
        list.add(String.valueOf(mSharedPreferences.getInt("music_position",0)));
        return list;
    }

}
