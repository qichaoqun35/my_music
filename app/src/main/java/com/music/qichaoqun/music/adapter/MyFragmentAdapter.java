package com.music.qichaoqun.music.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = null;
    private List<String> mListTitle = null;

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> listTitle) {
        super(fm);
        mFragmentList = fragmentList;
        mListTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) mListTitle.get(position % mListTitle.size());
    }
}
