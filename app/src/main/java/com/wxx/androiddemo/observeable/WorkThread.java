package com.wxx.androiddemo.observeable;

import android.os.SystemClock;

import com.wxx.androiddemo.observeable.manager.ObserverManager;

/**
 * 作者: TangRen on 2019/3/10
 * 包名：com.wxx.androiddemo.observeable
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class WorkThread extends Thread {
    private boolean isOpen=true;

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public void run() {
        super.run();
        do {
            System.out.println("WorkThread.run");
            ObserverManager.getInstance().notifyObserver(100,"我是循環消息:"+System.currentTimeMillis());
            SystemClock.sleep(5000);
        }while (isOpen);
    }
}
