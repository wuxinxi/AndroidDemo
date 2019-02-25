package com.wxx.androiddemo.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.fragment.adapter.ViewPageAdapter;
import com.wxx.androiddemo.fragment.fragment.FragmentA;
import com.wxx.androiddemo.fragment.fragment.FragmentB;
import com.wxx.androiddemo.fragment.fragment.FragmentC;
import com.wxx.androiddemo.fragment.fragment.FragmentD;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.fragment
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener {

    BottomNavigationView nav;
    ViewPager viewPager;

    FragmentA fragmentA;
    FragmentB fragmentB;
    FragmentC fragmentC;
    FragmentD fragmentD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main_fragment);
        nav = findViewById(R.id.nav);
        viewPager = findViewById(R.id.viewPager);
        nav.setOnNavigationItemSelectedListener(this);

        initData();

    }

    private void initData() {
        fragmentA = FragmentA.newInstance(FragmentA.class.getClass().getName());
        fragmentB = FragmentB.newInstance(FragmentB.class.getClass().getName());
        fragmentC = FragmentC.newInstance(FragmentC.class.getClass().getName());
        fragmentD = FragmentD.newInstance(FragmentD.class.getClass().getName());
        List<Fragment> list = new ArrayList<>();
        list.add(fragmentA);
        list.add(fragmentB);
        list.add(fragmentC);
        list.add(fragmentD);
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(4);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.item_2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.item_3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.item_4:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        Log.e("MainActivity",
                "onPageSelected(MainActivity.java:104)位置：" + i);
        nav.getMenu().getItem(i).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
