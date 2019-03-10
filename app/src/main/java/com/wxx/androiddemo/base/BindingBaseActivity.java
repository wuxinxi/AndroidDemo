package com.wxx.androiddemo.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wxx.androiddemo.observeable.interfaces.ObserverListener;

/**
 * 作者: TangRen on 2019/3/10
 * 包名：com.wxx.androiddemo.base
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public abstract class BindingBaseActivity extends AppCompatActivity implements ObserverListener{
    protected abstract int layout();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    public  ViewDataBinding viewDataBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, layout());
        initView(savedInstanceState);
        initData();
    }
}
