package com.wxx.androiddemo.observeable.manager;

import com.wxx.androiddemo.observeable.interfaces.ObserveableListener;
import com.wxx.androiddemo.observeable.interfaces.ObserverListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: TangRen on 2019/3/10
 * 包名：com.wxx.androiddemo.observeable.manager
 * 邮箱：996489865@qq.com
 * TODO:被观察者管理类
 * 创建具体主题,单例模式
 */
public class ObserverManager implements ObserveableListener{
    private volatile static ObserverManager instance = null;
    private ObserverManager(){}
    public static ObserverManager getInstance(){
        if (instance==null){
            synchronized(ObserverManager.class){
                if(instance == null){
                    instance=new ObserverManager();
                }
            }
        }
        return instance;
    }

    /**
     * 观察者集合
     */
    private List<ObserverListener>observerListenerList=new ArrayList<>();
    @Override
    public void addObserver(ObserverListener listener) {
        if (!observerListenerList.contains(listener)){
            observerListenerList.add(listener);
            System.out.println("ObserverManager.addObserver 添加观察者");
        }
    }

    @Override
    public void removeObserver(ObserverListener listener) {
        if (observerListenerList.contains(listener)){
            observerListenerList.remove(listener);
            System.out.println("ObserverManager.removeObserver 移除观察者");
        }
    }

    @Override
    public void notifyObserver(int code,String content) {
        for (ObserverListener observerListener : observerListenerList) {
            observerListener.onRefresh(code,content);
            System.out.println("ObserverManager.notifyObserver 通知观察者："+observerListener.observerName()+">>数据内容:"+content);
        }
    }
}
