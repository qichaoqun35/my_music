package com.music.qichaoqun.music.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.qichaoqun.music.R;
import com.music.qichaoqun.music.bean.LocalMusic;

import java.util.List;

/**
 * @author qichaoqun
 * @date 2018/12/12
 */
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicHolder> {
    private Context mContext = null;
    private List<LocalMusic> mLocalMusics = null;
    private OnItemClickListener mOnItemClickListener = null;

    public MusicListAdapter(Context mainActivity, List<LocalMusic> localMusics) {
        mContext = mainActivity;
        mLocalMusics = localMusics;
    }

    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.local_music_item, null);
        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder musicHolder, final int position) {
        //设置音乐的图片
        musicHolder.mImageView.setImageBitmap(mLocalMusics.get(position).getBitmap());
        //设置音乐名称
        musicHolder.mMusicName.setText(mLocalMusics.get(position).getMusicTitle());
        musicHolder.mMusicSinger.setText(mLocalMusics.get(position).getArtist());
        //设置音乐的播放时长
        musicHolder.mMusicSize.setText(formatTime(mLocalMusics.get(position).getDuration()) + "");
        //设置每个条目的监听事件
        if (mOnItemClickListener != null) {
            musicHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mLocalMusics.size();
    }

    public class MusicHolder extends RecyclerView.ViewHolder {

        private TextView mMusicName;
        private ImageView mImageView;
        private TextView mMusicSinger;
        private TextView mMusicSize;

        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image);
            mMusicName = itemView.findViewById(R.id.title);
            mMusicSinger = itemView.findViewById(R.id.singer);
            mMusicSize = itemView.findViewById(R.id.time);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        /**
         * @param view     被点击的视图
         * @param position 点击的视图的位置
         */
        void onItemClick(View view, int position);
    }


    public static String formatTime(long time) {
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
}
