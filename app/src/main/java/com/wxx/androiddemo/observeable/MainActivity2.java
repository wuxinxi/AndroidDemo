package com.wxx.androiddemo.observeable;

import android.os.Bundle;
import android.view.View;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.base.BindingBaseActivity;
import com.wxx.androiddemo.databinding.MainObserver2Binding;
import com.wxx.androiddemo.observeable.manager.ObserverManager;

/**
 * 作者: TangRen on 2019/3/10
 * 包名：com.wxx.androiddemo.observeable
 * 邮箱：996489865@qq.com
 * TODO:观察者模式
 */
public class MainActivity2 extends BindingBaseActivity {
    MainObserver2Binding binding;
    @Override
    protected int layout() {
        return R.layout.main_observer2;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        binding= (MainObserver2Binding) viewDataBinding;
        binding.setPresenter(new Presenter());
        ObserverManager.getInstance().addObserver(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRefresh(int code,String content) {
        if (code == 200) {
            binding.content.append(content+"\n");
        }

    }

    @Override
    public String observerName() {
        return this.getClass().getName();
    }

    public class Presenter{

        public void sendContent(View view){
            ObserverManager.getInstance().setChanged();
            ObserverManager.getInstance().notifyObserver(200,observerName()+"..."+ System.currentTimeMillis());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ObserverManager.getInstance().removeObserver(this);
    }
}
