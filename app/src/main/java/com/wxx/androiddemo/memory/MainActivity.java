package com.wxx.androiddemo.memory;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * 作者：Tangren on 2019-03-02
 * 包名：com.wxx.androiddemo.memory
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MainActivity extends BaseActivity {
    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    private MyHandler handler;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        int maxHeapSize = manager.getLargeMemoryClass();

        Log.e("MainActivity",
                "initView(MainActivity.java:29)heapSize=" + heapSize + ",maxHeapSize=" + maxHeapSize);
        handler=new MyHandler(this);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity",
                        "run(MainActivity.java:45)HHHHHHHHHHHHHHHH");
            }
        }, 60 * 60 * 1000);

    }

    @Override
    protected void initData() {

    }

    static class MyHandler extends Handler {
        private WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity=weakReference.get();
            if (activity!=null){

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }
}
