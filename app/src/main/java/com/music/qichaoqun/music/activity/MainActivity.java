package com.music.qichaoqun.music.activity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.music.qichaoqun.music.R;
import com.music.qichaoqun.music.adapter.MyFragmentAdapter;
import com.music.qichaoqun.music.application.MyApplication;
import com.music.qichaoqun.music.fragment.LocalMusicFragment;
import com.music.qichaoqun.music.fragment.MusicControlFragment;
import com.music.qichaoqun.music.fragment.MyFragment;
import com.music.qichaoqun.music.fragment.NetMusicFragment;
import com.music.qichaoqun.music.view.ViewPagerCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qichaoqun 
 * @create 2019/3/11
 * @Describe 对于音乐播放器的简单的练习
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //设置主页面中各个小的页面
        setTabLayout();
        //设置底部的控制页面
        setBottomController();
    }

    /**
     * 用于设置底部的控制音乐播放的fragment
     */
    private void setBottomController() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.music_control,new MusicControlFragment());
        transaction.commit();
    }

    /**
     * 设置页面中所需要的viewpager
     */
    private void setTabLayout() {
        //设置TabLayout
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPagerCompat viewPagerCompat = findViewById(R.id.view_pager);
        //创建装载fragment的相关的集合
        List<Fragment> fragmentList = new ArrayList<>();
        //创建fragment的相关实列化对象
        fragmentList.add(new LocalMusicFragment());
        fragmentList.add(new NetMusicFragment());
        fragmentList.add(new MyFragment());
        //创建用于装载fragment中标题的名字的相关的集合
        List<String> titles = new ArrayList<>();
        titles.add("本地歌曲");
        titles.add("网络歌曲");
        titles.add("我的");
        //将fragment 与 view pager和tablayouty相互结合
        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPagerCompat.setAdapter(myFragmentAdapter);
        tabLayout.setupWithViewPager(viewPagerCompat);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
