package com.music.qichaoqun.music.fragment;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.music.qichaoqun.music.Contants.MyContants;
import com.music.qichaoqun.music.R;
import com.music.qichaoqun.music.activity.PlayMusicActivity;
import com.music.qichaoqun.music.application.MyApplication;
import com.music.qichaoqun.music.bean.LastMusicMessage;
import com.music.qichaoqun.music.bean.LocalMusicInfo;
import com.music.qichaoqun.music.bean.LocalMusicMessage;
import com.music.qichaoqun.music.bean.MusicErrorMessage;
import com.music.qichaoqun.music.bean.MusicInfoMessage;
import com.music.qichaoqun.music.bean.MusicNextPlayMessage;
import com.music.qichaoqun.music.bean.MusicPauseOrStartMessage;
import com.music.qichaoqun.music.bean.MusicUpdateMessage;
import com.music.qichaoqun.music.bean.NetMusic;
import com.music.qichaoqun.music.bean.NetMusicInfoMessage;
import com.music.qichaoqun.music.bean.NetMusicMessage;
import com.music.qichaoqun.music.bean.PauseAndStartMessage;
import com.music.qichaoqun.music.bean.SingleMusic;
import com.music.qichaoqun.music.network.MyNetWork;
import com.music.qichaoqun.music.network.ResultCallback;
import com.music.qichaoqun.music.service.MyService;
import com.music.qichaoqun.music.utils.MyUtils;
import com.music.qichaoqun.music.utils.SetAndGet;
import com.music.qichaoqun.music.utils.ToastUtils;
import com.music.qichaoqun.music.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.music.qichaoqun.music.Contants.MyContants.MUSIC_ERROR;

