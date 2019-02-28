package com.wxx.androiddemo.http.nohttp;

import android.util.Log;

import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.ServerError;
import com.yanzhenjie.nohttp.error.StorageReadWriteError;
import com.yanzhenjie.nohttp.error.StorageSpaceNotEnoughError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;

/**
 * 作者：Tangren on 2018-09-18
 * 包名：com.szxb.jcbus.net.http
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class HttpDownResponseListener implements DownloadListener {

    private IHttpDownListener listener;

    public HttpDownResponseListener(IHttpDownListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDownloadError(int what, Exception exception) {
        if (listener != null) {
            String message = "下载出错了\n";
            if (exception instanceof ServerError) {
                message += "服务器发生内部错误";
            } else if (exception instanceof NetworkError) {
                message += "请检查网络";
            } else if (exception instanceof StorageReadWriteError) {
                message += "请检查存储卡";
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                message += "存储位置空间不足";
            } else if (exception instanceof TimeoutError) {
                message += "下载超时";
            } else if (exception instanceof UnKnownHostError) {
                message += "服务器找不到";
            } else if (exception instanceof URLError) {
                message += "url地址错误";
            } else {
                message += "未知错误";
            }
            listener.onDownloadError(what, message);
        }
    }

    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
        Log.d("HttpDownResponseListener",
                "onStart(HttpDownResponseListener.java:36)开始下载" + what);
    }

    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {
        if (listener != null) {
            listener.onProgress(what, progress, fileCount, speed);
        }
    }

    @Override
    public void onFinish(int what, String filePath) {
        if (listener != null) {
            listener.onFinish(what, filePath);
        }
    }

    @Override
    public void onCancel(int what) {

    }
}
