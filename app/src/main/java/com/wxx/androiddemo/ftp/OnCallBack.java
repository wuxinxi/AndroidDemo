package com.wxx.androiddemo.ftp;

/**
 * 作者：Tangren on 2019-04-28
 * 包名：com.wxx.androiddemo.ftp
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public interface OnCallBack {
    void onSuccess(String savePath);

    void onProgress(int progress);

    void onFail(String msg);
}