public class MusicControlFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.music_image_small)
    ImageView mImageView;
    @BindView(R.id.music_name)
    TextView mMusicName;
    @BindView(R.id.music_singer)
    TextView mMusicSinger;
    @BindView(R.id.music_switch)
    ImageView mMusicSwitch;
    @BindView(R.id.music_next)
    ImageView mMusicNext;
    private MyService.MyBinder mBinder;
    private int mPosition = 0;
    private SetAndGet mSetAndGet;
    public int flag = 0;
    private SingleMusic mSingleMusic;
    private String[] mSongsId;
    private String mSongId;
    private Utils mUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_control_layout, container, false);
        ButterKnife.bind(this,view);
        //为view设置监听事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlayMusicActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
        mUtils = new Utils(getContext());
        //注册相关的广播
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        mBinder = myApplication.getBinder();
        //读取上一次的记录
        getPlayLog();
    }

    @OnClick({R.id.music_switch,R.id.music_next})
    public void onClick(View v) {
        switch (v.getId()) {
            //音乐播放的暂停和开始
            case R.id.music_switch:
                //判断当前音乐播放器的状态
                if (mBinder.isPlaying()) {
                    //正在播放,将音乐暂停
                    mBinder.pause();
                    //更新图标
                    mMusicSwitch.setImageResource(R.drawable.music_pause);
                } else if (MyService.isFirstPlay == 0) {
                    //没有播放，开始播放
                    mBinder.play();
                    mMusicSwitch.setImageResource(R.drawable.music_open);
                } else {
                    //播放器中有歌曲但是暂停了播放，开启播放
                    mBinder.reStart();
                    mMusicSwitch.setImageResource(R.drawable.music_open);
                }
                //通知前台更改图标
                EventBus.getDefault().post(new MusicPauseOrStartMessage());
                break;
            case R.id.music_next:
                //下一首播放
                nextPlay();
                break;
        }
    }

    /**
     * 上一首播放
     */
    private void lastPlay(){
        if(mBinder.getMusicType() == 0){
            //本地音乐
            if(MyService.mLocalMusics.size() != 0){
                if(mBinder.getPosition() > 0){
                    mBinder.setPosition(mBinder.getPosition()-1);
                }else{
                    mBinder.setPosition(MyService.mLocalMusics.size()-1);
                }
                mBinder.play(MyService.mLocalMusics.get(mBinder.getPosition()).getData());
            }else{
                ToastUtils.getInstanc(getContext()).showToast("无内容，暂时不能播放。。");
            }
        }else{
            //网络音乐
            if(mSongsId != null || mSongsId.length >=1){
                if (mBinder.getNetPosition() != 0) {
                    mBinder.setNetPosition(mBinder.getNetPosition()-1);
                } else {
                    mBinder.setNetPosition(mSongsId.length-1);
                }
                getMusicPath(mSongsId[mBinder.getNetPosition()]);
            }
        }
    }

    /**
     * 下一首播放的音乐
     */
    private void nextPlay() {
        if(mBinder.getMusicType() == 0){
            if(MyService.mLocalMusics.size() != 0){
                mMusicSwitch.setImageResource(R.drawable.music_pause);
                if (mBinder.getPosition() < MyService.mLocalMusics.size() - 1) {
                    mBinder.setPosition(mBinder.getPosition()+1);
                } else {
                    mBinder.setPosition(0);
                }
                mBinder.play(MyService.mLocalMusics.get(mBinder.getPosition()).getData());
            }else{
                ToastUtils.getInstanc(getContext()).showToast("无内容，暂时不能播放。。");
            }
        }else{
            //播放网络音乐中的下一首音乐
            if(mSongsId != null || mSongsId.length >=1){
                if (mBinder.getNetPosition() < mSongsId.length - 1) {
                    mBinder.setNetPosition(mBinder.getNetPosition()+1);
                } else {
                    mBinder.setNetPosition(0);
                }
                getMusicPath(mSongsId[mBinder.getNetPosition()]);
            }
        }
    }

    /**
     * 处理本地音乐的播放
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealPlay(LocalMusicMessage localMusicMessage){
        mPosition = localMusicMessage.getSongId();
        //当前用户点击的音乐的位置
        mBinder.setPosition(mPosition);
        mBinder.setMusicType(0);
        mBinder.play();
        mMusicSwitch.setImageResource(R.drawable.music_open);
        //将播放记录保存起来
        if (mSetAndGet == null) {
            mSetAndGet = new SetAndGet(getContext());
        }
        mSetAndGet.saveMusicInfo(MyService.mLocalMusics.get(mBinder.getPosition()).getMusicTitle(), mBinder.getPosition());
    }

    /**
     * 播放出错的时候
     * @param musicErrorMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealError(MusicErrorMessage musicErrorMessage){
        mMusicSwitch.setImageResource(R.drawable.music_pause);
        ToastUtils.getInstanc(getContext()).showToast("播放出错了。。。");
    }

    /**
     * 播放成功以后调用，更新播放的信息（首页和播放页面）
     * @param musicUpdateMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealUpdate(MusicUpdateMessage musicUpdateMessage){
        //本地播放
        if(mBinder.getMusicType() == 0){
            mImageView.setImageBitmap(MyService.mLocalMusics.get(mBinder.getPosition()).getBitmap());
            mMusicName.setText(mUtils.splitText(MyService.mLocalMusics.get(mBinder.getPosition()).getMusicTitle()));
            mMusicSinger.setText(mUtils.splitText(MyService.mLocalMusics.get(mBinder.getPosition()).getArtist()));
            EventBus.getDefault().post(new LocalMusicInfo(
                    MyService.mLocalMusics.get(mBinder.getPosition()).getMusicTitle(),
                    MyService.mLocalMusics.get(mBinder.getPosition()).getArtist(),
                    mUtils.bitmap2Bytes(MyService.mLocalMusics.get(mBinder.getPosition()).getBitmap())
            ));
        }else{
            //网络播放
            Glide.with(getContext()).load(mSingleMusic.getSonginfo().getPic_big()).into(mImageView);
            mMusicName.setText(mUtils.splitText(mSingleMusic.getSonginfo().getTitle()));
            mMusicSinger.setText(mUtils.splitText(mSingleMusic.getSonginfo().getArtist()));
            EventBus.getDefault().post(new NetMusicInfoMessage(
                    mSingleMusic.getSonginfo().getPic_big(),
                    mSingleMusic.getSonginfo().getTitle(),
                    mSingleMusic.getSonginfo().getArtist(),
                    mSingleMusic.getBitrate().getFile_link(),
                    mSingleMusic.getSonginfo().getLrclink()
            ));
        }
        mMusicSwitch.setImageResource(R.drawable.music_open);
    }

    /**
     * 处理网络音乐的播放
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealNetMusic(NetMusicMessage netMusicMessage){
        mSongId = netMusicMessage.getSongId();
        //获取当前音乐在歌曲中的位置
        mBinder.setNetPosition(mUtils.getCurrentMusicPosition(mSongsId,mSongId));
        //根据歌曲的id获取相关的额音乐播放资源的路径
        getMusicPath(mSongId);
    }

    /**
     * 处理网络音乐
     * @param songListBeans
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealNetMusicSongs(List<NetMusic.SongListBean> songListBeans){
        //获取网络音乐中所有的音乐
        List<NetMusic.SongListBean> netMusics = songListBeans;
        //将网络音乐集合中的所有的音乐的id全部都取出来
        mSongsId = mUtils.getSongsID(netMusics);
    }

    /**
     * 播放页面请求的更求当前播放的音乐的信息
     * 两次握手
     * @param musicInfoMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealMusicInfo(MusicInfoMessage musicInfoMessage){
        //获取当前音乐的信息
        if(mBinder.getMusicType() == 0){
            //如果本地音乐为空
            if(MyService.mLocalMusics.size() == 0){
                EventBus.getDefault().post(new LocalMusicInfo("没有内容","无",null));
            }else{
                EventBus.getDefault().post(new LocalMusicInfo(
                        MyService.mLocalMusics.get(mBinder.getPosition()).getMusicTitle(),
                        MyService.mLocalMusics.get(mBinder.getPosition()).getArtist(),
                        mUtils.bitmap2Bytes(MyService.mLocalMusics.get(mBinder.getPosition()).getBitmap())
                ));
            }
        }else{
            //网络播放
            Glide.with(getContext()).load(mSingleMusic.getSonginfo().getPic_big()).into(mImageView);
            mMusicName.setText(mUtils.splitText(mSingleMusic.getSonginfo().getTitle()));
            mMusicSinger.setText(mUtils.splitText(mSingleMusic.getSonginfo().getArtist()));
            //发送广播更新界面
            EventBus.getDefault().post(new NetMusicInfoMessage(
                    mSingleMusic.getSonginfo().getPic_big(),
                    mSingleMusic.getSonginfo().getTitle(),
                    mSingleMusic.getSonginfo().getArtist(),
                    mSingleMusic.getBitrate().getFile_link(),
                    mSingleMusic.getSonginfo().getLrclink()
            ));
        }
    }

    /**
     * 音乐的暂停和开始播放（播放页面发出）
     * @param pauseAndStartMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealMusicPauseAndStart(PauseAndStartMessage pauseAndStartMessage){
        if(!mBinder.isPlaying()){
            mMusicSwitch.setImageResource(R.drawable.music_pause);
        }else{
            mMusicSwitch.setImageResource(R.drawable.music_open);
        }
    }

    /**
     * 音乐的上一首播放
     * @param lastMusicMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealMusicLastPlay(LastMusicMessage lastMusicMessage){
        //上一首播放
        lastPlay();
    }

    /**
     * 音乐的下一首播放
     * @param musicNextPlay
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealMusicNextPlay(MusicNextPlayMessage musicNextPlay){
        //上一首播放
        lastPlay();
    }

    /**
     * 获取音乐的播放路径
     */
    private void getMusicPath(String songId){
        String path = MyUtils.setPath(songId);
        MyNetWork.getInstance(getContext()).getAsynHttp(path, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.getInstanc(getContext()).showToast("路径解析错误。。");
            }

            @Override
            public void onResponse(String str) throws IOException {
                Gson gson = new Gson();
                mSingleMusic = gson.fromJson(str, SingleMusic.class);
                //获取音乐的播放的路径
                String fileLink = mSingleMusic.getBitrate().getFile_link();
                Message message = Message.obtain();
                message.obj = fileLink;
                new PlayNetMuisc().sendMessage(message);
            }
        });
    }

    public class PlayNetMuisc extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String fileLink = (String) msg.obj;
            //开始播放音乐
            mBinder.setMusicType(1);
            mBinder.setMusicPath(fileLink);
            mBinder.play();
            flag = 1;
        }
    }

    /**
     * 读取用户上一次的音乐播放的记录
     */
    private void getPlayLog() {
        mSetAndGet = new SetAndGet(getContext());
        List<String> music = mSetAndGet.getMusicInfo();
        //获取缓存中的音乐的位置
        if(music.size() == 0){
            int position = Integer.valueOf(music.get(1));
            if(position <= MyService.mLocalMusics.size()){
                if (MyService.mLocalMusics.get(position).getMusicTitle().equals(music.get(0))) {
                    //存在此歌曲，奖歌曲信息提前显示出来
                    mBinder.setPosition(position);
                    mImageView.setImageBitmap(MyService.mLocalMusics.get(mBinder.getPosition()).getBitmap());
                    mMusicName.setText(MyService.mLocalMusics.get(mBinder.getPosition()).getMusicTitle());
                    mMusicSinger.setText(MyService.mLocalMusics.get(mBinder.getPosition()).getArtist());
                    mBinder.setData(MyService.mLocalMusics.get(mBinder.getPosition()).getData());
                }
            }
        }else{
            //没有记录
            mBinder.setPosition(0);
            if(MyService.mLocalMusics != null && MyService.mLocalMusics.size() > 0){
                mImageView.setImageBitmap(MyService.mLocalMusics.get(mBinder.getPosition()).getBitmap());
                mMusicName.setText(MyService.mLocalMusics.get(mBinder.getPosition()).getMusicTitle());
                mMusicSinger.setText(MyService.mLocalMusics.get(mBinder.getPosition()).getArtist());
                mBinder.setData(MyService.mLocalMusics.get(mBinder.getPosition()).getData());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
