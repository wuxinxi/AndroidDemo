package com.wxx.ftp;

/**
 * 作者：Tangren on 2019-08-02
 * 包名：com.wxx.ftp
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public interface OnFtpCallBack {
    void onSuccess(String savePath);

    void onProgress(int progress);

    void onFail(String msg);
}
