package com.wxx.androiddemo.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * 作者: TangRen on 2019/2/21
 * 包名：com.wxx.androiddemo.handler
 * 邮箱：996489865@qq.com
 * TODO:使用Handler进行线程通讯
 * 1.子线程->主线程
 * 2.主线程->子线程
 * 3.子线程->子线程
 */
public class MainActivity extends BaseActivity {
    private WeakHandler mainHandler;
    private WeakHandler threadHandler;
    private WeakHandler threadHandler2;

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

        mainHandler = new WeakHandler(this, Looper.myLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程->主线程
                Message message = Message.obtain();
                message.obj = "子线程->主线程";
                message.what = 0;
                mainHandler.sendMessage(message);
            }
        }).start();


        HandlerThread handlerThread = new HandlerThread("handler thread");
        handlerThread.start();

        //获取handler线程的Looper
        threadHandler = new WeakHandler(this, handlerThread.getLooper());
        Message message = Message.obtain();
        message.obj = "主线程->子线程";
        message.what = 0;
        threadHandler.sendMessage(message);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                threadHandler2 = new WeakHandler(MainActivity.this, Looper.myLooper());

                Looper.loop();

            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message1=Message.obtain();
                message1.obj = "子线程->子线程";
                message1.what = 0;
                threadHandler2.sendMessage(message1);
            }
        }).start();

    }


    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //doSth
        }
    };

    /**
     * 弱引用使用handler
     * 静态内部类
     */
    static class WeakHandler extends Handler {
        private WeakReference<MainActivity> weakReference;

        public WeakHandler(MainActivity activity, Looper looper) {
            super(looper);
            this.weakReference = new WeakReference<>(activity);

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = weakReference.get();
            if (activity != null) {
                //doSth……
                if (msg.what == 0) {
                    Log.e("WeakHandler",
                            "handleMessage(WeakHandler.java:85)" + Thread.currentThread().getName() + ">>>>" + msg.obj);
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        if (threadHandler != null) {
            threadHandler.removeCallbacksAndMessages(null);
        }
        if (threadHandler2 != null) {
            threadHandler2.removeCallbacksAndMessages(null);
        }
    }
}
