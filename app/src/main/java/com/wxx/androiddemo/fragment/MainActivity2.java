package com.wxx.androiddemo.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.fragment.fragment.FragmentA;
import com.wxx.androiddemo.fragment.fragment.FragmentB;
import com.wxx.androiddemo.fragment.fragment.FragmentC;


/**
 * 作者：Tangren on 2019-02-20
 * 包名：com.fragment
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity2 extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FrameLayout layout;
    private BottomNavigationView nav;

    FragmentA fragmentA;
    FragmentB fragmentB;
    FragmentC fragmentC;

    Fragment lastFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main_fragment2);
        layout = findViewById(R.id.layout);
        nav = findViewById(R.id.nav);

        initData();

    }

    private void initData() {
        fragmentA = FragmentA.newInstance(FragmentA.class.getClass().getName());
        lastFragment = fragmentA;
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, fragmentA).show(fragmentA).commit();
        nav.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_1:
                if (fragmentA == null) {
                    fragmentA = FragmentA.newInstance(FragmentA.class.getClass().getName());
                }
                if (lastFragment != fragmentA) {
                    switchFragment(lastFragment, fragmentA);
                }
                return true;
            case R.id.item_2:
                if (fragmentB == null) {
                    fragmentB = FragmentB.newInstance(FragmentB.class.getName());
                }
                if (lastFragment != fragmentB) {
                    switchFragment(lastFragment, fragmentB);
                }
                return true;
            case R.id.item_3:
                if (fragmentC == null) {
                    fragmentC = FragmentC.newInstance(FragmentC.class.getName());
                }
                if (lastFragment != fragmentC) {
                    switchFragment(lastFragment, fragmentC);
                }
                return true;
            default:
                return false;
        }

    }

    private void switchFragment(Fragment lastFragment, Fragment currentFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            transaction.add(R.id.layout, currentFragment);
        }
        transaction.show(currentFragment).commitAllowingStateLoss();
        this.lastFragment = currentFragment;
    }
}
