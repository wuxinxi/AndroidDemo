package com.wxx.androiddemo.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.fragment.adapter
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class ViewPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mList;

    public ViewPageAdapter(FragmentManager fm,List<Fragment> mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
