package com.wxx.androiddemo.observeable.interfaces;

/**
 * 作者: TangRen on 2019/3/10
 * 包名：com.wxx.androiddemo.observeable.interfaces
 * 邮箱：996489865@qq.com
 * TODO:被观察者接口
 */
public interface ObserveableListener {
    void addObserver(ObserverListener listener);//添加观察者
    void removeObserver(ObserverListener listener);//移除观察者
    void notifyObserver(int code,String content);//通知观察者
}
