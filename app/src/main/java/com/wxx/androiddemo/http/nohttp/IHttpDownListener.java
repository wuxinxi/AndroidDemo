package com.wxx.androiddemo.http.nohttp;

/**
 * 作者：Tangren on 2018-09-18
 * 包名：com.szxb.jcbus.net.http
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public interface IHttpDownListener {
    void onDownloadError(int what, String exception);

    void onProgress(int what, int progress, long fileCount, long speed);

    void onFinish(int what, String filePath);
}
