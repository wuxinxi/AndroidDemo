package com.wxx.androiddemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 作者: TangRen on 2019/2/21
 * 包名：com.wxx.androiddemo.base
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int layout();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout());
        initView(savedInstanceState);
        initData();
    }


}
