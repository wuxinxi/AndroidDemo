package com.wxx.androiddemo.observeable.interfaces;

/**
 * 作者: TangRen on 2019/3/10
 * 包名：com.wxx.androiddemo.observeable.interfaces
 * 邮箱：996489865@qq.com
 * TODO:观察者接口
 */
public  interface ObserverListener {
    void onRefresh(int code,String content);//刷新操作
    String observerName();//观察者名字
}
