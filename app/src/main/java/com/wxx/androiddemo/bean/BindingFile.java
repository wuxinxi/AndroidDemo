package com.wxx.androiddemo.bean;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableMap;

/**
 * 作者：Tangren on 2019-03-01
 * 包名：com.wxx.androiddemo.bean
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class BindingFile<T> {
    public ObservableField<Object> name = new ObservableField<>();
    public ObservableMap<Object, Object> map = new ObservableArrayMap<>();
    public ObservableArrayList<T> list = new ObservableArrayList<>();
    public ObservableBoolean isOk = new ObservableBoolean(false);
}
