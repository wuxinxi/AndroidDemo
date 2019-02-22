package com.wxx.androiddemo.listview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * 作者：Tangren on 2019-02-22
 * 包名：com.wxx.androiddemo.listview
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout refresh;
    ListView listView;
    MyHandler handler;
    String[] arrayAdapterStrs = new String[]{"Android", "Java", "Python", "C++", "Html"};
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected int layout() {
        return R.layout.main_list_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        refresh = findViewById(R.id.refresh);
        listView = findViewById(R.id.listView);
        refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        refresh.setOnRefreshListener(this);

    }

    @Override
    protected void initData() {
        handler = new MyHandler(this);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.item_list_view,R.id.text, arrayAdapterStrs);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    static class MyHandler extends Handler {
        private WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = weakReference.get();
            if (activity != null) {
                activity.arrayAdapter.addAll("新增" + System.currentTimeMillis());
                activity.refresh.setRefreshing(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
