package com.music.qichaoqun.music.utils;

public class MyUtils {

    /**
     * 用来拼接path
     * @param size
     * @param offset
     * @return
     */
    public static String setPath(String type,String size,String offset){
        String path = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type="+type+"&";
        return path+"size="+size+"&offset="+offset;
    }

    /**
     * 根据歌曲的id生成歌曲的播放的路径
     * @param songId
     * @return
     */
    public static String setPath(String songId){
        return "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.playAAC&songid="+songId;
    }
}
