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
import com.music.qichaoqun.music.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
/**
 * @author qichaoqun
 * @date 2019/3/11
 */
public class LocalMusicFragment extends Fragment {

    private RecyclerView mLocalList = null;
    private TextView mNoContent = null;
    private ProgressBar mProgressBar = null;
    private List<LocalMusic> mLocalMusics = null;
    public static final int WHAT = 1000;
    private Intent mIntent = null;
    private Utils mUtils;

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
        mUtils = new Utils(getContext());
        mIntent = new Intent();
        //开启异步进行本地数据的加载
        new MyAnsyTask().execute();
    }

    public class MyAnsyTask extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... strings) {
            mLocalMusics = new ArrayList<>();
            ContentResolver contentResolver = getActivity().getContentResolver();
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
            while(mCursor.moveToNext()){
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
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MyHandler myHandler = new MyHandler();
            myHandler.sendEmptyMessage(WHAT);
        }
    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == WHAT){
                //加载数据完成，判断是否有数据
                if(mLocalMusics != null && mLocalMusics.size() > 0){
                    //有数据，进行数据和recyclerview适配
                    MusicListAdapter musicListAdapter = new MusicListAdapter(getActivity(),mLocalMusics);
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
        }
    }


}
