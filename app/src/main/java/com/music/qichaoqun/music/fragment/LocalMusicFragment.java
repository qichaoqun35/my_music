package com.music.qichaoqun.music.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.music.qichaoqun.music.Contants.MyContants;
import com.music.qichaoqun.music.R;
import com.music.qichaoqun.music.adapter.MusicListAdapter;
import com.music.qichaoqun.music.bean.LocalMusic;
import com.music.qichaoqun.music.bean.LocalMusicMessage;
import com.music.qichaoqun.music.service.MyService;
import com.music.qichaoqun.music.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author qichaoqun
 * @date 2019/3/11
 */
public class LocalMusicFragment extends Fragment {

    private RecyclerView mLocalList = null;
    private TextView mNoContent = null;
    private ProgressBar mProgressBar = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.local_music_layout,container,false);
        //初始化相关的控件
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mLocalList = view.findViewById(R.id.local_music_list);
        mLocalList.setLayoutManager(linearLayoutManager);
        mLocalList.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mNoContent = view.findViewById(R.id.local_no_content);
        mProgressBar = view.findViewById(R.id.local_progress_bar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //开启异步进行本地数据的加载
        Observable.intervalRange(0, 1, 1, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //加载数据完成，判断是否有数据
                        if(MyService.mLocalMusics != null && MyService.mLocalMusics.size() > 0){
                            //有数据，进行数据和recyclerview适配
                            MusicListAdapter musicListAdapter = new MusicListAdapter(getActivity(),MyService.mLocalMusics);
                            mLocalList.setAdapter(musicListAdapter);
                            musicListAdapter.setOnItemClickListener(new MusicListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //发送消息告诉服务应该播放那一首歌曲
                                    EventBus.getDefault().post(new LocalMusicMessage(position));
                                }
                            });
                        }else{
                            //没有数据，显示出没有数据的 textview
                            mNoContent.setVisibility(View.VISIBLE);
                        }
                        //将进度条进行隐藏
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }
}
