package com.wxx.androiddemo.observeable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BindingBaseActivity;
import com.wxx.androiddemo.databinding.MainObserverBinding;
import com.wxx.androiddemo.observeable.manager.ObserverManager;

/**
 * 作者: TangRen on 2019/3/10
 * 包名：com.wxx.androiddemo.observeable
 * 邮箱：996489865@qq.com
 * TODO:观察者模式实例demo
 */
public class MainActivity extends BindingBaseActivity {
    MainObserverBinding binding;
      WorkThread workThread;
    @Override
    protected int layout() {
        return R.layout.main_observer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        binding= (MainObserverBinding) viewDataBinding;
        binding.setPresenter(new Presenter());
        ObserverManager.getInstance().addObserver(this);
    }

    @Override
    protected void initData() {
        workThread=  new WorkThread();
        workThread.start();

    }

    @Override
    public void onRefresh(int code, final String content) {
        if (code==100){
            if (Thread.currentThread()== Looper.getMainLooper().getThread()){
                binding.content.append(content+"\n");
            }else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.content.append("子线程:"+content+"\n");
                    }
                });
            }

        }

    }

    @Override
    public String observerName() {
        return this.getClass().getName();
    }

    public class Presenter{
        public void jumpNetActivity(View view){
            startActivity(new Intent(MainActivity.this,MainActivity2.class));
        }

        public void sendContent(View view){
            ObserverManager.getInstance().setChanged();
            ObserverManager.getInstance().notifyObserver(100, "主线程消息："+System.currentTimeMillis());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        workThread.setOpen(false);
        ObserverManager.getInstance().removeObserver(this);

    }
}
