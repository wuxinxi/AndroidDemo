package com.wxx.androiddemo.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者：Tangren on 2019-02-22
 * 包名：com.wxx.androiddemo.intentService
 * 邮箱：996489865@qq.com
 * TODO:IntentService
 * 1.Service+Thread+Handler
 */
public class MyIntentService extends IntentService {
    private MainHandler handler;
    private static OnBitmapCallBack callBack;

    public static void setCallBack(OnBitmapCallBack callBack) {
        MyIntentService.callBack = callBack;
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyIntentService() {
        super("IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        final int index = intent.getIntExtra("index", 0);
        final Bitmap bitmap = downloadImg(url);

        handler = new MainHandler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.setBitmap(index, bitmap);
            }
        });
    }


    private Bitmap downloadImg(String urlStr) {
        HttpURLConnection connection = null;
        BufferedInputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                inputStream = new BufferedInputStream(connection.getInputStream());
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    static class MainHandler extends Handler {

        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
