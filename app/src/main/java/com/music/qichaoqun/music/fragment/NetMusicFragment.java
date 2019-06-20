package com.music.qichaoqun.music.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.gson.Gson;
import com.music.qichaoqun.music.R;
import com.music.qichaoqun.music.adapter.NetMusicListAdapter;
import com.music.qichaoqun.music.bean.NetMusic;
import com.music.qichaoqun.music.bean.NetMusicMessage;
import com.music.qichaoqun.music.bean.SingleMusic;
import com.music.qichaoqun.music.network.MyNetWork;
import com.music.qichaoqun.music.network.ResultCallback;
import com.music.qichaoqun.music.utils.DownloadUtil;
import com.music.qichaoqun.music.utils.MyUtils;
import com.music.qichaoqun.music.utils.ToastUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Request;

/**
 * @author qichaoqun
 * @date 2019/3/11
 */
public class NetMusicFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener, NetMusicListAdapter.OnDownloadClickListener {

    public static final int RESPOND_CODE = 200;
    public static final int FIRST_REFRESH = 1000;
    public static final int PULL_REFRESH = 1001;
    public static final int LOAD_REFRESH = 1002;
    public static final int DELAYED = 800;

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private String size = "10";
    private String offset = "0";
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private int status = 0;
    private int times = 1;
    private int type = 1;
    private int[] types = new int[]{1, 12, 22, 23, 16, 24, 25, 2, 11, 21};
    private int count = 1;
    /**
     * 用于装在当前加载的数据
     */
    private List<NetMusic.SongListBean> currentMusicList = null;
    private NetMusicListAdapter mMusicListAdapter;
    private MyUtils mMyUtils = null;
    private String mMusicName;
    private CircleProgressBar mCircleProgressBar;
    private ImageView mDownloadImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.net_music_layout, container, false);
        //初始化先啦刷新和上划刷新的控件
        mSmartRefreshLayout = view.findViewById(R.id.refreshLayout);
        //设置相关的样式
        mSmartRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        //mSmartRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        //设置滑动动作的监听
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        //初始化recyclerview控件
        mRecyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mTextView = view.findViewById(R.id.net_no_content);
        mProgressBar = view.findViewById(R.id.net_progress_bar);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMyUtils = new MyUtils();
        Observable.intervalRange(0, 1, 2, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //异步进行歌曲的加载
                        getNetMusic();
                    }
                });
    }


    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        //下拉刷新的时候加载数据
        times = 0;
        status = 1;
        offset = "0";
        //切换歌曲的类型
        if (count <= types.length) {
            count++;
        } else {
            count = 0;
        }
        type = types[count];
        getNetMusic();
        refreshLayout.finishRefresh(DELAYED);
    }

    /**
     * 上划刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        times++;
        status = 2;
        offset = String.valueOf(10 * times);
        getNetMusic();
        refreshLayout.finishLoadMore(DELAYED);
    }

    /**
     * 获取网络音乐的列表
     */
    private void getNetMusic() {
        String path = MyUtils.setPath(String.valueOf(type), size, offset);
        MyNetWork.getInstance(getContext()).getAsynHttp(path, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.getInstanc(getContext()).showToast("音乐列表加载出错。。");
            }

            @Override
            public void onResponse(String str) throws IOException {
                Gson gson = new Gson();
                NetMusic netMusic = gson.fromJson(str, NetMusic.class);
                currentMusicList = netMusic.getSong_list();
                switch (status) {
                    //进入页面的刷新
                    case 0:
                        new MyHandler().sendEmptyMessage(FIRST_REFRESH);
                        break;
                    //下拉刷新
                    case 1:
                        new MyHandler().sendEmptyMessage(PULL_REFRESH);
                        break;
                    //上划刷新
                    case 2:
                        new MyHandler().sendEmptyMessage(LOAD_REFRESH);
                        break;
                }
            }
        });
    }

    /**
     * 点击下载的事件
     *
     * @param songId
     */
    @Override
    public void onDownloadClick(String songId, ImageView imageView, CircleProgressBar circleProgressBar) {
        getMusicPath(songId);
        //更改图标
        imageView.setVisibility(View.GONE);
        circleProgressBar.setVisibility(View.VISIBLE);
        mDownloadImage = imageView;
        mCircleProgressBar = circleProgressBar;
    }

    /**
     * 获取音乐的下载路径
     */
    private void getMusicPath(String songId) {
        String path = MyUtils.setPath(songId);
        MyNetWork.getInstance(getContext()).getAsynHttp(path, new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtils.getInstanc(getContext()).showToast("下载路径出错。。。");
            }

            @Override
            public void onResponse(String str) throws IOException {
                Gson gson = new Gson();
                SingleMusic singleMusic = gson.fromJson(str, SingleMusic.class);
                //获取音乐的播放的路径
                String fileLink = singleMusic.getBitrate().getFile_link();
                //获取音乐的名称
                String musicName = singleMusic.getSonginfo().getTitle();
                //数组存储
                String[] paths = new String[]{fileLink, musicName};
                Message message = Message.obtain();
                message.obj = paths;
                new DownMusic().sendMessage(message);
            }
        });
    }


    public class DownMusic extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String[] paths = (String[]) msg.obj;
            DownloadUtil.get().download(paths[0], "qichaoqun.music", paths[1], new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadImage.setImageResource(R.drawable.down_load_success);
                            ToastUtils.getInstanc(getContext()).showToast("下载完成。。。");
                        }
                    });
                }

                @Override
                public void onDownloadSuccess(File file) {

                }

                @Override
                public void onDownloading(int progress) {
                    mCircleProgressBar.setProgress(progress);
                }

                @Override
                public void onDownloadFailed() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.getInstanc(getContext()).showToast("下载失败。。。");
                        }
                    });
                }
            });
        }
    }


    /**
     * 处理各种刷新的状态
     */
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //进入页面的刷新
                case FIRST_REFRESH:
                    //为recycler配置相关的数据，和一些基本的初始化操作
                    setMusicListViewAdapter();
                    break;
                //用户的下拉刷新
                case PULL_REFRESH:
                    mMusicListAdapter.pullRefresh(currentMusicList);
                    currentMusicList.clear();
                    break;
                //用户的上划刷新
                case LOAD_REFRESH:
                    if (currentMusicList != null) {
                        mMusicListAdapter.loadMore(currentMusicList);
                        currentMusicList.clear();
                    } else {
                        ToastUtils.getInstanc(getContext()).showToast("无更多内容。。。");
                    }
                    break;
            }
        }
    }

    /**
     * 初始化相关的recycler和数据的初次适配
     */
    private void setMusicListViewAdapter() {
        //加载数据完成，判断是否有数据
        if (currentMusicList != null && currentMusicList.size() > 0) {
            //有数据，进行数据和recyclerview适配
            mMusicListAdapter = new NetMusicListAdapter(getActivity(), currentMusicList);
            mRecyclerView.setAdapter(mMusicListAdapter);
            mMusicListAdapter.setOnItemClickListener(new NetMusicListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, String songId, List<NetMusic.SongListBean> netMusics) {
                    EventBus.getDefault().post(new NetMusicMessage(songId));
                }
            });
            mMusicListAdapter.setOnDownloadClickListener(this);
        } else {
            //没有数据，显示出没有数据的 textview
            mTextView.setVisibility(View.VISIBLE);
        }
        //将进度条进行隐藏
        mProgressBar.setVisibility(View.GONE);
    }


}
