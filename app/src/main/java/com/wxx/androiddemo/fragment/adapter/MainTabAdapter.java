package com.wxx.androiddemo.fragment.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.fragment.tabadapter
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainTabAdapter extends FragmentStatePagerAdapter {
    List<Fragment> mList;
    String mTabTitle[] = new String[]{"科技", "游戏", "创业", "想法", "学习", "Android"};

    public MainTabAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle[position];
    }
}
