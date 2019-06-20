package com.music.qichaoqun.music.network;

import java.io.IOException;

import okhttp3.Request;

/**
 * @author qichaoqun
 * @create 2019/1/19
 * @Describe  对于网络请求中的相应内容的封装
 */
public interface ResultCallback {
    /**
     * 数据加载出错时的情形
     * @param request 请求体
     * @param e 异常信息
     */
    void onError(Request request, Exception e);

    /**
     * 数据加载成功是的情形
     * @param str 成功返回的数据内容
     * @throws IOException 出现的各种读取中的异常
     */
    void onResponse(String str) throws IOException;
}
