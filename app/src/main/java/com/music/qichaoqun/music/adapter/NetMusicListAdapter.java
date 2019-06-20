package com.music.qichaoqun.music.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.music.qichaoqun.music.R;
import com.music.qichaoqun.music.bean.NetMusic;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

/**
 * @author qichaoqun
 * @date 2018/12/12
 */
public class NetMusicListAdapter extends RecyclerView.Adapter<NetMusicListAdapter.MusicHolder> {
    private Context mContext = null;
    private List<NetMusic.SongListBean> mNetMuiscs = null;
    private OnItemClickListener mOnItemClickListener = null;
    private OnDownloadClickListener mOnDownloadClickListener = null;

    public NetMusicListAdapter(Context mainActivity, List<NetMusic.SongListBean> localMusics) {
        mContext = mainActivity;
        mNetMuiscs = localMusics;
        sendMusicToController();
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.net_music_item, null);
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicHolder musicHolder, final int position) {
        //设置音乐的图片
        Glide.with(mContext).load(mNetMuiscs.get(position).getPic_premium()).into(musicHolder.mImageView);
        //设置音乐名称
        String musicName = mNetMuiscs.get(position).getTitle();
        if(musicName.length() >= 8){
            musicName = musicName.substring(0,8)+"...";
        }
        musicHolder.mMusicName.setText(musicName);
        musicHolder.mMusicSinger.setText(mNetMuiscs.get(position).getArtist_name());
        //设置音乐的播放时长
        musicHolder.mMusicSize.setText(formatTime(mNetMuiscs.get(position).getFile_duration()));
        //设置每个条目的监听事件
        if (mOnItemClickListener != null) {
            musicHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, mNetMuiscs.get(position).getSong_id(),mNetMuiscs);
                }
            });
        }
        musicHolder.mDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnDownloadClickListener != null){
                    mOnDownloadClickListener.onDownloadClick(mNetMuiscs.get(position).getSong_id(),musicHolder.mDownloadImage,musicHolder.mCircleProgressBar);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mNetMuiscs.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {

        private TextView mMusicName;
        private ImageView mImageView;
        private TextView mMusicSinger;
        private TextView mMusicSize;
        private ImageView mDownloadImage;
        private final CircleProgressBar mCircleProgressBar;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mMusicName = itemView.findViewById(R.id.title);
            mMusicSinger = itemView.findViewById(R.id.singer);
            mMusicSize = itemView.findViewById(R.id.time);
            mDownloadImage = itemView.findViewById(R.id.down_load);
            mCircleProgressBar = itemView.findViewById(R.id.circle_progress);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置下载的点击事件
     * @param onDownloadClickListener
     */
    public void setOnDownloadClickListener(OnDownloadClickListener onDownloadClickListener){
        mOnDownloadClickListener = onDownloadClickListener;
    }


    public interface OnItemClickListener {
        /**
         * @param view   被点击的视图
         * @param songId 点击的视图的位置
         */
        void onItemClick(View view, String songId,List<NetMusic.SongListBean> netMusics);
    }

    public interface  OnDownloadClickListener{
        void onDownloadClick(String songId,ImageView imageView,CircleProgressBar circleProgressBar);
    }


    /**
     * 格式化音乐的时间
     * @param time
     * @return
     */
    public static String formatTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        String duration = String.valueOf(minutes) + ":" + String.valueOf(seconds);
        if (minutes <= 9) {
            duration = "0" + duration;
        }
        if (seconds <= 9) {
            duration = duration.replace(":", ":0");
        }
        return duration;
    }

    /**
     * 上划刷新获取的数据
     */
    public void loadMore(List<NetMusic.SongListBean> currentMusicList) {
        mNetMuiscs.addAll(currentMusicList);
        notifyDataSetChanged();
        sendMusicToController();
    }

    /**
     * 下拉刷新获取的数据
     */
    public void pullRefresh(List<NetMusic.SongListBean> currentMusicList) {
        mNetMuiscs.clear();
        mNetMuiscs.addAll(currentMusicList);
        notifyDataSetChanged();
        sendMusicToController();
    }

    /**
     * 广播放送档当前的音乐的数量
     */
    public void sendMusicToController(){
        EventBus.getDefault().post(mNetMuiscs);
        /*Intent intent = new Intent();
        intent.setAction("com.qichaoqun.music.net.songs");
        intent.putExtra("songs", (Serializable) mNetMuiscs);
        mContext.sendBroadcast(intent);*/
    }



}
