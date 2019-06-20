package com.music.qichaoqun.music.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lauzy.freedom.library.Lrc;
import com.lauzy.freedom.library.LrcHelper;
import com.lauzy.freedom.library.LrcView;
import com.music.qichaoqun.music.BaseClass.ToBitmap;
import com.music.qichaoqun.music.Contants.MyContants;
import com.music.qichaoqun.music.R;
import com.music.qichaoqun.music.application.MyApplication;
import com.music.qichaoqun.music.bean.LocalMusicInfo;
import com.music.qichaoqun.music.bean.MusicInfoMessage;
import com.music.qichaoqun.music.bean.MusicPauseOrStartMessage;
import com.music.qichaoqun.music.bean.NetMusicInfoMessage;
import com.music.qichaoqun.music.bean.PauseAndStartMessage;
import com.music.qichaoqun.music.service.MyService;
import com.music.qichaoqun.music.utils.BlurUtil;
import com.music.qichaoqun.music.utils.DownloadUtil;
import com.music.qichaoqun.music.utils.ToastUtils;
import com.music.qichaoqun.music.utils.Utils;
import com.shehuan.niv.NiceImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public static  int rotationTime = 20000;
    @BindView(R.id.music_back)
    ImageView mBackImage;
    @BindView(R.id.music_text)
    TextView mMusicName;
    @BindView(R.id.singer_text)
    TextView mSingerText;
    @BindView(R.id.music_image)
    NiceImageView mMusicImage;
    @BindView(R.id.now_time)
    TextView mNowTime;
    @BindView(R.id.music_seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.all_time)
    TextView mAllTime;
    @BindView(R.id.music_last)
    ImageView mLastMusic;
    @BindView(R.id.music_pause_start)
    ImageView mStartAndOpen;
    @BindView(R.id.music_next_new)
    ImageView mNextMusic;
    @BindView(R.id.music_down_load)
    ImageView mDownLoad;
    MyService.MyBinder mMyBinder;
    private Utils mUtils;
    SeekBarHandler mSeekBarHandler;
    private static final int HANDLER_MESSAGE = 1001;
    private String mMusicUrl;
    private String mLyric;
    private String mName;
    private String mArtist;
    private boolean showImage = true;
    @BindView(R.id.lyric_view)
    LrcView mLrcView;
    private static File file = null;
    private Bitmap mBitmap;
    @BindView(R.id.back_ground_view)
    RelativeLayout mBgRelativeView;
    private ObjectAnimator mObjectAnimator;
    private long mCurrentPlayTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.music_play_layout);
        ButterKnife.bind(this);
        //全屏
        fullscreen(true);
        mSeekBar.setOnSeekBarChangeListener(this);
        EventBus.getDefault().register(this);
        //获取service
        MyApplication myApplication = (MyApplication) getApplication();
        mMyBinder = myApplication.getBinder();
        //注册广播用于接收
        //发送广播给控制页面，获取当前音乐的信息
        mUtils = new Utils(this);
        EventBus.getDefault().post(new MusicInfoMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.music_image,R.id.layout_two,R.id.lyric_view,R.id.music_back,R.id.music_last,
            R.id.music_pause_start,R.id.music_next_new,R.id.music_down_load})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_image:
                if (showImage) {
                    //显示歌词
                    //下载歌词
                    if (mLyric != null) {
                        if(mMyBinder.getMusicType() == 1){
                            downloadLyric();
                        }else{
                            mLrcView.setEmptyContent("没有歌词哟。。。");
                        }
                    } else {
                        mLrcView.setEmptyContent("没有内容呢。。。");
                    }
                    showImage = false;
                    //专辑图片消失
                    mMusicImage.setVisibility(View.GONE);
                    //歌词控件显示
                    mLrcView.setVisibility(View.VISIBLE);
                } else {
                    //显示专辑
                    mMusicImage.setVisibility(View.VISIBLE);
                    //歌词控件消失
                    mLrcView.setVisibility(View.GONE);
                    showImage = true;
                }
                break;
            case R.id.layout_two:
                mMusicImage.setVisibility(View.VISIBLE);
                mLrcView.setVisibility(View.GONE);
                break;
            case R.id.lyric_view:
                mMusicImage.setVisibility(View.VISIBLE);
                mLrcView.setVisibility(View.GONE);
                break;
            case R.id.music_back:
                onBackPressed();
                //后退
                break;
            case R.id.music_last:
                //上一首播放
                mStartAndOpen.setImageResource(R.drawable.end_play_new);
                stopAnimation();
                mMyBinder.lastMusic();
                break;
            case R.id.music_pause_start:
                //暂停和开始
                //判断当前音乐播放器的状态
                if (mMyBinder.isPlaying()) {
                    //正在播放,将音乐暂停
                    mMyBinder.pause();
                    //更新图标
                    mStartAndOpen.setImageResource(R.drawable.end_play_new);
                    stopAnimation();
                } else if (MyService.isFirstPlay == 0) {
                    //没有播放，开始播放
                    mMyBinder.play();
                    mStartAndOpen.setImageResource(R.drawable.start_play_new);
                    startAnimation();
                } else {
                    //播放器中有歌曲但是暂停了播放，开启播放
                    mMyBinder.reStart();
                    mStartAndOpen.setImageResource(R.drawable.start_play_new);
                    startAnimation();
                }
                EventBus.getDefault().post(new PauseAndStartMessage());
                break;
            case R.id.music_next_new:
                mStartAndOpen.setImageResource(R.drawable.end_play_new);
                stopAnimation();
                mMyBinder.nextMusic();
                break;
            case R.id.music_down_load:
                if (mMyBinder.getMusicType() == 1) {
                    if (mMusicUrl != null) {
                        //下载歌曲
                        downMusic(mMusicUrl);
                    } else {
                        ToastUtils.getInstanc(this).showToast("下载出错。。。");
                    }
                } else {
                    ToastUtils.getInstanc(this).showToast("不能下载哟。。。");
                }
                break;
        }
    }

    /**
     * 下载歌词
     */
    private void downloadLyric() {
        DownloadUtil.get().download(mLyric, "qichaoqun.lyric", mName, ".lrc", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {}

            @Override
            public void onDownloadSuccess(File file) {
                List<Lrc> lrcs = LrcHelper.parseLrcFromFile(file);
                mLrcView.setLrcData(lrcs);
                mLrcView.updateTime(mMyBinder.getProgress());
                mLrcView.setOnPlayIndicatorLineListener(new LrcView.OnPlayIndicatorLineListener() {
                    @Override
                    public void onPlay(long time, String content) {
                        mMyBinder.setMusicSeek((int) time);
                    }
                });
            }

            @Override
            public void onDownloading(int progress) {
                mLrcView.setEmptyContent("正在下载。。。");
            }

            @Override
            public void onDownloadFailed() {
                mLrcView.setEmptyContent("歌词下载失败。。。");
            }
        });
    }


    /**
     * 下载音乐
     * @param url
     */
    private void downMusic(String url) {
        DownloadUtil.get().download(url, "qichaoqun.music", mName, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PlayMusicActivity.this, "下载完成。。。", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onDownloadSuccess(File file) {}

            @Override
            public void onDownloading(int progress) {}

            @Override
            public void onDownloadFailed() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PlayMusicActivity.this, "下载失败。。。", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    /**
     * 设置seekbar的动态更新
     */
    public class SeekBarHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //用于动态的显示和更新seekbar
            String time = mUtils.stringForTime(mMyBinder.getProgress());
            int seekPosition = mMyBinder.getProgress();
            mLrcView.updateTime(seekPosition);
            mNowTime.setText(time);
            mSeekBar.setProgress(seekPosition);
            //更新旋图案的时间
            mObjectAnimator.setDuration(20000);
            mSeekBarHandler.removeCallbacksAndMessages(null);
            mSeekBarHandler.sendEmptyMessageDelayed(HANDLER_MESSAGE, 1000);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealMusicLocalInfoUpdate(LocalMusicInfo localMusicInfo){
        //更新当前音乐的信息
        mName = localMusicInfo.getTitle();
        mArtist = localMusicInfo.getSinger();
        if (mName.length() >= 7) {
            mName = mName.substring(0, 7) + "...";
        }
        if (mArtist.length() >= 5) {
            mArtist = mArtist.substring(0, 5) + "...";
        }
        mMusicName.setText(mName);
        mSingerText.setText(mArtist);
        //本地音乐
        byte[] data = localMusicInfo.getImage();
        if (data == null || data.length == 0) {
            mMusicImage.setImageResource(R.drawable.load_faild);
        } else {
            mMusicImage.setImageBitmap(mUtils.byte2Bitmap(data));
        }
        mBitmap = mUtils.byte2Bitmap(data);
        //设置音乐播放背景虚化效果
        Bitmap bgbm = BlurUtil.doBlur(mBitmap, 10, 5);
        mBgRelativeView.setBackgroundDrawable(new BitmapDrawable(bgbm));
        updateMusicOtherInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealMusicNetInfoUpdate(NetMusicInfoMessage netMusicInfo){
        //更新当前音乐的信息
        mName = netMusicInfo.getTitle();
        mArtist = netMusicInfo.getSinger();
        if (mName.length() >= 7) {
            mName = mName.substring(0, 7) + "...";
        }
        if (mArtist.length() >= 5) {
            mArtist = mArtist.substring(0, 5) + "...";
        }
        mMusicName.setText(mName);
        mSingerText.setText(mArtist);
        //网络音乐
        Glide.with(PlayMusicActivity.this).load(netMusicInfo.getImage()).into(mMusicImage);
        //更新音乐的播放地址
        mMusicUrl = netMusicInfo.getMusicPath();
        mLyric = netMusicInfo.getMusicLyric();
        //将网络图片转为bitmap
        mUtils.returnBitMap(netMusicInfo.getImage(), new ToBitmap() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                mBitmap = bitmap;
                //设置音乐播放背景虚化效果
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bgbm = BlurUtil.doBlur(mBitmap, 10, 5);
                        mBgRelativeView.setBackgroundDrawable(new BitmapDrawable(bgbm));
                    }
                });
            }
        });
        updateMusicOtherInfo();
    }

    /**
     * 音乐的播放和暂停更新页面，首页面发出
     * @param musicPauseOrStartMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dealMusicPauseOrStartInfo(MusicPauseOrStartMessage musicPauseOrStartMessage){
        if (!mMyBinder.isPlaying()) {
            //正在播放
            mStartAndOpen.setImageResource(R.drawable.end_play_new);
            stopAnimation();
        } else {
            mStartAndOpen.setImageResource(R.drawable.start_play_new);
            startAnimation();
        }
    }

    /**
     * 更新音乐的其他的信息
     */
    private void updateMusicOtherInfo() {
        mStartAndOpen.setImageResource(R.drawable.start_play_new);
        //设置总的时常
        mSeekBar.setMax(mMyBinder.getDuration());
        mSeekBar.setProgress(mMyBinder.getProgress());
        //设置总的时常
        mAllTime.setText(mUtils.formatTime(mMyBinder.getDuration()));
        //开启循环的更新进度条
        mSeekBarHandler = new SeekBarHandler();
        mSeekBarHandler.sendEmptyMessage(HANDLER_MESSAGE);
        //更新图标
        if (mMyBinder.isPlaying()) {
            mStartAndOpen.setImageResource(R.drawable.start_play_new);
        } else {
            mStartAndOpen.setImageResource(R.drawable.end_play_new);
        }
        //开启专辑图片的旋转效果
        setAnimation();
    }

    /**
     * 设置旋转的动画
     */
    public void setAnimation(){
        if(mObjectAnimator == null){
            mObjectAnimator = ObjectAnimator.ofFloat(mMusicImage,"rotation",0,360);
            mObjectAnimator.setDuration(rotationTime);
            mObjectAnimator.setInterpolator(new LinearInterpolator());
            mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }
        if(mMyBinder.isPlaying()){
            startAnimation();
        }
    }

    /**
     * 暂停旋转
     */
    private void stopAnimation(){
        mCurrentPlayTime = mObjectAnimator.getCurrentPlayTime();
        mObjectAnimator.cancel();
    }

    /**
     * 开始旋转
     */
    private void startAnimation() {
        mObjectAnimator.start();
        mObjectAnimator.setCurrentPlayTime(mCurrentPlayTime);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            seekBar.setProgress(progress);
            mMyBinder.setMusicSeek(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}


    /**
     * 全屏显示
     * @param enable
     */
    private void fullscreen(boolean enable) {
        if (enable) { //显示状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else { //隐藏状态栏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

}
