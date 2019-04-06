package com.wxx.androiddemo.app;

import android.app.Application;

import com.wxx.androiddemo.greendao.manager.DBCore;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

/**
 * 作者：Tangren on 2019-02-28
 * 包名：com.wxx.androiddemo.app
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        DBCore.init(this);
        NoHttp.initialize(InitializationConfig.newBuilder(this)
                .networkExecutor(new OkHttpNetworkExecutor())
                .connectionTimeout(10 * 1000)
                .readTimeout(10 * 1000)
                .build());
        Logger.setDebug(true);
    }

    public static MyApplication getInstance(){
        return instance;
    }
}
